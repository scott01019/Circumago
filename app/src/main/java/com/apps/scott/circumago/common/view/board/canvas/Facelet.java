package com.apps.scott.circumago.common.view.board.canvas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.NodePosition;
import com.apps.scott.circumago.common.twisty.node.position.Position;
import com.apps.scott.circumago.common.utility.CanvasUtil;
import com.apps.scott.circumago.common.view.board.BoardSpec;
import com.apps.scott.circumago.common.view.board.canvas.drawable.FaceletDrawable;
import com.apps.scott.circumago.common.view.board.canvas.drawable.FaceletDrawableFactory;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Scott on 10/5/2016.
 */
public abstract class Facelet implements Observer {
    protected Node mNode;
    protected Rect mBounds;
    protected Bitmap mDrawableBitmap;
    protected Canvas mCanvas;
    protected FaceletDrawable mDrawable;

    public Facelet(Node node, boolean isCenter) {
        mNode = node;
        mDrawableBitmap = Bitmap.createBitmap(
                BoardSpec.CELL_DIM,
                BoardSpec.CELL_DIM,
                Bitmap.Config.ARGB_8888
        );
        mCanvas = new Canvas(mDrawableBitmap);
        mDrawable = FaceletDrawableFactory.createFaceletDrawable(node, isCenter);
        build();
        mDrawable.draw(mCanvas);
    }

    public void build() {
        int centerX = BoardSpec.BOARD_PADDING + BoardSpec.CELL_DIM / 2 +
                ((Position) mNode.getPosition()).getColumn() * BoardSpec.CELL_DIM;
        int centerY = BoardSpec.BOARD_PADDING + BoardSpec.CELL_DIM / 2 +
                ((Position) mNode.getPosition()).getRow() * BoardSpec.CELL_DIM;

        mBounds = new Rect(
                centerX - BoardSpec.CELL_DIM / 2,
                centerY - BoardSpec.CELL_DIM / 2,
                centerX + BoardSpec.CELL_DIM / 2,
                centerY + BoardSpec.CELL_DIM / 2
        );
    }

    public void drawStatic(Canvas canvas) {
        CanvasUtil.clearCanvas(mCanvas);
        mDrawable.draw(mCanvas);
        canvas.drawBitmap(mDrawableBitmap, mBounds.left, mBounds.top, null);
    }

    public NodePosition getPosition() { return mNode.getPosition(); }
    public Rect getBounds() { return mBounds; }

    public abstract void draw(Canvas canvas);
    public abstract void drawRotated(Canvas canvas, float angle, int centerX, int centerY);

    @Override
    public void update(Observable o, Object arg) {
        build();
        mDrawable.update(mNode);
    }

    public Node getNode() { return mNode; }
}