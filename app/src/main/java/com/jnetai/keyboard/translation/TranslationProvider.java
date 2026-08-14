package com.jnetai.keyboard.translation;

public interface TranslationProvider {
    String getName();
    String getDisplayName();
    String translate(String text, String sourceLang, String targetLang, String apiUrl, String apiKey) throws TranslationException;

    class TranslationException extends Exception {
        private final String errorCode;
        public TranslationException(String errorCode, String message) { super(message); this.errorCode = errorCode; }
        public TranslationException(String errorCode, String message, Throwable cause) { super(message, cause); this.errorCode = errorCode; }
        public String getErrorCode() { return errorCode; }
    }
}
