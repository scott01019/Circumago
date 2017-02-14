package com.apps.scott.circumago.common.view.board.canvas.drawable;

import android.graphics.Canvas;

import com.apps.scott.circumago.common.twisty.node.Node;


/**
 * Created by Scott on 10/5/2016.
 */
public interface FaceletDrawable {
    void draw(Canvas canvas);
    void update(Node node);
    int getCenterX();
    int getCenterY();
}