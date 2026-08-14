package com.jnetai.keyboard.ime;

import android.inputmethodservice.CandidateView;
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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import com.jnetai.keyboard.clipboard.ClipboardManager;
import com.jnetai.keyboard.diagnostics.Diagnostics;
import com.jnetai.keyboard.diagnostics.ErrorCodes;
import com.jnetai.keyboard.remapping.KeyRemapping;
import com.jnetai.keyboard.settings.KeyboardSettings;
import com.jnetai.keyboard.translation.TranslationManager;
import com.jnetai.keyboard.unicode.UnicodeStyleDatabase;
import java.util.ArrayList;
import java.util.List;

public class JNetIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private static JNetIME instance;
    private KeyboardView keyboardView;
    private CandidateView candidateView;
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
    private long lastShiftTime = 0;
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
        View root = getLayoutInflater().inflate(
                getResources().getIdentifier("keyboard_view", "layout", getPackageName()), null);
        keyboardView = root.findViewById(getResources().getIdentifier("keyboard", "id", getPackageName()));
        candidateView = root.findViewById(getResources().getIdentifier("candidates", "id", getPackageName()));

        if (keyboardView == null) {
            keyboardView = new KeyboardView(this, null);
        }
        keyboardView.setOnKeyboardActionListener(this);
        keyboardView.setPreviewEnabled(false);

        if (candidateView != null) {
            candidateView.setService(this);
        }

        loadKeyboards();
        applyTheme();
        return root;
    }

    @Override
    public View onCreateCandidatesView() {
        return candidateView;
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
            Diagnostics.info("JNetIME", "onStartInputView", "Secure field detected, disabling translation/transformation");
        }
        if (keyboardView != null && currentKeyboard != null) {
            keyboardView.setKeyboard(currentKeyboard);
        }
        updateCandidates();
    }

    @Override
    public void onDisplayCompletions(CompletionInfo[] completions) {
        if (completions == null) {
            this.completions = null;
            if (candidateView != null) candidateView.setVisibility(View.GONE);
            return;
        }
        this.completions = completions;
        if (candidateView != null) {
            candidateView.setSuggestions(completions, true, true);
            candidateView.setVisibility(View.VISIBLE);
        }
    }

    private void updateCandidates() {
        if (candidateView == null) return;
        if (completions != null && completions.length > 0) {
            candidateView.setSuggestions(completions, true, true);
            candidateView.setVisibility(View.VISIBLE);
        } else {
            candidateView.setVisibility(View.GONE);
        }
    }

    @Override
    public void pickSuggestionManually(int index) {
        if (completions != null && index >= 0 && index < completions.length) {
            CompletionInfo ci = completions[index];
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                ic.commitCompletion(ci);
            }
            completions = null;
            updateCandidates();
        }
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
        } else if (composing.length() > 0) {
            composing.setLength(composing.length() - 1);
            ic.setComposingText(composing, 1);
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
        ic.commitText("\n", 1);
    }

    private void handleSpace(InputConnection ic) {
        ic.commitText(" ", 1);
    }

    private void handleCharacter(int primaryCode, InputConnection ic) {
        if (isShifted || isCapsLock) {
            primaryCode = Character.toUpperCase(primaryCode);
        }
        String text = String.valueOf((char) primaryCode);

        if (!isSecureField && settings.isUnicodeEnabled()) {
            String styleId = settings.getCurrentStyleId();
            text = UnicodeStyleDatabase.transform(text, styleId);
        }

        if (!isSecureField && settings.isTranslationEnabled()) {
            composing.append(text);
            ic.setComposingText(composing, 1);
            translateAndCommit(ic);
        } else {
            ic.commitText(text, 1);
        }

        if (isShifted && !isCapsLock) {
            isShifted = false;
            if (keyboardView != null) keyboardView.setShifted(false);
        }
    }

    private void translateAndCommit(InputConnection ic) {
        String sourceText = composing.toString();
        String sourceLang = settings.isAutoDetectSource() ? "auto" : settings.getSourceLanguage();
        String targetLang = settings.getDestinationLanguage();
        String apiUrl = settings.getApiUrl();
        String apiKey = settings.getApiKey();

        translationManager.setCurrentProvider(settings.getTranslationProvider());
        translationManager.translate(sourceText, sourceLang, targetLang, apiUrl, apiKey,
                new TranslationManager.TranslationCallback() {
                    @Override
                    public void onSuccess(String translatedText) {
                        handler.post(() -> {
                            InputConnection conn = getCurrentInputConnection();
                            if (conn != null) {
                                conn.finishComposingText();
                                conn.commitText(translatedText, 1);
                            }
                            composing.setLength(0);
                        });
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        handler.post(() -> {
                            InputConnection conn = getCurrentInputConnection();
                            if (conn != null) {
                                conn.finishComposingText();
                                conn.commitText(sourceText, 1);
                            }
                            composing.setLength(0);
                        });
                    }
                });
    }

    private void handleManualTranslation(InputConnection ic) {
        if (isSecureField) return;
        CharSequence selected = ic.getSelectedText(0);
        String text;
        if (selected != null && selected.length() > 0) {
            text = selected.toString();
        } else {
            text = composing.toString();
            if (text.isEmpty()) {
                ic.getExtractedText(new android.view.inputmethod.ExtractedTextRequest(), 0);
                return;
            }
        }

        String sourceLang = settings.isAutoDetectSource() ? "auto" : settings.getSourceLanguage();
        String targetLang = settings.getDestinationLanguage();
        String apiUrl = settings.getApiUrl();
        String apiKey = settings.getApiKey();

        translationManager.setCurrentProvider(settings.getTranslationProvider());
        translationManager.translate(text, sourceLang, targetLang, apiUrl, apiKey,
                new TranslationManager.TranslationCallback() {
                    @Override
                    public void onSuccess(String translatedText) {
                        handler.post(() -> {
                            InputConnection conn = getCurrentInputConnection();
                            if (conn != null) {
                                conn.commitText(translatedText, 1);
                            }
                        });
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        Diagnostics.log(errorCode, "JNetIME", "manualTranslate", message);
                    }
                });
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
