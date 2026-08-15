package com.jnetai.keyboard.ime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import java.util.List;

public class JNetKeyboardView extends KeyboardView {
    public JNetKeyboardView(Context context) {
        super(context, null);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Keyboard keyboard = getKeyboard();
        if (keyboard == null) return;
        List<Keyboard.Key> keys = keyboard.getKeys();
        for (Keyboard.Key key : keys) {
            if (key.codes != null && key.codes.length > 0 && key.codes[0] == -4 && key.label != null) {
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(0xFFFFFFFF);
                paint.setTextSize(getResources().getDimension(getResources().getIdentifier(
                        "enter_key_icon_size", "dimen", getContext().getPackageName())));
                float x = key.x + key.width / 2f;
                float y = key.y + key.height / 2f;
                Paint.FontMetrics fm = paint.getFontMetrics();
                canvas.drawText(key.label.toString(), x, y - (fm.ascent + fm.descent) / 2f, paint);
                break;
            }
        }
    }
}
