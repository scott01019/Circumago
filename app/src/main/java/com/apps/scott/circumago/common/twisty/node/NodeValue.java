package com.apps.scott.circumago.common.twisty.node;

import android.os.Parcelable;

/**
 * Created by Scott on 10/17/2016.
 */
public interface NodeValue extends Rotatable {
    void setValue(NodeValue nodeValue);
    boolean equals(NodeValue nodeValue);
    int getKey();
}
