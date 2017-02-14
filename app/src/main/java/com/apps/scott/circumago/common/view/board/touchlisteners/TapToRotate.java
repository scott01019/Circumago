package com.apps.scott.circumago.common.view.board.touchlisteners;

import android.view.MotionEvent;
import android.view.View;

import com.apps.scott.circumago.common.twisty.board.move.Move;
import com.apps.scott.circumago.common.twisty.node.NodePosition;
import com.apps.scott.circumago.common.view.board.BoardView;
import com.apps.scott.circumago.common.view.board.canvas.Facelet;


/**
 * Created by Scott on 10/2/2016.
 */
public class TapToRotate implements BoardViewTouchListener {
    private Move.Rotation mRotationDirection = Move.Rotation.CLOCKWISE;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        for (Facelet center : ((BoardView) v).getCenterFacelets()) {
            if (center.getBounds().contains((int) event.getX(),(int) event.getY())) {
                NodePosition position = ((BoardView) v).getCenterPositionFromFacelet(center);
                ((BoardView) v).addMoveToQueue(new Move(
                        ((BoardView) v).getNodeByPosition(position),
                        mRotationDirection
                ));
            }
        }
        return false;
    }

    public void invertDirection() {
        mRotationDirection = Move.getInverse(mRotationDirection);
    }
}
