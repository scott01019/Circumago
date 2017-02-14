package com.apps.scott.circumago.common.twisty.node;


import android.os.Parcelable;

/**
 * Created by Scott on 9/6/2016.
 */
public interface NodePosition extends Rotatable {
    void setPosition(NodePosition nodePosition);
    boolean equals(NodePosition nodePosition);
    boolean isAdjacent(NodePosition nodePosition);
    int getKey();
    int distanceSquared(NodePosition nodePosition);
}
