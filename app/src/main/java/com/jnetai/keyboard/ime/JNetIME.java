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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jnetai.keyboard.clipboard.ClipboardManager;
import com.jnetai.keyboard.dictionary.WordDictionary;
import com.jnetai.keyboard.diagnostics.Diagnostics;
import com.jnetai.keyboard.diagnostics.ErrorCodes;
import com.jnetai.keyboard.remapping.KeyRemapping;
import com.jnetai.keyboard.settings.KeyboardSettings;
import com.jnetai.keyboard.translation.TranslationManager;
import com.jnetai.keyboard.unicode.UnicodeStyleDatabase;

public class JNetIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private static JNetIME instance;
    private KeyboardView keyboardView;
    private LinearLayout suggestionBar;
    private Keyboard currentKeyboard;
    private Keyboard ukKeyboard;
    private Keyboard usKeyboard;
    private Keyboard symbolsKeyboard;
    private Keyboard symbolsKeyboard2;
    private Keyboard emojiKeyboard;
    private KeyboardSettings settings;
    private TranslationManager translationManager;
    private ClipboardManager clipboardManager;
    private KeyRemapping keyRemapping;
    private boolean isShifted = false;
    private boolean isCapsLock = false;
    private boolean isSecureField = false;
    private boolean isSymbolsPage = false;
    private boolean isSymbolsPage2 = false;
    private boolean isEmojiPage = false;
    private StringBuilder composing = new StringBuilder();
    private StringBuilder currentWord = new StringBuilder();
    private long lastShiftTime = 0;
    private long lastPressTime = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private CompletionInfo[] completions;

    public static JNetIME getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        settings = KeyboardSettings.getInstance(this);
        translationManager = new TranslationManager();
        clipboardManager = new ClipboardManager(this);
        keyRemapping = new KeyRemapping(this);
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
        suggestionBar.setBackgroundColor(0xFF2D2D2D);
        suggestionBar.setVisibility(View.GONE);
        suggestionBar.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(suggestionBar);

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
        int symId = getResources().getIdentifier("keyboard_symbols", "xml", getPackageName());
        int sym2Id = getResources().getIdentifier("keyboard_symbols2", "xml", getPackageName());
        int emojiId = getResources().getIdentifier("keyboard_emoji", "xml", getPackageName());

        if (ukId != 0) ukKeyboard = new Keyboard(this, ukId);
        if (usId != 0) usKeyboard = new Keyboard(this, usId);
        if (symId != 0) symbolsKeyboard = new Keyboard(this, symId);
        if (sym2Id != 0) symbolsKeyboard2 = new Keyboard(this, sym2Id);
        if (emojiId != 0) emojiKeyboard = new Keyboard(this, emojiId);

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
        java.util.List<String> suggestions = WordDictionary.getSuggestions(typed);
        if (suggestions.isEmpty() || typed.length() < 1) {
            hideSuggestions();
            return;
        }
        suggestionBar.removeAllViews();
        for (String s : suggestions) {
            TextView tv = new TextView(this);
            tv.setText(s);
            tv.setTextSize(16);
            tv.setTextColor(0xFFFFFFFF);
            tv.setPadding(16, 12, 16, 12);
            tv.setBackgroundColor(0xFF3C3C3C);
            tv.setOnClickListener(v -> acceptSuggestion(s));
            suggestionBar.addView(tv);
        }
        suggestionBar.setVisibility(View.VISIBLE);
    }

    private void acceptSuggestion(String suggestion) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        currentWord.setLength(0);
        hideSuggestions();
        String text = suggestion + " ";
        if (!isSecureField && settings.isUnicodeEnabled() && !"normal".equals(settings.getCurrentStyleId())) {
            text = UnicodeStyleDatabase.transform(suggestion, settings.getCurrentStyleId()) + " ";
        }
        ic.commitText(text, 1);
    }

    private void hideSuggestions() {
        if (suggestionBar != null) {
            suggestionBar.removeAllViews();
            suggestionBar.setVisibility(View.GONE);
        }
    }

    private void commitCurrentWord(InputConnection ic) {
        String word = currentWord.toString();
        if (word.isEmpty()) return;
        currentWord.setLength(0);
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
    }

    @Override
    public void onRelease(int primaryCode) {}

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
            case -108:
                switchToLetters();
                break;
            case -109:
                switchToSymbols2();
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
        } else if (currentWord.length() > 0) {
            currentWord.setLength(currentWord.length() - 1);
            if (currentWord.length() > 0) {
                ic.setComposingText(applyUnicode(currentWord.toString()), 1);
                if (settings.isSuggestionsEnabled()) updateSuggestions();
            } else {
                hideSuggestions();
                ic.finishComposingText();
            }
        } else if (composing.length() > 0) {
            composing.setLength(composing.length() - 1);
            ic.setComposingText(applyUnicode(composing.toString()), 1);
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
        if (!isSecureField && (settings.isSuggestionsEnabled() || settings.isAutoCorrectEnabled())
                && currentWord.length() > 0) {
            commitCurrentWord(ic);
        }
        if (!isSecureField && settings.isTranslationEnabled()) {
            if (composing.length() > 0) {
                ic.finishComposingText();
                composing.setLength(0);
            }
            translateWholeInputThenSend();
            return;
        }
        if (composing.length() > 0) {
            ic.finishComposingText();
            composing.setLength(0);
        }
        if (settings.isEnterSendsMessage()) {
            sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
        } else {
            ic.commitText("\n", 1);
        }
    }

    private void handleSpace(InputConnection ic) {
        if (!isSecureField && (settings.isSuggestionsEnabled() || settings.isAutoCorrectEnabled())
                && currentWord.length() > 0) {
            commitCurrentWord(ic);
            ic.commitText(" ", 1);
            return;
        }
        if (!isSecureField && settings.isTranslationEnabled() && composing.length() > 0) {
            String word = composing.toString();
            ic.setComposingText("", 1);
            composing.setLength(0);
            translateWord(word, " ");
            return;
        }
        ic.commitText(" ", 1);
    }

    private void handleCharacter(int primaryCode, InputConnection ic) {
        boolean isEmoji = isEmojiCodePoint(primaryCode);

        if (isEmoji) {
            String text = new String(Character.toChars(primaryCode));
            if (isEmojiPage && System.currentTimeMillis() - lastPressTime > 600) {
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

        boolean suggestOn = !isSecureField && (settings.isSuggestionsEnabled() || settings.isAutoCorrectEnabled());

        if (!isSecureField && settings.isTranslationEnabled() && isLetter) {
            composing.append(text);
            String display = unicodeOn
                    ? UnicodeStyleDatabase.transform(composing.toString(), settings.getCurrentStyleId())
                    : composing.toString();
            ic.setComposingText(display, 1);
            if (isShifted && !isCapsLock) {
                isShifted = false;
                if (keyboardView != null) keyboardView.setShifted(false);
            }
            return;
        }

        if (suggestOn && isLetter) {
            currentWord.append(text);
            String display = unicodeOn ? UnicodeStyleDatabase.transform(currentWord.toString(), settings.getCurrentStyleId())
                    : currentWord.toString();
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
        if (isEmojiPage) {
            switchToLetters();
        } else {
            isEmojiPage = true;
            isSymbolsPage = false;
            isSymbolsPage2 = false;
            if (emojiKeyboard != null && keyboardView != null) {
                keyboardView.setKeyboard(emojiKeyboard);
            }
        }
    }

    private void toggleSymbols() {
        if (isSymbolsPage) {
            switchToLetters();
        } else {
            isSymbolsPage = true;
            isSymbolsPage2 = false;
            isEmojiPage = false;
            if (symbolsKeyboard != null && keyboardView != null) {
                keyboardView.setKeyboard(symbolsKeyboard);
            }
        }
    }

    private void switchToSymbols2() {
        isSymbolsPage2 = true;
        isSymbolsPage = false;
        isEmojiPage = false;
        if (symbolsKeyboard2 != null && keyboardView != null) {
            keyboardView.setKeyboard(symbolsKeyboard2);
        }
    }

    private void switchToLetters() {
        isSymbolsPage = false;
        isSymbolsPage2 = false;
        isEmojiPage = false;
        if (currentKeyboard != null && keyboardView != null) {
            keyboardView.setKeyboard(currentKeyboard);
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
    public void swipeLeft() {}
    @Override
    public void swipeRight() {}
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
