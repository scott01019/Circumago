package com.apps.scott.circumago.common.twisty.board.move;

import com.apps.scott.circumago.common.twisty.node.Node;

/**
 * Created by Scott on 9/30/2016.
 */
public class Move {
    Node mCenter;
    Rotation mRotation;

    public Move(Node center, Rotation rotation) {
        mCenter = center;
        mRotation = rotation;
    }

    public Node getCenter() {
        return mCenter;
    }

    public Rotation getRotation() {
        return mRotation;
    }

    public void invertRotation() {
        mRotation = getInverse(mRotation);
    }

    public static Rotation getInverse(Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE: return Rotation.COUNTER_CLOCKWISE;
            case COUNTER_CLOCKWISE: return Rotation.CLOCKWISE;
        }
        return null;
    }

    public enum Rotation {
        CLOCKWISE,
        COUNTER_CLOCKWISE,
        REVERT
    }
}
