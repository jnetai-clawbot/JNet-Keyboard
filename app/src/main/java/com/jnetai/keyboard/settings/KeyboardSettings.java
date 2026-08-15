package com.jnetai.keyboard.settings;

import android.content.Context;
import android.content.SharedPreferences;
import com.jnetai.keyboard.diagnostics.Diagnostics;
import com.jnetai.keyboard.diagnostics.ErrorCodes;
import com.jnetai.keyboard.unicode.UnicodeStyleDatabase;

public class KeyboardSettings {
    private static final String PREFS_NAME = "jnet_keyboard_prefs";
    private static KeyboardSettings instance;
    private final SharedPreferences prefs;

    private Context appContext;

    private KeyboardSettings(Context context) {
        appContext = context.getApplicationContext();
        prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized KeyboardSettings getInstance(Context context) {
        if (instance == null) {
            instance = new KeyboardSettings(context.getApplicationContext());
        }
        return instance;
    }

    public boolean isDarkTheme() {
        String theme = prefs.getString("theme", "dark");
        if ("system".equals(theme)) {
            return (context().getResources().getConfiguration().uiMode
                    & android.content.res.Configuration.UI_MODE_NIGHT_MASK)
                    == android.content.res.Configuration.UI_MODE_NIGHT_YES;
        }
        return !"light".equals(theme);
    }

    public String getTheme() { return prefs.getString("theme", "dark"); }
    public void setTheme(String theme) { prefs.edit().putString("theme", theme).apply(); }

    public String getKeyboardLayout() { return prefs.getString("keyboard_layout", "uk"); }
    public void setKeyboardLayout(String layout) { prefs.edit().putString("keyboard_layout", layout).apply(); }

    public boolean isNumberRowEnabled() { return prefs.getBoolean("number_row", true); }
    public void setNumberRowEnabled(boolean enabled) { prefs.edit().putBoolean("number_row", enabled).apply(); }

    public boolean isHapticFeedback() { return prefs.getBoolean("haptic_feedback", false); }
    public void setHapticFeedback(boolean enabled) { prefs.edit().putBoolean("haptic_feedback", enabled).apply(); }

    public boolean isKeySound() { return prefs.getBoolean("key_sound", false); }
    public void setKeySound(boolean enabled) { prefs.edit().putBoolean("key_sound", enabled).apply(); }

    public String getCurrentStyleId() { return prefs.getString("current_style", UnicodeStyleDatabase.getDefaultStyleId()); }
    public void setCurrentStyleId(String styleId) { prefs.edit().putString("current_style", styleId).apply(); }

    public boolean isUnicodeEnabled() { return prefs.getBoolean("unicode_enabled", true); }
    public void setUnicodeEnabled(boolean enabled) { prefs.edit().putBoolean("unicode_enabled", enabled).apply(); }

    public boolean isTranslationEnabled() { return prefs.getBoolean("translation_enabled", false); }
    public void setTranslationEnabled(boolean enabled) { prefs.edit().putBoolean("translation_enabled", enabled).apply(); }

    public String getSourceLanguage() { return prefs.getString("source_lang", "auto"); }
    public void setSourceLanguage(String lang) { prefs.edit().putString("source_lang", lang).apply(); }

    public boolean isAutoDetectSource() { return prefs.getBoolean("auto_detect_source", true); }
    public void setAutoDetectSource(boolean auto) { prefs.edit().putBoolean("auto_detect_source", auto).apply(); }

    public String getDestinationLanguage() { return prefs.getString("dest_lang", "en"); }
    public void setDestinationLanguage(String lang) { prefs.edit().putString("dest_lang", lang).apply(); }

    public String getTranslationProvider() { return prefs.getString("translation_provider", "google"); }
    public void setTranslationProvider(String provider) { prefs.edit().putString("translation_provider", provider).apply(); }

    public String getApiUrl() { return prefs.getString("api_url", ""); }
    public void setApiUrl(String url) { prefs.edit().putString("api_url", url).apply(); }

    public String getApiKey() { return prefs.getString("api_key", ""); }
    public void setApiKey(String key) { prefs.edit().putString("api_key", key).apply(); }

    public int getKeySize() { return prefs.getInt("key_size", 100); }
    public void setKeySize(int size) { prefs.edit().putInt("key_size", size).apply(); }

    public int getKeyboardHeight() { return prefs.getInt("keyboard_height", 100); }
    public void setKeyboardHeight(int height) { prefs.edit().putInt("keyboard_height", height).apply(); }

    public boolean isCompactLayout() { return prefs.getBoolean("compact_layout", false); }
    public void setCompactLayout(boolean compact) { prefs.edit().putBoolean("compact_layout", compact).apply(); }

    public boolean isAutoCorrectEnabled() { return prefs.getBoolean("auto_correct", false); }
    public void setAutoCorrectEnabled(boolean enabled) { prefs.edit().putBoolean("auto_correct", enabled).apply(); }

    public boolean isEnterSendsMessage() { return prefs.getBoolean("enter_sends_message", true); }
    public void setEnterSendsMessage(boolean enabled) { prefs.edit().putBoolean("enter_sends_message", enabled).apply(); }

    public boolean isStyleFavourite(String styleId) {
        return prefs.getBoolean("fav_" + styleId, false);
    }
    public void setStyleFavourite(String styleId, boolean fav) {
        prefs.edit().putBoolean("fav_" + styleId, fav).apply();
    }

    public String getRemapping(String keyCode) {
        return prefs.getString("remap_" + keyCode, null);
    }
    public void setRemapping(String keyCode, String value) {
        prefs.edit().putString("remap_" + keyCode, value).apply();
    }
    public void clearRemapping(String keyCode) {
        prefs.edit().remove("remap_" + keyCode).apply();
    }
    public void clearAllRemappings() {
        SharedPreferences.Editor editor = prefs.edit();
        for (String key : prefs.getAll().keySet()) {
            if (key.startsWith("remap_")) editor.remove(key);
        }
        editor.apply();
    }

    public String getSavedClipboard(int index) {
        return prefs.getString("clipboard_" + index, null);
    }
    public void setSavedClipboard(int index, String text) {
        prefs.edit().putString("clipboard_" + index, text).apply();
    }
    public int getSavedClipboardCount() {
        int count = 0;
        for (String key : prefs.getAll().keySet()) {
            if (key.startsWith("clipboard_")) count++;
        }
        return count;
    }

    private Context context() {
        return appContext;
    }
}
