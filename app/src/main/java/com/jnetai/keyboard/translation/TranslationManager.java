package com.jnetai.keyboard.translation;

import com.jnetai.keyboard.diagnostics.Diagnostics;
import com.jnetai.keyboard.diagnostics.ErrorCodes;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TranslationManager {
    private final Map<String, TranslationProvider> providers = new LinkedHashMap<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private String currentProviderName = "libretranslate";

    public TranslationManager() {
        providers.put("libretranslate", new LibreTranslateProvider());
        providers.put("google", new GoogleTranslateProvider());
    }

    public TranslationProvider getCurrentProvider() {
        return providers.get(currentProviderName);
    }

    public TranslationProvider getProvider(String name) {
        return providers.get(name);
    }

    public void setCurrentProvider(String name) {
        if (providers.containsKey(name)) {
            currentProviderName = name;
        }
    }

    public String getCurrentProviderName() {
        return currentProviderName;
    }

    public Map<String, TranslationProvider> getProviders() {
        return providers;
    }

    public void translate(String text, String sourceLang, String targetLang,
                           String apiUrl, String apiKey, TranslationCallback callback) {
        executor.execute(() -> {
            try {
                TranslationProvider provider = getCurrentProvider();
                if (provider == null) {
                    callback.onError(ErrorCodes.TR_005, "No translation provider configured");
                    return;
                }
                String result = provider.translate(text, sourceLang, targetLang, apiUrl, apiKey);
                callback.onSuccess(result);
            } catch (TranslationProvider.TranslationException e) {
                Diagnostics.log(e.getErrorCode(), "TranslationManager", "translate", e, null);
                callback.onError(e.getErrorCode(), e.getMessage());
            } catch (Exception e) {
                Diagnostics.log(ErrorCodes.TR_002, "TranslationManager", "translate", e, null);
                callback.onError(ErrorCodes.TR_002, e.getMessage());
            }
        });
    }

    public interface TranslationCallback {
        void onSuccess(String translatedText);
        void onError(String errorCode, String message);
    }
}
