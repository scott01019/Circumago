package com.apps.scott.circumago.common.view.board.canvas.drawable.color;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;

import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.color.ColorNode;
import com.apps.scott.circumago.common.utility.Colors;
import com.apps.scott.circumago.common.view.board.BoardSpec;
import com.apps.scott.circumago.common.view.board.canvas.drawable.FaceletDrawable;


/**
 * Created by Scott on 10/10/2016.
 */
public class ColorCenterFaceletDrawable implements FaceletDrawable {
    private ShapeDrawable mShape;

    public ColorCenterFaceletDrawable(ColorNode node) {
        mShape = new ShapeDrawable(new RectShape());
        mShape.setBounds(new Rect(
                BoardSpec.CENTER_CELL_PADDING,
                BoardSpec.CENTER_CELL_PADDING,
                BoardSpec.CENTER_CELL_PADDING + BoardSpec.CENTER_BLOCK_DIM,
                BoardSpec.CENTER_CELL_PADDING + BoardSpec.CENTER_BLOCK_DIM
        ));

        mShape.getPaint().setColor(Colors.getColor(node.getBotLeft()));
    }

    public void draw(Canvas canvas) {
        mShape.draw(canvas);
    }

    @Override
    public void update(Node node) {}

    @Override
    public int getCenterX() {
        return mShape.getBounds().centerX();
    }

    @Override
    public int getCenterY() {
        return mShape.getBounds().centerY();
    }
}