package com.apps.scott.circumago.common.twisty.node;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Scott on 9/15/2016.
 */
public abstract class Node implements Rotatable {
    protected NodePosition mPosition;
    protected NodeValue mValue;

    public Node(NodePosition position, NodeValue value) {
        mPosition = position;
        mValue = value;
    }

    public Node(Node node) {
        mPosition = node.mPosition;
        mValue = node.mValue;
    }

    public boolean isAdjacent(Node node) {
        return mPosition.isAdjacent(node.getPosition());
    }

    public NodePosition getPosition() { return mPosition; }
    public NodeValue getValue() { return mValue; }
}
