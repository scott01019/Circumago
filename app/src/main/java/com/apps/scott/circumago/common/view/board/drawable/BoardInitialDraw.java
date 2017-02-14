package com.apps.scott.circumago.common.view.board.drawable;


import android.graphics.Canvas;

import com.apps.scott.circumago.common.utility.CanvasUtil;
import com.apps.scott.circumago.common.view.board.canvas.Facelet;

import java.util.ArrayList;

/**
 * Created by Scott on 10/3/2016.
 */
public class BoardInitialDraw extends BoardDrawable {
    public BoardInitialDraw(ArrayList<Facelet> centers, ArrayList<Facelet> facelets) {
        super(centers, facelets);
        init();
    }

    private void init() {
        for (Facelet center : mCenters) addObserver(center);
        for (Facelet facelet : mFacelets) addObserver(facelet);
    }

    public void draw(Canvas canvas) {
        setChanged();
        notifyObservers();
        for (Facelet center : mCenters) center.drawStatic(canvas);
        for (Facelet facelet : mFacelets) facelet.drawStatic(canvas);
    }
}
