package com.apps.scott.circumago.common.view.board.drawable;


import android.graphics.Canvas;
import android.graphics.Rect;

import com.apps.scott.circumago.common.twisty.board.move.Move;
import com.apps.scott.circumago.common.twisty.node.NodePosition;
import com.apps.scott.circumago.common.utility.CanvasUtil;
import com.apps.scott.circumago.common.view.board.BoardSpec;
import com.apps.scott.circumago.common.view.board.canvas.Facelet;

import java.util.ArrayList;

/**
 * Created by Scott on 10/3/2016.
 */
public class BoardMoveDraw extends BoardDrawable {
    protected Move mCurrentMove;
    protected Facelet mRotationCenter;
    protected ArrayList<Facelet> mRotatingFacelets;
    protected ArrayList<Facelet> mRegionCenters;
    protected ArrayList<Facelet> mRegionFacelets;

    protected Rect mRotationRegionBounds;
    protected int mCount = 0;
    protected int mMaxCount = 6;
    protected float mAngle = 90.0f / mMaxCount;

    public BoardMoveDraw(ArrayList<Facelet> centers, ArrayList<Facelet> facelets,
                         Move move, Facelet rotationCenter) {
        super(centers, facelets);
        mCurrentMove = move;
        mRotationCenter = rotationCenter;
        init();
    }

    public BoardMoveDraw(ArrayList<Facelet> centers, ArrayList<Facelet> facelets,
                         Move move, Facelet rotationCenter, int maxCount) {
        super(centers, facelets);
        setMaxCount(maxCount);
        mCurrentMove = move;
        mRotationCenter = rotationCenter;
        init();
    }

    private void init() {
        mRegionCenters = new ArrayList<>();
        mRegionFacelets = new ArrayList<>();
        mRotatingFacelets = new ArrayList<>();

        addObserver(mRotationCenter);
        NodePosition centerPos = mRotationCenter.getPosition();

        for (Facelet facelet : mCenters) {
            int distanceSquared = centerPos.distanceSquared(facelet.getPosition());
            if (distanceSquared == 4 || distanceSquared == 8 || distanceSquared == 5)
                mRegionCenters.add(facelet);
        }

        for (Facelet facelet : mFacelets) {
            int distanceSquared = centerPos.distanceSquared(facelet.getPosition());
            if (distanceSquared == 5 || distanceSquared == 4 || distanceSquared == 8)
                mRegionFacelets.add(facelet);
            else if (distanceSquared == 1 || distanceSquared == 2) {
                addObserver(facelet);
                mRotatingFacelets.add(facelet);
            }
        }

        Rect centerBounds = mRotationCenter.getBounds();

        int regionLeft, regionTop, regionWidth, regionHeight;

        if (centerBounds.left - 2 * BoardSpec.CELL_DIM < 0) regionLeft = 0;
        else regionLeft = centerBounds.left - 2 * BoardSpec.CELL_DIM;

        if (centerBounds.top - 2 * BoardSpec.CELL_DIM < 0) regionTop = 0;
        else regionTop = centerBounds.top - 2 * BoardSpec.CELL_DIM;

        if (centerBounds.right + 2 * BoardSpec.CELL_DIM < BoardSpec.CANVAS_DIM)
            regionWidth = centerBounds.right + 2 * BoardSpec.CELL_DIM - regionLeft;
        else regionWidth = BoardSpec.CANVAS_DIM - regionLeft;

        if (centerBounds.bottom + BoardSpec.CELL_DIM < BoardSpec.CANVAS_DIM)
            regionHeight = centerBounds.bottom + 2* BoardSpec.CELL_DIM - regionTop;
        else regionHeight = BoardSpec.CANVAS_DIM - regionTop;

        mRotationRegionBounds = new Rect(
                regionLeft,
                regionTop,
                regionLeft + regionWidth,
                regionTop + regionHeight
        );
    }

    public void setMaxCount(int maxCount) {
        mMaxCount = maxCount;
        mAngle = 90.0f / mMaxCount;
    }

    public void drawMove(Canvas canvas) {
        CanvasUtil.clearRegion(canvas, mRotationRegionBounds);


        for (Facelet facelet : mRegionFacelets) facelet.drawStatic(canvas);
        for (Facelet facelet : mRegionCenters) facelet.drawStatic(canvas);

        float angle = 0.0f;
        switch(mCurrentMove.getRotation()) {
            case CLOCKWISE: angle = mAngle * mCount;
                break;
            case COUNTER_CLOCKWISE: angle = 360.0f - mAngle * mCount;
                break;
        }

        mRotationCenter.drawRotated(
                canvas,
                angle,
                mRotationCenter.getBounds().centerX(),
                mRotationCenter.getBounds().centerY()
        );

        for (Facelet facelet : mRotatingFacelets)
            facelet.drawRotated(
                    canvas,
                    angle,
                    mRotationCenter.getBounds().centerX(),
                    mRotationCenter.getBounds().centerY()
            );
    }

    @Override
    public void draw(Canvas canvas) {
        drawMove(canvas);
        ++mCount;
        if (mCount > mMaxCount) {
            setChanged();
            notifyObservers();
            deleteObservers();
        }
    }
}
