package com.jnetai.keyboard.remapping;

import android.content.Context;
import com.jnetai.keyboard.diagnostics.Diagnostics;
import com.jnetai.keyboard.diagnostics.ErrorCodes;
import com.jnetai.keyboard.settings.KeyboardSettings;
import java.util.LinkedHashMap;
import java.util.Map;

public class KeyRemapping {
    private final KeyboardSettings settings;

    public KeyRemapping(Context context) {
        settings = KeyboardSettings.getInstance(context);
    }

    public String getRemappedValue(String keyCode) {
        try {
            return settings.getRemapping(keyCode);
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.RM_001, "KeyRemapping", "getRemappedValue", e, null);
            return null;
        }
    }

    public void setRemapping(String keyCode, String value) {
        try {
            if (value == null || value.isEmpty()) {
                settings.clearRemapping(keyCode);
            } else {
                settings.setRemapping(keyCode, value);
            }
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.RM_002, "KeyRemapping", "setRemapping", e, null);
        }
    }

    public Map<String, String> getAllRemappings() {
        Map<String, String> result = new LinkedHashMap<>();
        try {
            for (int i = 0; i < 256; i++) {
                String val = settings.getRemapping(String.valueOf(i));
                if (val != null) result.put(String.valueOf(i), val);
            }
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.RM_001, "KeyRemapping", "getAllRemappings", e, null);
        }
        return result;
    }

    public void resetAll() {
        try {
            settings.clearAllRemappings();
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.RM_002, "KeyRemapping", "resetAll", e, null);
        }
    }
}
