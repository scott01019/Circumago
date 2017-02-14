package com.apps.scott.circumago.common.twisty.node.color;

import android.os.Parcel;

import com.apps.scott.circumago.common.twisty.node.NodePosition;
import com.apps.scott.circumago.common.twisty.node.position.Position;

/**
 * Created by Scott on 10/17/2016.
 */
public class OrientableColorNode extends ColorNode {
    public OrientableColorNode(Position position, Color value) {
        super(position, value);
    }

    @Override
    public void rotateClockwise(NodePosition nodePosition) {
        mValue.rotateClockwise(nodePosition);
    }

    @Override
    public void rotateCounterClockwise(NodePosition nodePosition) {
        mValue.rotateCounterClockwise(nodePosition);
    }
}