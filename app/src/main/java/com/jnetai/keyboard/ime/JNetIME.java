package com.jnetai.keyboard.ime;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jnetai.keyboard.clipboard.ClipboardManager;
import com.jnetai.keyboard.dictionary.WordDictionary;
import com.jnetai.keyboard.diagnostics.Diagnostics;
import com.jnetai.keyboard.diagnostics.ErrorCodes;
import com.jnetai.keyboard.emoji.EmojiDatabase;
import com.jnetai.keyboard.remapping.KeyRemapping;
import com.jnetai.keyboard.settings.KeyboardSettings;
import com.jnetai.keyboard.translation.TranslationManager;
import com.jnetai.keyboard.unicode.UnicodeStyleDatabase;

public class JNetIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private static JNetIME instance;
    private JNetKeyboardView keyboardView;
    private LinearLayout suggestionBar;
    private Keyboard currentKeyboard;
    private Keyboard ukKeyboard;
    private Keyboard usKeyboard;
    private Keyboard[] symbolsKeyboards;
    private Keyboard[] emojiKeyboards;
    private Keyboard phrasesKeyboard;
    private KeyboardSettings settings;
    private TranslationManager translationManager;
    private ClipboardManager clipboardManager;
    private KeyRemapping keyRemapping;
    private boolean isShifted = false;
    private boolean isCapsLock = false;
    private boolean isSecureField = false;
    private boolean phrasesActive = false;
    private int symbolsPage = -1;
    private int emojiPage = -1;
    private StringBuilder composing = new StringBuilder();
    private StringBuilder currentWord = new StringBuilder();
    private int wordStartOffset = -1;
    private int wordDisplayLen = 0;
    private long lastShiftTime = 0;
    private long lastPressTime = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private CompletionInfo[] completions;
    private boolean phraseLongPressed = false;
    private Runnable phraseLongPressRunnable;

    public static JNetIME getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        settings = KeyboardSettings.getInstance(this);
        translationManager = new TranslationManager();
        clipboardManager = new ClipboardManager(this);
        keyRemapping = new KeyRemapping(this);
        WordDictionary.init(new java.io.File(getFilesDir(), "custom_words.txt"));
        Diagnostics.info("JNetIME", "onCreate", "IME service created");
    }

    @Override
    public View onCreateInputView() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        suggestionBar = new LinearLayout(this);
        suggestionBar.setOrientation(LinearLayout.HORIZONTAL);
        suggestionBar.setGravity(android.view.Gravity.CENTER_VERTICAL);
        suggestionBar.setBackgroundColor(0xFF2D2D2D);
        suggestionBar.setVisibility(View.VISIBLE);
        suggestionBar.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(getResources().getIdentifier(
                        "suggestion_bar_height", "dimen", getPackageName()))));
        // A word can match many emojis, so let the bar scroll sideways to reach them all.
        HorizontalScrollView suggestionScroller = new HorizontalScrollView(this);
        suggestionScroller.setHorizontalScrollBarEnabled(false);
        suggestionScroller.setFillViewport(true);
        suggestionScroller.addView(suggestionBar, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        root.addView(suggestionScroller,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) getResources().getDimension(getResources().getIdentifier(
                                "suggestion_bar_height", "dimen", getPackageName()))));

        keyboardView = new JNetKeyboardView(this);
        keyboardView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        keyboardView.setOnKeyboardActionListener(this);
        keyboardView.setPreviewEnabled(false);
        root.addView(keyboardView);

        loadKeyboards();
        applyTheme();
        return root;
    }

    private void loadKeyboards() {
        String layout = settings.getKeyboardLayout();
        int ukId = getResources().getIdentifier("keyboard_uk", "xml", getPackageName());
        int usId = getResources().getIdentifier("keyboard_us", "xml", getPackageName());

        if (ukId != 0) ukKeyboard = new Keyboard(this, ukId);
        if (usId != 0) usKeyboard = new Keyboard(this, usId);

        java.util.List<Keyboard> symList = new java.util.ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String name = i == 1 ? "keyboard_symbols" : "keyboard_symbols" + i;
            int id = getResources().getIdentifier(name, "xml", getPackageName());
            if (id == 0) break;
            symList.add(new Keyboard(this, id));
        }
        symbolsKeyboards = symList.toArray(new Keyboard[0]);

        java.util.List<Keyboard> emList = new java.util.ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String name = i == 1 ? "keyboard_emoji" : "keyboard_emoji" + i;
            int id = getResources().getIdentifier(name, "xml", getPackageName());
            if (id == 0) break;
            emList.add(new Keyboard(this, id));
        }
        emojiKeyboards = emList.toArray(new Keyboard[0]);
        int phrasesId = getResources().getIdentifier("keyboard_phrases", "xml", getPackageName());
        if (phrasesId != 0) phrasesKeyboard = new Keyboard(this, phrasesId);
        applyPhraseLabels(phrasesKeyboard);

        if ("us".equals(layout) && usKeyboard != null) {
            currentKeyboard = usKeyboard;
        } else if (ukKeyboard != null) {
            currentKeyboard = ukKeyboard;
        } else if (usKeyboard != null) {
            currentKeyboard = usKeyboard;
        }
    }

    private void applyTheme() {
        if (keyboardView == null) return;
        boolean dark = settings.isDarkTheme();
        keyboardView.setBackgroundColor(dark ? 0xFF1E1E1E : 0xFFF5F5F5);
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        isSecureField = isSecureInputType(info);
        if (isSecureField) {
            Diagnostics.info("JNetIME", "onStartInputView", "Secure field detected");
        }
        if (keyboardView != null && currentKeyboard != null) {
            keyboardView.setKeyboard(currentKeyboard);
        }
        currentWord.setLength(0);
        composing.setLength(0);
        wordStartOffset = -1;
        wordDisplayLen = 0;
        hideSuggestions();
    }

    @Override
    public void onDisplayCompletions(CompletionInfo[] completions) {
        if (!settings.isSuggestionsEnabled()) {
            this.completions = null;
            hideSuggestions();
            return;
        }
        if (completions == null || completions.length == 0) {
            this.completions = null;
            hideSuggestions();
            return;
        }
        this.completions = completions;
    }

    private void updateSuggestions() {
        if (suggestionBar == null) return;
        if (!settings.isSuggestionsEnabled()) {
            hideSuggestions();
            return;
        }
        String typed = currentWord.toString();
        if (typed.isEmpty()) {
            if (settings.isNextWordPrediction() && !settings.isTranslationEnabled()) {
                showNextWordPredictions();
            } else {
                hideSuggestions();
            }
            return;
        }
        java.util.List<String> suggestions = WordDictionary.getSuggestions(typed);
        if (suggestions.isEmpty()) {
            hideSuggestions();
            return;
        }
        boolean customDict = settings.isAddWordEnabled();
        suggestionBar.removeAllViews();
        for (String s : suggestions) {
            TextView tv = makeSuggestionChip(s, customDict);
            suggestionBar.addView(tv);
        }
        String norm = typed.trim().toLowerCase();
        if (customDict && !norm.isEmpty() && !WordDictionary.isWord(norm)) {
            TextView add = new TextView(this);
            add.setText("+ Add \"" + norm + "\"");
            add.setTextSize(14);
            add.setTextColor(0xFF8AB4F8);
            add.setPadding(16, 12, 16, 12);
            add.setBackgroundColor(0xFF1A73E8);
            add.setOnClickListener(v -> {
                WordDictionary.addWord(norm);
                hideSuggestions();
                updateSuggestions();
            });
            suggestionBar.addView(add);
        }
        suggestionBar.setVisibility(View.VISIBLE);
    }

    private TextView makeSuggestionChip(final String word, boolean customDict) {
        TextView tv = new TextView(this);
        tv.setText(word);
        tv.setTextSize(16);
        tv.setTextColor(0xFFFFFFFF);
        tv.setPadding(16, 12, 16, 12);
        tv.setBackgroundColor(0xFF3C3C3C);
        tv.setClickable(true);
        tv.setOnClickListener(v -> acceptSuggestion(word));
        if (customDict) {
            tv.setLongClickable(true);
            tv.setOnLongClickListener(v -> {
                if (WordDictionary.isCustomWord(word)) {
                    confirmRemoveWord(word);
                    return true;
                }
                return false;
            });
        }
        return tv;
    }

    private void showNextWordPredictions() {
        if (suggestionBar == null) return;
        suggestionBar.removeAllViews();
        for (String s : WordDictionary.getNextWordSuggestions()) {
            TextView tv = new TextView(this);
            tv.setText(s);
            tv.setTextSize(16);
            tv.setTextColor(0xFFFFFFFF);
            tv.setPadding(16, 12, 16, 12);
            tv.setBackgroundColor(0xFF3C3C3C);
            tv.setOnClickListener(v -> {
                InputConnection ic = getCurrentInputConnection();
                if (ic != null) ic.commitText(s + " ", 1);
                updateSuggestions();
            });
            suggestionBar.addView(tv);
        }
        suggestionBar.setVisibility(View.VISIBLE);
    }

    private void confirmRemoveWord(final String word) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Remove word")
                .setMessage("Remove \"" + word + "\" from your dictionary?")
                .setPositiveButton("Remove", (d, w) -> {
                    WordDictionary.removeWord(word);
                    updateSuggestions();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void acceptSuggestion(String suggestion) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        currentWord.setLength(0);
        wordStartOffset = -1;
        wordDisplayLen = 0;
        hideSuggestions();
        String text = suggestion + " ";
        if (!isSecureField && settings.isUnicodeEnabled() && !"normal".equals(settings.getCurrentStyleId())) {
            text = UnicodeStyleDatabase.transform(suggestion, settings.getCurrentStyleId()) + " ";
        }
        ic.commitText(text, 1);
        if (settings.isSuggestionsEnabled()) {
            updateSuggestions();
        }
    }

    private void hideSuggestions() {
        if (suggestionBar != null) {
            suggestionBar.removeAllViews();
            suggestionBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Adds every emoji that matches the completed word to the suggestion bar.
     * Word chips are already there, so these append after them - the bar scrolls sideways.
     */
    private void showEmojiSuggestions(String word, int wordStart, int displayLen) {
        if (suggestionBar == null) return;
        if (isSecureField) return;
        if (!settings.isEmojiSuggestionsEnabled()) return;
        if (word == null || word.isEmpty()) return;
        java.util.List<EmojiDatabase.EmojiEntry> matches = EmojiDatabase.getEmojiForWord(word);
        if (matches.isEmpty()) return;
        final boolean replace = settings.isEmojiReplaceEnabled();
        final int ws = wordStart;
        final int dl = displayLen;
        for (EmojiDatabase.EmojiEntry e : matches) {
            final String emoji = e.emoji;
            TextView tv = new TextView(this);
            tv.setText(emoji);
            tv.setTextSize(16);
            tv.setTextColor(0xFFFFFFFF);
            tv.setGravity(android.view.Gravity.CENTER);
            tv.setPadding(14, 10, 14, 10);
            tv.setBackgroundColor(0xFF3C3C3C);
            tv.setClickable(true);
            tv.setFocusable(true);
            tv.setContentDescription(emoji);
            tv.setOnClickListener(v -> acceptEmojiSuggestion(emoji, ws, dl, replace));
            suggestionBar.addView(tv);
        }
        suggestionBar.setVisibility(View.VISIBLE);
    }

    private void acceptEmojiSuggestion(String emoji, int wordStart, int displayLen, boolean replace) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        hideSuggestions();
        if (replace) {
            try {
                int cursor = getCursorAbs();
                if (cursor >= 0 && displayLen > 0 && cursor - (displayLen + 1) >= 0) {
                    ic.beginBatchEdit();
                    ic.deleteSurroundingText(displayLen + 1, 0);
                    ic.commitText(emoji + " ", 1);
                    ic.endBatchEdit();
                    if (settings.isSuggestionsEnabled()) updateSuggestions();
                    return;
                }
            } catch (Exception e) {
                Diagnostics.log(ErrorCodes.GE_001, "JNetIME", "acceptEmojiSuggestion", e, null);
            }
        }
        ic.commitText(emoji + " ", 1);
        if (settings.isSuggestionsEnabled()) updateSuggestions();
    }

    private int getCursorAbs() {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return -1;
        try {
            android.view.inputmethod.ExtractedTextRequest req = new android.view.inputmethod.ExtractedTextRequest();
            android.view.inputmethod.ExtractedText et = ic.getExtractedText(req, 0);
            if (et == null || et.text == null) return -1;
            return et.startOffset + et.selectionStart;
        } catch (Exception e) {
            return -1;
        }
    }

    private boolean isComposingAligned(InputConnection ic) {
        if (currentWord.length() == 0 && composing.length() == 0) return true;
        int cursor = getCursorAbs();
        if (cursor < 0 || wordStartOffset < 0) return true;
        return cursor == wordStartOffset + wordDisplayLen;
    }

    private void resetComposing(InputConnection ic) {
        if (ic != null && (composing.length() > 0 || currentWord.length() > 0)) {
            ic.finishComposingText();
        }
        composing.setLength(0);
        currentWord.setLength(0);
        wordStartOffset = -1;
        wordDisplayLen = 0;
        hideSuggestions();
    }

    private void syncComposing(InputConnection ic) {
        if (currentWord.length() == 0 && composing.length() == 0) return;
        if (!isComposingAligned(ic)) {
            resetComposing(ic);
        }
    }

    private void commitCurrentWord(InputConnection ic) {
        String word = currentWord.toString();
        if (word.isEmpty()) return;
        currentWord.setLength(0);
        wordStartOffset = -1;
        wordDisplayLen = 0;
        hideSuggestions();
        String commit = word;
        if (settings.isAutoCorrectEnabled()) {
            String corrected = WordDictionary.correct(word);
            if (corrected != null) commit = corrected;
        }
        if (!isSecureField && settings.isUnicodeEnabled()
                && !"normal".equals(settings.getCurrentStyleId())) {
            commit = UnicodeStyleDatabase.transform(commit, settings.getCurrentStyleId());
        }
        ic.setComposingText(commit, 1);
        ic.finishComposingText();
    }

    private boolean isSecureInputType(EditorInfo info) {
        if (info == null) return false;
        int inputType = info.inputType & InputType.TYPE_MASK_CLASS;
        int variation = info.inputType & InputType.TYPE_MASK_VARIATION;
        if (inputType == InputType.TYPE_CLASS_TEXT) {
            return variation == InputType.TYPE_TEXT_VARIATION_PASSWORD
                    || variation == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    || variation == InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;
        }
        if (inputType == InputType.TYPE_CLASS_NUMBER) {
            return variation == InputType.TYPE_NUMBER_VARIATION_PASSWORD;
        }
        return false;
    }

    @Override
    public void onPress(int primaryCode) {
        lastPressTime = System.currentTimeMillis();
        if (settings.isHapticFeedback()) {
            performHapticFeedback();
        }
        if (isPhraseCode(primaryCode)) {
            phraseLongPressed = false;
            if (phraseLongPressRunnable != null) handler.removeCallbacks(phraseLongPressRunnable);
            phraseLongPressRunnable = () -> {
                phraseLongPressed = true;
                int index = primaryCode + 201;
                java.util.List<String> phrases = settings.getCommonPhrases();
                if (index >= 0 && index < phrases.size()) {
                    showPhraseEditDialog(index);
                }
            };
            handler.postDelayed(phraseLongPressRunnable, 600);
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        if (isPhraseCode(primaryCode) && phraseLongPressRunnable != null) {
            handler.removeCallbacks(phraseLongPressRunnable);
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;

        String remapped = keyRemapping.getRemappedValue(String.valueOf(primaryCode));
        if (remapped != null) {
            ic.commitText(remapped, 1);
            return;
        }

        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                handleBackspace(ic);
                break;
            case Keyboard.KEYCODE_SHIFT:
                handleShift();
                break;
            case Keyboard.KEYCODE_DONE:
                handleEnter(ic);
                break;
            case -101:
                toggleEmoji();
                break;
            case -102:
                toggleSymbols();
                break;
            case -103:
                openFontSelector();
                break;
            case -104:
                openSettings();
                break;
            case -105:
                handleManualTranslation(ic);
                break;
            case -106:
                openClipboard();
                break;
            case -107:
                handleSpace(ic);
                break;
            case -111:
                nextPage();
                break;
            case -201:
            case -202:
            case -203:
            case -204:
            case -205:
            case -206:
            case -207:
            case -208:
            case -209:
            case -210:
            case -211:
            case -212:
                handlePhrasePress(primaryCode);
                break;
            case -213:
                showPhraseEditDialog(-1);
                break;
            case -108:
                if (symbolsPage >= 0) {
                    nextPage();
                } else {
                    switchToLetters();
                }
                break;
            case -109:
                nextPage();
                break;
            case -110:
                openEmojiSearch();
                break;
            default:
                handleCharacter(primaryCode, ic);
                break;
        }
    }

    private void handleBackspace(InputConnection ic) {
        CharSequence selected = ic.getSelectedText(0);
        if (selected != null && selected.length() > 0) {
            ic.commitText("", 1);
            return;
        }
        if ((currentWord.length() > 0 || composing.length() > 0) && !isComposingAligned(ic)) {
            resetComposing(ic);
            sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
            return;
        }
        if (currentWord.length() > 0) {
            currentWord.setLength(currentWord.length() - 1);
            if (currentWord.length() > 0) {
                String display = applyUnicode(currentWord.toString());
                wordDisplayLen = display.length();
                ic.setComposingText(display, 1);
                if (settings.isSuggestionsEnabled()) updateSuggestions();
            } else {
                hideSuggestions();
                ic.finishComposingText();
                wordStartOffset = -1;
                wordDisplayLen = 0;
            }
        } else if (composing.length() > 0) {
            composing.setLength(composing.length() - 1);
            String display = applyUnicode(composing.toString());
            wordDisplayLen = display.length();
            ic.setComposingText(display, 1);
        } else {
            sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
        }
    }

    private void handleShift() {
        long now = System.currentTimeMillis();
        if (isShifted && (now - lastShiftTime) < 500) {
            isCapsLock = true;
            isShifted = true;
        } else {
            isShifted = !isShifted;
            isCapsLock = false;
        }
        lastShiftTime = now;
        if (keyboardView != null) {
            keyboardView.setShifted(isShifted || isCapsLock);
        }
    }

    private void handleEnter(InputConnection ic) {
        syncComposing(ic);
        if (!isSecureField && (settings.isSuggestionsEnabled() || settings.isAutoCorrectEnabled())
                && currentWord.length() > 0) {
            commitCurrentWord(ic);
        }
        if (!isSecureField && settings.isTranslationEnabled()) {
            if (composing.length() > 0) {
                ic.finishComposingText();
                composing.setLength(0);
                wordStartOffset = -1;
                wordDisplayLen = 0;
            }
            translateWholeInputThenSend();
            return;
        }
        if (composing.length() > 0) {
            ic.finishComposingText();
            composing.setLength(0);
            wordStartOffset = -1;
            wordDisplayLen = 0;
        }
        if (settings.isEnterSendsMessage()) {
            sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
        } else {
            ic.commitText("\n", 1);
        }
    }

    private void handleSpace(InputConnection ic) {
        syncComposing(ic);
        if (!isSecureField && (settings.isSuggestionsEnabled() || settings.isAutoCorrectEnabled()
                || settings.isEmojiSuggestionsEnabled())
                && currentWord.length() > 0) {
            String word = currentWord.toString();
            int wordStart = wordStartOffset;
            int displayLen = wordDisplayLen;
            if (settings.isAutoCorrectEnabled()) {
                String corrected = WordDictionary.correct(word);
                if (corrected != null) {
                    word = corrected;
                    displayLen = applyUnicode(word).length();
                }
            }
            commitCurrentWord(ic);
            ic.commitText(" ", 1);
            if (settings.isSuggestionsEnabled() && !settings.isTranslationEnabled()) {
                updateSuggestions();
            } else {
                hideSuggestions();
            }
            showEmojiSuggestions(word, wordStart, displayLen);
            return;
        }
        if (!isSecureField && settings.isTranslationEnabled() && composing.length() > 0) {
            String word = composing.toString();
            ic.setComposingText("", 1);
            composing.setLength(0);
            wordStartOffset = -1;
            wordDisplayLen = 0;
            translateWord(word, " ");
            return;
        }
        ic.commitText(" ", 1);
        if (settings.isSuggestionsEnabled() && !settings.isTranslationEnabled()) {
            updateSuggestions();
        }
    }

    private void handleCharacter(int primaryCode, InputConnection ic) {
        boolean isEmoji = isEmojiCodePoint(primaryCode);

        if (isEmoji) {
            String text = new String(Character.toChars(primaryCode));
            if (emojiPage >= 0 && System.currentTimeMillis() - lastPressTime > 600) {
                ic.commitText(text + text + text, 1);
            } else {
                ic.commitText(text, 1);
            }
            return;
        }

        if (isShifted || isCapsLock) {
            primaryCode = Character.toUpperCase(primaryCode);
        }
        String text = String.valueOf((char) primaryCode);
        boolean isLetter = Character.isLetter(primaryCode);

        boolean unicodeOn = !isSecureField && settings.isUnicodeEnabled()
                && !"normal".equals(settings.getCurrentStyleId());

        boolean suggestOn = !isSecureField && (settings.isSuggestionsEnabled() || settings.isAutoCorrectEnabled()
                || settings.isEmojiSuggestionsEnabled());

        syncComposing(ic);

        if (!isSecureField && settings.isTranslationEnabled() && isLetter) {
            if (composing.length() == 0) wordStartOffset = getCursorAbs();
            composing.append(text);
            String display = unicodeOn
                    ? UnicodeStyleDatabase.transform(composing.toString(), settings.getCurrentStyleId())
                    : composing.toString();
            wordDisplayLen = display.length();
            ic.setComposingText(display, 1);
            if (isShifted && !isCapsLock) {
                isShifted = false;
                if (keyboardView != null) keyboardView.setShifted(false);
            }
            return;
        }

        if (suggestOn && isLetter) {
            if (currentWord.length() == 0) wordStartOffset = getCursorAbs();
            currentWord.append(text);
            String display = unicodeOn ? UnicodeStyleDatabase.transform(currentWord.toString(), settings.getCurrentStyleId())
                    : currentWord.toString();
            wordDisplayLen = display.length();
            ic.setComposingText(display, 1);
            if (settings.isSuggestionsEnabled()) {
                updateSuggestions();
            }
            if (isShifted && !isCapsLock) {
                isShifted = false;
                if (keyboardView != null) keyboardView.setShifted(false);
            }
            return;
        }

        if (suggestOn && currentWord.length() > 0) {
            commitCurrentWord(ic);
        }

        if (unicodeOn) {
            text = UnicodeStyleDatabase.transform(text, settings.getCurrentStyleId());
        }

        ic.commitText(text, 1);

        if (isShifted && !isCapsLock) {
            isShifted = false;
            if (keyboardView != null) keyboardView.setShifted(false);
        }
    }

    private String applyUnicode(String text) {
        if (text == null || text.isEmpty()) return text;
        if (isSecureField || !settings.isUnicodeEnabled()
                || "normal".equals(settings.getCurrentStyleId())) {
            return text;
        }
        return UnicodeStyleDatabase.transform(text, settings.getCurrentStyleId());
    }

    private boolean isEmojiCodePoint(int code) {
        return code > 0xFFFF
                || (code >= 0x2600 && code <= 0x27BF)
                || (code >= 0x2B00 && code <= 0x2BFF)
                || (code >= 0x1F000 && code <= 0x1FFFF)
                || (code >= 0xFE00 && code <= 0xFE0F);
    }

    private void translateWord(String word, String suffix) {
        if (word.isEmpty()) {
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) ic.commitText(suffix, 1);
            return;
        }
        String sourceLang = settings.isAutoDetectSource() ? "auto" : settings.getSourceLanguage();
        String targetLang = settings.getDestinationLanguage();
        String apiUrl = settings.getApiUrl();
        String apiKey = settings.getApiKey();

        translationManager.setCurrentProvider(settings.getTranslationProvider());
        translationManager.translate(word, sourceLang, targetLang, apiUrl, apiKey,
                new TranslationManager.TranslationCallback() {
                    @Override
                    public void onSuccess(String translatedText) {
                        handler.post(() -> {
                            InputConnection conn = getCurrentInputConnection();
                            if (conn != null) {
                                conn.commitText(applyUnicode(translatedText) + suffix, 1);
                            }
                        });
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        handler.post(() -> {
                            InputConnection conn = getCurrentInputConnection();
                            if (conn != null) {
                                conn.commitText(applyUnicode(word) + suffix, 1);
                            }
                        });
                    }
                });
    }

    private void translateWholeInput() {
        translateWholeInputThenSend(false);
    }

    private void translateWholeInputThenSend() {
        translateWholeInputThenSend(true);
    }

    private void translateWholeInputThenSend(final boolean sendAfter) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null || isSecureField) return;
        if (composing.length() > 0) {
            ic.finishComposingText();
            composing.setLength(0);
            wordStartOffset = -1;
            wordDisplayLen = 0;
        }
        android.view.inputmethod.ExtractedTextRequest req = new android.view.inputmethod.ExtractedTextRequest();
        req.flags = 0;
        android.view.inputmethod.ExtractedText et = ic.getExtractedText(req,
                android.view.inputmethod.InputConnection.GET_EXTRACTED_TEXT_MONITOR);
        if (et == null || et.text == null || et.text.length() == 0) {
            if (sendAfter) sendEnterKey();
            return;
        }
        String fullText = et.text.toString();
        if (fullText.trim().isEmpty()) {
            if (sendAfter) sendEnterKey();
            return;
        }

        String sourceLang = settings.isAutoDetectSource() ? "auto" : settings.getSourceLanguage();
        String targetLang = settings.getDestinationLanguage();
        String apiUrl = settings.getApiUrl();
        String apiKey = settings.getApiKey();

        translationManager.setCurrentProvider(settings.getTranslationProvider());
        translationManager.translate(fullText, sourceLang, targetLang, apiUrl, apiKey,
                new TranslationManager.TranslationCallback() {
                    @Override
                    public void onSuccess(String translatedText) {
                        handler.post(() -> {
                            InputConnection conn = getCurrentInputConnection();
                            if (conn != null) {
                                conn.beginBatchEdit();
                                conn.setSelection(et.startOffset, et.startOffset + fullText.length());
                                conn.commitText(applyUnicode(translatedText), 1);
                                conn.endBatchEdit();
                            }
                            if (sendAfter) sendEnterKey();
                        });
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        Diagnostics.log(errorCode, "JNetIME", "translateWholeInput", message);
                        if (sendAfter) sendEnterKey();
                    }
                });
    }

    private void sendEnterKey() {
        InputConnection conn = getCurrentInputConnection();
        if (conn != null) {
            if (settings.isEnterSendsMessage()) {
                sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
            } else {
                conn.commitText("\n", 1);
            }
        }
    }

    private void handleManualTranslation(InputConnection ic) {
        translateWholeInput();
    }

    private void toggleEmoji() {
        if (emojiPage >= 0) {
            nextPage();
            return;
        }
        emojiPage = 0;
        symbolsPage = -1;
        phrasesActive = false;
        if (emojiKeyboards != null && emojiKeyboards.length > 0 && keyboardView != null) {
            keyboardView.setKeyboard(emojiKeyboards[0]);
        }
    }

    private void toggleSymbols() {
        if (symbolsPage >= 0) {
            nextPage();
            return;
        }
        symbolsPage = 0;
        emojiPage = -1;
        phrasesActive = false;
        if (symbolsKeyboards != null && symbolsKeyboards.length > 0 && keyboardView != null) {
            keyboardView.setKeyboard(symbolsKeyboards[0]);
        }
    }

    private void nextPage() {
        if (phrasesActive) {
            switchToLetters();
        } else if (emojiPage >= 0) {
            switchToPhrases();
        } else if (symbolsPage >= 0) {
            if (symbolsPage >= symbolsKeyboards.length - 1) {
                switchToEmoji();
            } else {
                symbolsPage++;
                if (keyboardView != null) keyboardView.setKeyboard(symbolsKeyboards[symbolsPage]);
            }
        } else {
            switchToSymbols();
        }
    }

    private void prevPage() {
        if (phrasesActive) {
            switchToEmoji();
        } else if (emojiPage >= 0) {
            symbolsPage = symbolsKeyboards.length - 1;
            emojiPage = -1;
            if (keyboardView != null && symbolsKeyboards.length > 0) {
                keyboardView.setKeyboard(symbolsKeyboards[symbolsPage]);
            }
        } else if (symbolsPage >= 0) {
            if (symbolsPage <= 0) {
                switchToLetters();
            } else {
                symbolsPage--;
                if (keyboardView != null) keyboardView.setKeyboard(symbolsKeyboards[symbolsPage]);
            }
        } else {
            switchToPhrases();
        }
    }

    private void switchToSymbols() {
        symbolsPage = 0;
        emojiPage = -1;
        phrasesActive = false;
        if (symbolsKeyboards != null && symbolsKeyboards.length > 0 && keyboardView != null) {
            keyboardView.setKeyboard(symbolsKeyboards[0]);
        }
    }

    private void switchToEmoji() {
        emojiPage = 0;
        symbolsPage = -1;
        phrasesActive = false;
        if (emojiKeyboards != null && emojiKeyboards.length > 0 && keyboardView != null) {
            keyboardView.setKeyboard(emojiKeyboards[0]);
        }
    }

    private void switchToPhrases() {
        emojiPage = -1;
        symbolsPage = -1;
        phrasesActive = true;
        if (phrasesKeyboard != null && keyboardView != null) {
            applyPhraseLabels(phrasesKeyboard);
            keyboardView.setKeyboard(phrasesKeyboard);
        }
    }

    private void switchToLetters() {
        symbolsPage = -1;
        emojiPage = -1;
        phrasesActive = false;
        if (currentKeyboard != null && keyboardView != null) {
            keyboardView.setKeyboard(currentKeyboard);
        }
    }

    private void applyPhraseLabels(Keyboard keyboard) {
        if (keyboard == null) return;
        java.util.List<String> phrases = settings.getCommonPhrases();
        java.util.Map<Integer, String> labels = new java.util.HashMap<>();
        for (Keyboard.Key key : keyboard.getKeys()) {
            if (key.codes != null && key.codes.length > 0) {
                int code = key.codes[0];
                if (code >= -212 && code <= -201) {
                    int index = code + 201;
                    key.label = "";
                    if (index >= 0 && index < phrases.size()) {
                        labels.put(code, phrases.get(index));
                    } else {
                        labels.put(code, "");
                    }
                }
            }
        }
        if (keyboardView != null) {
            keyboardView.setPhraseLabels(labels);
        }
    }

    private boolean isPhraseCode(int code) {
        return code >= -212 && code <= -201;
    }

    private void insertPhrase(int code) {
        java.util.List<String> phrases = settings.getCommonPhrases();
        int index = code + 201;
        if (index < 0 || index >= phrases.size()) return;
        String phrase = phrases.get(index);
        if (phrase == null || phrase.isEmpty()) return;
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        if (!isSecureField && settings.isUnicodeEnabled()
                && !"normal".equals(settings.getCurrentStyleId())) {
            phrase = UnicodeStyleDatabase.transform(phrase, settings.getCurrentStyleId());
        }
        ic.commitText(phrase, 1);
        if (settings.isSuggestionsEnabled()) updateSuggestions();
    }

    private void handlePhrasePress(int code) {
        if (phraseLongPressed) {
            phraseLongPressed = false;
            return;
        }
        java.util.List<String> phrases = settings.getCommonPhrases();
        int index = code + 201;
        if (index >= 0 && index < phrases.size() && phrases.get(index) != null
                && !phrases.get(index).isEmpty()) {
            insertPhrase(code);
        } else {
            showPhraseEditDialog(-1);
        }
    }

    private void showPhraseEditDialog(final int index) {
        handler.post(() -> showPhraseEditDialogInner(index));
    }

    private void showPhraseEditDialogInner(int index) {
        java.util.List<String> phrases = settings.getCommonPhrases();
        String phrase = (index >= 0 && index < phrases.size()) ? phrases.get(index) : "";
        if (index < 0 && phrase.isEmpty()) {
            phrase = getCurrentFieldText();
        }
        final EditText input = new EditText(this);
        input.setText(phrase);
        input.setTextColor(0xFFFFFFFF);
        input.setBackgroundColor(0xFF3C3C3C);
        input.setPadding(16, 12, 16, 12);
        input.setHint("Phrase (emojis ok)");

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this)
                .setTitle(index >= 0 ? "Edit Phrase" : "Add Phrase")
                .setView(input)
                .setPositiveButton("Save", (d, w) -> {
                    String text = input.getText().toString().trim();
                    if (text.isEmpty()) return;
                    if (index >= 0) {
                        settings.updateCommonPhrase(index, text);
                    } else {
                        settings.addCommonPhrase(text);
                    }
                    refreshPhrasesPage();
                })
                .setNegativeButton("Cancel", null);
        if (index >= 0) {
            builder.setNeutralButton("Remove", (d, w) -> {
                settings.removeCommonPhrase(index);
                refreshPhrasesPage();
            });
        }
        try {
            builder.show();
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.GE_001, "JNetIME", "showPhraseEditDialog", e, null);
        }
    }

    private String getCurrentFieldText() {
        try {
            InputConnection ic = getCurrentInputConnection();
            if (ic == null) return "";
            CharSequence sel = ic.getSelectedText(0);
            if (sel != null && sel.length() > 0) return sel.toString().trim();
            CharSequence before = ic.getTextBeforeCursor(300, 0);
            if (before != null && before.length() > 0) return before.toString().trim();
        } catch (Exception e) {
        }
        return "";
    }

    private void refreshPhrasesPage() {
        applyPhraseLabels(phrasesKeyboard);
        if (phrasesActive && keyboardView != null && phrasesKeyboard != null) {
            keyboardView.setKeyboard(phrasesKeyboard);
        }
    }

    private void openFontSelector() {
        try {
            android.content.Intent intent = new android.content.Intent(this,
                    Class.forName("com.jnetai.keyboard.settings.SettingsActivity"));
            intent.putExtra("open_fragment", "unicode");
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.GE_001, "JNetIME", "openFontSelector", e, null);
        }
    }

    private void openSettings() {
        try {
            android.content.Intent intent = new android.content.Intent(this,
                    Class.forName("com.jnetai.keyboard.settings.SettingsActivity"));
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.GE_001, "JNetIME", "openSettings", e, null);
        }
    }

    private void openClipboard() {
        try {
            android.content.Intent intent = new android.content.Intent(this,
                    Class.forName("com.jnetai.keyboard.settings.SettingsActivity"));
            intent.putExtra("open_fragment", "clipboard");
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.GE_001, "JNetIME", "openClipboard", e, null);
        }
    }

    private void openEmojiSearch() {
        try {
            android.content.Intent intent = new android.content.Intent(this,
                    Class.forName("com.jnetai.keyboard.settings.SettingsActivity"));
            intent.putExtra("open_fragment", "emoji");
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.GE_001, "JNetIME", "openEmojiSearch", e, null);
        }
    }

    private void performHapticFeedback() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                VibratorManager vm = (VibratorManager) getSystemService(VIBRATOR_MANAGER_SERVICE);
                if (vm != null) {
                    Vibrator v = vm.getDefaultVibrator();
                    v.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            } else {
                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (v != null) {
                    v.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            ic.commitText(text, 1);
        }
    }

    @Override
    public void swipeLeft() {
        if (symbolsPage >= 0 || emojiPage >= 0) prevPage();
    }

    @Override
    public void swipeRight() {
        if (symbolsPage >= 0 || emojiPage >= 0) nextPage();
    }
    @Override
    public void swipeDown() {}
    @Override
    public void swipeUp() {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }
}
