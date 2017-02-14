package com.apps.scott.circumago.common.view.board.canvas;


import android.graphics.Canvas;

import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.utility.CanvasUtil;

/**
 * Created by Scott on 10/10/2016.
 */
public class PermutableFacelet extends Facelet {

    public PermutableFacelet(Node node, boolean isCenter) {
        super(node, isCenter);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mDrawableBitmap, mBounds.left, mBounds.top, null);
    }

    @Override
    public void drawRotated(Canvas canvas, float angle, int centerX, int centerY) {
        CanvasUtil.clearCanvas(mCanvas);
        mCanvas.save();
        mCanvas.rotate(360.0f - angle, mDrawable.getCenterX(), mDrawable.getCenterY());
        mDrawable.draw(mCanvas);
        mCanvas.restore();

        canvas.save();
        canvas.rotate(angle, centerX, centerY);
        draw(canvas);
        canvas.restore();
    }
}
