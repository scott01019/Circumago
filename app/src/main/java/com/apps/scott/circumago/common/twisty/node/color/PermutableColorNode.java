package com.apps.scott.circumago.common.twisty.node.color;

import android.os.Parcel;

import com.apps.scott.circumago.common.twisty.node.NodePosition;
import com.apps.scott.circumago.common.twisty.node.position.Position;

/**
 * Created by Scott on 10/17/2016.
 */
public class PermutableColorNode extends ColorNode {

    public PermutableColorNode(Position position, Color value) {
        super(position, value);
    }

    @Override
    public void rotateClockwise(NodePosition nodePosition) {
        mPosition.rotateClockwise(nodePosition);
    }

    @Override
    public void rotateCounterClockwise(NodePosition nodePosition) {
        mPosition.rotateCounterClockwise(nodePosition);
    }
}
