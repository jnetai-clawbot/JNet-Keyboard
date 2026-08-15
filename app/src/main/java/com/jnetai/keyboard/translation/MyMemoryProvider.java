package com.jnetai.keyboard.translation;

import com.jnetai.keyboard.diagnostics.Diagnostics;
import com.jnetai.keyboard.diagnostics.ErrorCodes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MyMemoryProvider implements TranslationProvider {

    @Override
    public String getName() { return "mymemory"; }

    @Override
    public String getDisplayName() { return "MyMemory"; }

    @Override
    public String translate(String text, String sourceLang, String targetLang, String apiUrl, String apiKey) throws TranslationException {
        try {
            String src = (sourceLang != null && !sourceLang.isEmpty() && !"auto".equals(sourceLang)) ? sourceLang : "en";
            String urlStr = "https://api.mymemory.translated.net/get?q="
                    + URLEncoder.encode(text, "UTF-8")
                    + "&langpair=" + URLEncoder.encode(src, "UTF-8")
                    + "|" + URLEncoder.encode(targetLang, "UTF-8");
            if (apiKey != null && !apiKey.isEmpty()) {
                urlStr += "&key=" + URLEncoder.encode(apiKey, "UTF-8");
            }

            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(15000);

            int code = conn.getResponseCode();
            if (code != 200) {
                Diagnostics.log(ErrorCodes.TR_002, "MyMemory", "translate",
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
            if (translated == null || translated.isEmpty() || translated.equals("NO QUERY SPECIFIED. PLEASE SPECIFY A QUERY.")) {
                Diagnostics.log(ErrorCodes.TR_003, "MyMemory", "translate",
                        "Invalid response: " + respStr.substring(0, Math.min(200, respStr.length())));
                throw new TranslationException(ErrorCodes.TR_003, "Invalid translation response");
            }
            return translated;

        } catch (TranslationException e) {
            throw e;
        } catch (java.net.SocketTimeoutException e) {
            Diagnostics.log(ErrorCodes.TR_004, "MyMemory", "translate", e, "Timeout");
            throw new TranslationException(ErrorCodes.TR_004, "Translation timed out", e);
        } catch (java.io.IOException e) {
            Diagnostics.log(ErrorCodes.TR_001, "MyMemory", "translate", e, "Network error");
            throw new TranslationException(ErrorCodes.TR_001, "Network error: " + e.getMessage(), e);
        } catch (Exception e) {
            Diagnostics.log(ErrorCodes.TR_002, "MyMemory", "translate", e, "Unexpected error");
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

    private String unescapeJson(String s) {
        return s.replace("\\\"", "\"").replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t").replace("\\\\", "\\");
    }
}
