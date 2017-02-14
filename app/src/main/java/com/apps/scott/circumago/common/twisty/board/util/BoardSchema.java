package com.apps.scott.circumago.common.twisty.board.util;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.apps.scott.circumago.common.twisty.node.NodeType;

/**
 * Created by Scott on 10/24/2016.
 */
public class BoardSchema implements Parcelable {
    private NodeType mNodeType;
    private boolean mHasCorners;
    private boolean mHasEdges;
    private int[][] mBoard;
    private int[] mColorScheme;

    public BoardSchema(NodeType nodeType, int[][] board, int[] colorScheme,
                       boolean hasCorners, boolean hasEdges) {
        mNodeType = nodeType;
        mBoard = board;
        mColorScheme = colorScheme;
        mHasCorners = hasCorners;
        mHasEdges = hasEdges;
    }

    public BoardSchema(Parcel in) {
        Bundle bundle = in.readBundle();
        mBoard = (int[][]) bundle.getSerializable("mBoard");
        mColorScheme = bundle.getIntArray("mColorScheme");
        mHasEdges = bundle.getBoolean("mHasEdges");
        mHasCorners = bundle.getBoolean("mHasCorners");
        mNodeType = (NodeType) bundle.getSerializable("mNodeType");
    }

    public int[][] getBoard() {
        return mBoard;
    }

    public void setBoard(int[][] board) {
        mBoard = board;
    }

    public int[] getColorScheme() {
        return mColorScheme;
    }

    public void setColorScheme(int[] colorScheme) {
        mColorScheme = colorScheme;
    }

    public boolean hasCorners() {
        return mHasCorners;
    }

    public void setHasCorners(boolean hasCorners) {
        mHasCorners = hasCorners;
    }

    public boolean hasEdges() {
        return mHasEdges;
    }

    public void setHasEdges(boolean hasEdges) {
        mHasEdges = hasEdges;
    }

    public NodeType getNodeType() {
        return mNodeType;
    }

    public void setNodeType(NodeType nodeType) {
        mNodeType = nodeType;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public BoardSchema createFromParcel(Parcel in) {
            return new BoardSchema(in);
        }

        public BoardSchema[] newArray(int size) {
            return new BoardSchema[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putIntArray("mColorScheme", mColorScheme);
        bundle.putBoolean("mHasEdges", mHasEdges);
        bundle.putBoolean("mHasCorners", mHasCorners);
        bundle.putSerializable("mNodeType", mNodeType);
        bundle.putSerializable("mBoard", mBoard);
        dest.writeBundle(bundle);
    }
}
