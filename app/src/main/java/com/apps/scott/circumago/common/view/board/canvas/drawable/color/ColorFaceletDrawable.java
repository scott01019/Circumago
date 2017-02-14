package com.apps.scott.circumago.common.view.board.canvas.drawable.color;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;


import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.color.ColorNode;
import com.apps.scott.circumago.common.utility.CanvasUtil;
import com.apps.scott.circumago.common.utility.Colors;
import com.apps.scott.circumago.common.view.board.BoardSpec;
import com.apps.scott.circumago.common.view.board.canvas.drawable.FaceletDrawable;

import java.util.ArrayList;

/**
 * Created by Scott on 10/5/2016.
 */
public class ColorFaceletDrawable implements FaceletDrawable {
    private ArrayList<ShapeDrawable> mShapes;

    public ColorFaceletDrawable(ColorNode node) {
        mShapes = new ArrayList<>();

        mShapes.add(new ShapeDrawable(new RectShape()));
        mShapes.add(new ShapeDrawable(new RectShape()));
        mShapes.add(new ShapeDrawable(new RectShape()));
        mShapes.add(new ShapeDrawable(new RectShape()));

        update(node);
        init();
    }

    private void init() {
        int centerX = BoardSpec.CELL_DIM / 2;
        int centerY = BoardSpec.CELL_DIM / 2;

        mShapes.get(0).setBounds(new Rect(
                centerX - BoardSpec.QUAD_CELL_CENTER_OFFSET - BoardSpec.QUAD_BLOCK_DIM,
                centerY - BoardSpec.QUAD_CELL_CENTER_OFFSET - BoardSpec.QUAD_BLOCK_DIM,
                centerX - BoardSpec.QUAD_CELL_CENTER_OFFSET,
                centerY - BoardSpec.QUAD_CELL_CENTER_OFFSET
        ));

        mShapes.get(1).setBounds(new Rect(
                centerX + BoardSpec.QUAD_CELL_CENTER_OFFSET,
                centerY - BoardSpec.QUAD_CELL_CENTER_OFFSET - BoardSpec.QUAD_BLOCK_DIM,
                centerX + BoardSpec.QUAD_CELL_CENTER_OFFSET + BoardSpec.QUAD_BLOCK_DIM,
                centerY - BoardSpec.QUAD_CELL_CENTER_OFFSET
        ));

        mShapes.get(2).setBounds(new Rect(
                centerX - BoardSpec.QUAD_CELL_CENTER_OFFSET - BoardSpec.QUAD_BLOCK_DIM,
                centerY + BoardSpec.QUAD_CELL_CENTER_OFFSET,
                centerX - BoardSpec.QUAD_CELL_CENTER_OFFSET,
                centerY + BoardSpec.QUAD_CELL_CENTER_OFFSET + BoardSpec.QUAD_BLOCK_DIM
        ));

        mShapes.get(3).setBounds(new Rect(
                centerX + BoardSpec.QUAD_CELL_CENTER_OFFSET,
                centerY + BoardSpec.QUAD_CELL_CENTER_OFFSET,
                centerX + BoardSpec.QUAD_CELL_CENTER_OFFSET + BoardSpec.QUAD_BLOCK_DIM,
                centerY + BoardSpec.QUAD_CELL_CENTER_OFFSET + BoardSpec.QUAD_BLOCK_DIM
        ));
    }

    public void setColor(ShapeDrawable shape, int color) {
        shape.getPaint().setColor(Colors.getColor(color));
    }

    @Override
    public void draw(Canvas canvas) {
        for (ShapeDrawable shape : mShapes)
            if (shape.getPaint().getColor() != Colors.BACKGROUND_COLOR)
                shape.draw(canvas);
            else
                CanvasUtil.clearRegion(canvas, shape.getBounds());
    }

    @Override
    public void update(Node node) {
        setColor(mShapes.get(0), ((ColorNode) node).getTopLeft());
        setColor(mShapes.get(1), ((ColorNode) node).getTopRight());
        setColor(mShapes.get(2), ((ColorNode) node).getBotLeft());
        setColor(mShapes.get(3), ((ColorNode) node).getBotRight());
    }

    @Override
    public int getCenterX() {
        return BoardSpec.CELL_DIM / 2;
    }

    @Override
    public int getCenterY() {
        return BoardSpec.CELL_DIM / 2;
    }
}
