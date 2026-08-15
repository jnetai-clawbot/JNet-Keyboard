package com.jnetai.keyboard.translation;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jnetai.keyboard.diagnostics.Diagnostics;
import com.jnetai.keyboard.diagnostics.ErrorCodes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleTranslateProvider implements TranslationProvider {

    @Override
    public String getName() { return "google"; }

    @Override
    public String getDisplayName() { return "Google Translate"; }

    @Override
    public String translate(String text, String sourceLang, String targetLang, String apiUrl, String apiKey) throws TranslationException {
        try {
            String src = (sourceLang != null && !sourceLang.isEmpty() && !"auto".equals(sourceLang)) ? sourceLang : "auto";
            String baseUrl = (apiUrl != null && !apiUrl.isEmpty())
                    ? apiUrl
                    : "https://translate.googleapis.com/translate_a/single";
            String urlStr = baseUrl + "?client=gtx&sl=" + URLEncoder.encode(src, "UTF-8")
                    + "&tl=" + URLEncoder.encode(targetLang, "UTF-8")
                    + "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");
            if (apiKey != null && !apiKey.isEmpty()) {
                urlStr += "&key=" + URLEncoder.encode(apiKey, "UTF-8");
            }

            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36 Chrome/120.0 Mobile Safari/537.36");
            conn.setRequestProperty("Accept", "application/json, text/plain, */*");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(20000);

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
            JsonElement root = new Gson().fromJson(json, JsonElement.class);
            if (root == null || !root.isJsonArray()) return null;
            JsonArray outer = root.getAsJsonArray();
            if (outer.size() == 0) return null;
            JsonElement seg = outer.get(0);
            if (seg == null || !seg.isJsonArray()) return null;
            JsonArray segArr = seg.getAsJsonArray();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < segArr.size(); i++) {
                JsonElement item = segArr.get(i);
                if (item == null || !item.isJsonArray()) continue;
                JsonArray itemArr = item.getAsJsonArray();
                if (itemArr.size() > 0) {
                    JsonElement first = itemArr.get(0);
                    if (first != null && first.isJsonPrimitive()) {
                        sb.append(first.getAsString());
                    }
                }
            }
            if (sb.length() > 0) return sb.toString();
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
