package com.jnetai.keyboard.ime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JNetKeyboardView extends KeyboardView {
    private Map<Integer, String> phraseLabels;

    public JNetKeyboardView(Context context) {
        super(context, null);
    }

    public void setPhraseLabels(Map<Integer, String> labels) {
        this.phraseLabels = labels;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Keyboard keyboard = getKeyboard();
        if (keyboard == null) return;
        List<Keyboard.Key> keys = keyboard.getKeys();
        for (Keyboard.Key key : keys) {
            if (key.codes != null && key.codes.length > 0) {
                int code = key.codes[0];
                if (code == -4 && key.label != null) {
                    drawEnterIcon(canvas, key);
                } else if (code >= -212 && code <= -201) {
                    drawPhraseLabel(canvas, key);
                }
            }
        }
    }

    private void drawEnterIcon(Canvas canvas, Keyboard.Key key) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(0xFFFFFFFF);
        paint.setTextSize(getResources().getDimension(getResources().getIdentifier(
                "enter_key_icon_size", "dimen", getContext().getPackageName())));
        float x = key.x + key.width / 2f;
        float y = key.y + key.height / 2f;
        Paint.FontMetrics fm = paint.getFontMetrics();
        canvas.drawText(key.label.toString(), x, y - (fm.ascent + fm.descent) / 2f, paint);
    }

    private void drawPhraseLabel(Canvas canvas, Keyboard.Key key) {
        String text = phraseLabels != null ? phraseLabels.get(key.codes[0]) : null;
        if (text == null || text.isEmpty()) return;
        float density = getResources().getDisplayMetrics().density;
        float inset = 4 * density;
        float availW = key.width - inset * 2;
        float availH = key.height - inset * 2;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFFFFFFF);
        paint.setTextAlign(Paint.Align.LEFT);

        float size = Math.min(key.height * 0.30f, 18 * density);
        float minSize = 8 * density;
        List<String> lines = new ArrayList<>();
        for (; size >= minSize; size -= 0.5f * density) {
            paint.setTextSize(size);
            lines = wrapText(text, paint, availW);
            float lineH = paint.getFontSpacing();
            if (lines.size() * lineH <= availH) break;
        }
        if (size < minSize) {
            paint.setTextSize(minSize);
            lines = wrapText(text, paint, availW);
            lines = ellipsizeToFit(lines, paint, availW, availH);
        }

        float lineH = paint.getFontSpacing();
        float totalH = lines.size() * lineH;
        float startY = key.y + (key.height - totalH) / 2f - paint.getFontMetrics().ascent;
        for (String line : lines) {
            float w = paint.measureText(line);
            canvas.drawText(line, key.x + (key.width - w) / 2f, startY, paint);
            startY += lineH;
        }
    }

    private List<String> wrapText(String text, Paint paint, float maxWidth) {
        List<String> lines = new ArrayList<>();
        if (text.isEmpty()) {
            lines.add("");
            return lines;
        }
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            String test = line.length() == 0 ? word : line + " " + word;
            if (paint.measureText(test) <= maxWidth) {
                line.setLength(0);
                line.append(test);
            } else {
                if (line.length() > 0) {
                    lines.add(line.toString());
                    line.setLength(0);
                }
                if (paint.measureText(word) > maxWidth) {
                    StringBuilder chunk = new StringBuilder();
                    for (int i = 0; i < word.length(); i++) {
                        String c = String.valueOf(word.charAt(i));
                        String t = chunk.toString() + c;
                        if (paint.measureText(t) <= maxWidth) {
                            chunk.append(c);
                        } else {
                            lines.add(chunk.toString());
                            chunk.setLength(0);
                            chunk.append(c);
                        }
                    }
                    line.append(chunk);
                } else {
                    line.append(word);
                }
            }
        }
        if (line.length() > 0) lines.add(line.toString());
        return lines;
    }

    private List<String> ellipsizeToFit(List<String> lines, Paint paint, float maxWidth, float maxHeight) {
        float lineH = paint.getFontSpacing();
        int maxLines = (int) (maxHeight / lineH);
        if (maxLines < 1) maxLines = 1;
        List<String> result = new ArrayList<>(lines);
        if (result.size() > maxLines) {
            result = new ArrayList<>(result.subList(0, maxLines));
        }
        String last = result.get(result.size() - 1);
        String ellipsized = last;
        while (paint.measureText(ellipsized + "…") > maxWidth && ellipsized.length() > 0) {
            ellipsized = ellipsized.substring(0, ellipsized.length() - 1);
        }
        if (last.length() > ellipsized.length()) {
            result.set(result.size() - 1, ellipsized + "…");
        }
        return result;
    }
}