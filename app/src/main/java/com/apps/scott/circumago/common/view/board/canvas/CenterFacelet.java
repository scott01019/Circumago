package com.apps.scott.circumago.common.view.board.canvas;

import android.graphics.Canvas;

import com.apps.scott.circumago.common.twisty.node.Node;


/**
 * Created by Scott on 10/10/2016.
 */
public class CenterFacelet extends Facelet {
    public CenterFacelet(Node node, boolean isCenter) {
        super(node, isCenter);
    }

    @Override
    public void draw(Canvas canvas) {
        mDrawable.draw(mCanvas);
        canvas.drawBitmap(mDrawableBitmap, mBounds.left, mBounds.top, null);
    }

    @Override
    public void drawRotated(Canvas canvas, float angle, int centerX, int centerY) {
        canvas.save();
        canvas.rotate(angle, centerX, centerY);
        draw(canvas);
        canvas.restore();
    }
}
