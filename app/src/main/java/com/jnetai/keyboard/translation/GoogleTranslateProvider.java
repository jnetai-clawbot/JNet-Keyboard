package com.jnetai.keyboard.translation;

import com.jnetai.keyboard.diagnostics.Diagnostics;
import com.jnetai.keyboard.diagnostics.ErrorCodes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GoogleTranslateProvider implements TranslationProvider {

    @Override
    public String getName() { return "google"; }

    @Override
    public String getDisplayName() { return "Google Translate"; }

    @Override
    public String translate(String text, String sourceLang, String targetLang, String apiUrl, String apiKey) throws TranslationException {
        try {
            String url = (apiUrl != null && !apiUrl.isEmpty()) ? apiUrl
                    : "https://translation.googleapis.com/language/translate/v2";

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(15000);
            conn.setDoOutput(true);

            StringBuilder json = new StringBuilder("{");
            json.append("\"q\":\"").append(escapeJson(text)).append("\"");
            if (sourceLang != null && !sourceLang.isEmpty() && !sourceLang.equals("auto")) {
                json.append(",\"source\":\"").append(sourceLang).append("\"");
            }
            json.append(",\"target\":\"").append(targetLang).append("\"");
            json.append(",\"format\":\"text\"");
            json.append("}");

            if (apiKey != null && !apiKey.isEmpty()) {
                url += "?key=" + apiKey;
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(15000);
                conn.setDoOutput(true);
            }

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();
            if (code != 200) {
                Diagnostics.log(ErrorCodes.TR_002, "GoogleTranslate", "translate",
                        "HTTP " + code + " for text length " + text.length());
                throw new TranslationException(ErrorCodes.TR_002, "Translation API returned HTTP " + code);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) response.append(line);
            }

            String respStr = response.toString();
            String translated = extractTranslatedText(respStr);
            if (translated == null) {
                Diagnostics.log(ErrorCodes.TR_003, "GoogleTranslate", "translate",
                        "Invalid response: " + respStr.substring(0, Math.min(200, respStr.length())));
                throw new TranslationException(ErrorCodes.TR_003, "Invalid translation response");
            }
            return translated;

        } catch (TranslationException e) {
            throw e;
        } catch (java.net.SocketTimeoutException e) {
            Diagnostics.log(ErrorCodes.TR_004, "GoogleTranslate", "translate", e, "Timeout");
            throw new TranslationException(ErrorCodes.TR_004, "Translation timed out", e);
        } catch (java.io.IOException e) {
            Diagnostics.log(ErrorCodes.TR_001, "GoogleTranslate", "translate", e, "Network error");
            throw new TranslationException(ErrorCodes.TR_001, "Network error: " + e.getMessage(), e);
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.TR_002, "GoogleTranslate", "translate", e, "Unexpected error");
            throw new TranslationException(ErrorCodes.TR_002, "Translation error: " + e.getMessage(), e);
        }
    }

    private String extractTranslatedText(String json) {
        try {
            int idx = json.indexOf("\"translatedText\"");
            if (idx < 0) return null;
            int colon = json.indexOf(":", idx);
            if (colon < 0) return null;
            int start = json.indexOf("\"", colon);
            if (start < 0) return null;
            int end = json.indexOf("\"", start + 1);
            if (end < 0) return null;
            return unescapeJson(json.substring(start + 1, end));
        } catch (Exception e) {
            return null;
        }
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    private String unescapeJson(String s) {
        return s.replace("\\\"", "\"").replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t").replace("\\\\", "\\");
    }
}
