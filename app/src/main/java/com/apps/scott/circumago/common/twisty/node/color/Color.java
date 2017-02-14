package com.apps.scott.circumago.common.twisty.node.color;

import android.os.Parcel;

import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.NodePosition;
import com.apps.scott.circumago.common.twisty.node.NodeValue;

/**
 * Created by Scott on 10/17/2016.
 */
public class Color implements NodeValue {
    private int mTopLeft;
    private int mTopRight;
    private int mBotLeft;
    private int mBotRight;

    public Color() {
        mTopLeft = mTopRight = mBotRight = mBotLeft = -1;
    }

    public Color(int topLeft, int topRight, int botLeft, int botRight) {
        mTopLeft = topLeft;
        mTopRight = topRight;
        mBotLeft = botLeft;
        mBotRight = botRight;
    }

    public Color(int values) {
        mTopRight = mTopLeft = mBotLeft = mBotRight = values;
    }

    public int getTopLeft() {
        return mTopLeft;
    }

    public void setTopLeft(int mTopLeft) {
        this.mTopLeft = mTopLeft;
    }

    public int getTopRight() {
        return mTopRight;
    }

    public void setTopRight(int mTopRight) {
        this.mTopRight = mTopRight;
    }

    public int getBotLeft() {
        return mBotLeft;
    }

    public void setBotLeft(int mBotLeft) {
        this.mBotLeft =  mBotLeft;
    }

    public int getBotRight() {
        return mBotRight;
    }

    public void setBotRight(int mBotRight) {
        this.mBotRight = mBotRight;
    }

    @Override
    public boolean equals(NodeValue nodeValue) {
        return (this.mTopLeft == ((Color) nodeValue).mTopLeft
                && this.mTopRight == ((Color) nodeValue).mTopRight
                && this.mBotLeft == ((Color) nodeValue).mBotLeft
                && this.mBotRight == ((Color) nodeValue).mBotRight);
    }

    @Override
    public void rotateClockwise(NodePosition nodePosition) {
        int temp = mTopRight;
        mTopRight = mTopLeft;
        mTopLeft = mBotLeft;
        mBotLeft = mBotRight;
        mBotRight = temp;
    }

    @Override
    public void rotateCounterClockwise(NodePosition nodePosition) {
        int temp = mTopRight;
        mTopRight = mBotRight;
        mBotRight = mBotLeft;
        mBotLeft = mTopLeft;
        mTopLeft = temp;
    }

    @Override
    public void setValue(NodeValue nodeValue) {
        mTopLeft = ((Color) nodeValue).mTopLeft;
        mTopRight = ((Color) nodeValue).mTopRight;
        mBotLeft = ((Color) nodeValue).mBotLeft;
        mBotRight = ((Color) nodeValue).mBotRight;
    }

    @Override
    public int getKey() {
        return mTopLeft * 1000000 + mTopRight * 100000 + mBotLeft * 10000 + mBotRight * 1000;
    }
}
