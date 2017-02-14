package com.apps.scott.circumago.common.twisty.node.position;

import android.os.Parcel;

import com.apps.scott.circumago.common.twisty.node.NodePosition;

/**
 * Created by Scott on 10/17/2016.
 */
public class Position implements NodePosition {
    private int mRow;
    private int mColumn;

    public Position(Position pos) {
        mRow = pos.getRow();
        mColumn = pos.getColumn();
    }

    public Position(int row, int column) {
        mRow = row;
        mColumn = column;
    }

    public void setRow(int row) { mRow = row; }
    public int getRow() { return mRow; }
    public void setColumn(int column) { mColumn = column; }
    public int getColumn() { return mColumn; }

    @Override
    public boolean equals(NodePosition nodePosition) {
        return mRow == ((Position) nodePosition).getRow()
                && mColumn == ((Position) nodePosition).getColumn();
    }

    @Override
    public int getKey() {
        return mRow * 7 + mColumn;
    }

    @Override
    public boolean isAdjacent(NodePosition nodePosition) {
        int dx = Math.abs(mColumn - ((Position) nodePosition).getColumn());
        int dy = Math.abs(mRow - ((Position) nodePosition).getRow());

        return (dx == 1 && dy == 1)
                || (dx == 1 && dy == 0)
                || (dx == 0 && dy == 1);
    }

    @Override
    public void rotateClockwise(NodePosition nodePosition) {
        int temp = mRow;
        int column = ((Position) nodePosition).getColumn();
        int row = ((Position) nodePosition).getRow();

        mRow = mColumn - column + row;
        mColumn = row - temp + column;
    }

    @Override
    public void rotateCounterClockwise(NodePosition nodePosition) {
        int temp = mRow;
        int column = ((Position) nodePosition).getColumn();
        int row = ((Position) nodePosition).getRow();

        mRow = column - mColumn + row;
        mColumn = column + temp - row;
    }

    @Override
    public void setPosition(NodePosition nodePosition) {
        mRow = ((Position) nodePosition).getRow();
        mColumn = ((Position) nodePosition).getColumn();
    }

    @Override
    public int distanceSquared(NodePosition nodePosition) {
        return Math.abs(mRow - ((Position) nodePosition).getRow())
                * Math.abs(mRow - ((Position) nodePosition).getRow())
                + Math.abs(mColumn - ((Position) nodePosition).getColumn())
                * Math.abs(mColumn - ((Position) nodePosition).getColumn());
    }
}
