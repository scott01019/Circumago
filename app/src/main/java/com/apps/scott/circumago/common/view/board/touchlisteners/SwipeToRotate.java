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
public class SwipeToRotate implements BoardViewTouchListener {
    private float mDownX, mDownY, mUpX;

    private final float MIN_DISTANCE = 30.0f;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                mUpX = event.getX();
                float dx = mUpX - mDownX;

                if (dx < 0 && Math.abs(dx) > MIN_DISTANCE) {
                    for (Facelet center : ((BoardView) v).getCenterFacelets()) {
                        if (center.getBounds().contains((int) mDownX, (int) mDownY)) {
                            NodePosition position = ((BoardView) v).getCenterPositionFromFacelet(center);
                            ((BoardView) v).addMoveToQueue(new Move(
                                    ((BoardView) v).getNodeByPosition(position),
                                    Move.Rotation.COUNTER_CLOCKWISE
                            ));
                            return false;
                        }
                    }
                }
                if (dx > 0 && Math.abs(dx) > MIN_DISTANCE) {
                    for (Facelet center : ((BoardView) v).getCenterFacelets()) {
                        if (center.getBounds().contains((int) mDownX, (int) mDownY)) {
                            NodePosition position = ((BoardView) v).getCenterPositionFromFacelet(center);
                            ((BoardView) v).addMoveToQueue(new Move(
                                    ((BoardView) v).getNodeByPosition(position),
                                    Move.Rotation.CLOCKWISE
                            ));
                            return false;
                        }
                    }
                }
                return true;
        }
        return false;
    }

    @Override
    public void invertDirection() {}
}
