package com.apps.scott.circumago.common.utility;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;

/**
 * Created by Scott on 10/3/2016.
 */
public class CanvasUtil {
    public static void clearRegion(Bitmap bitmap, Rect rect) {
        Canvas canvas = new Canvas(bitmap);
        canvas.clipRect(rect);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public static void clearRegion(Canvas canvas, Rect rect) {
        canvas.save();
        canvas.clipRect(rect);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.restore();
    }

    public static void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }
}
