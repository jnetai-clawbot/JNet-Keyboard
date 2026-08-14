package com.jnetai.keyboard.clipboard;

import android.content.Context;
import com.jnetai.keyboard.diagnostics.Diagnostics;
import com.jnetai.keyboard.diagnostics.ErrorCodes;
import com.jnetai.keyboard.settings.KeyboardSettings;
import java.util.ArrayList;
import java.util.List;

public class ClipboardManager {
    private static final int MAX_ENTRIES = 50;
    private final KeyboardSettings settings;

    public ClipboardManager(Context context) {
        settings = KeyboardSettings.getInstance(context);
    }

    public void save(String text) {
        if (text == null || text.isEmpty()) return;
        try {
            int count = settings.getSavedClipboardCount();
            if (count >= MAX_ENTRIES) {
                for (int i = MAX_ENTRIES - 1; i >= 0; i--) {
                    String existing = settings.getSavedClipboard(i);
                    if (existing != null && existing.equals(text)) return;
                }
                for (int i = 0; i < MAX_ENTRIES - 1; i++) {
                    String next = settings.getSavedClipboard(i + 1);
                    if (next != null) settings.setSavedClipboard(i, next);
                    else settings.setSavedClipboard(i, null);
                }
                settings.setSavedClipboard(MAX_ENTRIES - 1, text);
            } else {
                for (int i = 0; i < count; i++) {
                    if (text.equals(settings.getSavedClipboard(i))) return;
                }
                settings.setSavedClipboard(count, text);
            }
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.CL_001, "ClipboardManager", "save", e, null);
        }
    }

    public List<String> getAll() {
        List<String> items = new ArrayList<>();
        try {
            int count = settings.getSavedClipboardCount();
            for (int i = 0; i < count; i++) {
                String text = settings.getSavedClipboard(i);
                if (text != null) items.add(text);
            }
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.CL_002, "ClipboardManager", "getAll", e, null);
        }
        return items;
    }

    public void delete(int index) {
        try {
            int count = settings.getSavedClipboardCount();
            for (int i = index; i < count - 1; i++) {
                String next = settings.getSavedClipboard(i + 1);
                if (next != null) settings.setSavedClipboard(i, next);
                else settings.setSavedClipboard(i, null);
            }
            settings.setSavedClipboard(count - 1, null);
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.CL_002, "ClipboardManager", "delete", e, null);
        }
    }

    public void clear() {
        try {
            int count = settings.getSavedClipboardCount();
            for (int i = 0; i < count; i++) {
                settings.setSavedClipboard(i, null);
            }
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.CL_002, "ClipboardManager", "clear", e, null);
        }
    }
}
