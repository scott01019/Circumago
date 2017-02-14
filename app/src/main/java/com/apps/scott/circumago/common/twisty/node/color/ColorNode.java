package com.apps.scott.circumago.common.twisty.node.color;

import android.os.Parcel;

import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.position.Position;

/**
 * Created by Scott on 10/17/2016.
 */
public abstract class ColorNode extends Node {
    public ColorNode(Position position, Color value) {
        super(position, value);
    }

    public int getTopLeft() { return ((Color) mValue).getTopLeft(); }
    public int getTopRight() { return ((Color) mValue).getTopRight(); }
    public int getBotLeft() { return ((Color) mValue).getBotLeft(); }
    public int getBotRight() { return ((Color) mValue).getBotRight(); }

    public void setColor(int topLeft, int topRight, int botLeft, int botRight) {
        ((Color) mValue).setTopLeft(topLeft);
        ((Color) mValue).setTopRight(topRight);
        ((Color) mValue).setBotLeft(botLeft);
        ((Color) mValue).setBotRight(botRight);
    }

    public void setTopLeft(int topLeft) {
        ((Color) mValue).setTopLeft(topLeft);
    }

    public void setTopRight(int topRight) {
        ((Color) mValue).setTopRight(topRight);
    }

    public void setBotLeft(int botLeft) {
        ((Color) mValue).setBotLeft(botLeft);
    }

    public void setBotRight(int botRight) {
        ((Color) mValue).setBotRight(botRight);
    }
}
