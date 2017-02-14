package com.apps.scott.circumago.common.view.board.drawable;


import android.graphics.Canvas;

import com.apps.scott.circumago.common.view.board.canvas.Facelet;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Scott on 10/3/2016.
 */
public abstract class BoardDrawable extends Observable {
    protected ArrayList<Facelet> mCenters;
    protected ArrayList<Facelet> mFacelets;

    public BoardDrawable() {}

    public BoardDrawable(ArrayList<Facelet> centers, ArrayList<Facelet> facelets) {
        mCenters = centers;
        mFacelets = facelets;
    }

    public abstract void draw(Canvas canvas);
}
