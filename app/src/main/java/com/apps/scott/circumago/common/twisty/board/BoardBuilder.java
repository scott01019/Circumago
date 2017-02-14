package com.apps.scott.circumago.common.twisty.board;


import android.util.Log;

import com.apps.scott.circumago.common.twisty.board.template.Square9;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.NodePosition;
import com.apps.scott.circumago.common.twisty.node.NodeType;
import com.apps.scott.circumago.common.twisty.node.position.Position;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Scott on 9/27/2016.
 */
public abstract class BoardBuilder {
    protected BoardSchema mBoardSchema;
    protected ArrayList<Node> mCenters;
    protected ArrayList<Node> mNodes;

    public BoardBuilder(BoardSchema boardSchema) {
        mBoardSchema = boardSchema;

        mCenters = new ArrayList<>();
        mNodes = new ArrayList<>();


        int[][] copy = new int[7][7];
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) copy[i][j] = boardSchema.getBoard()[i][j];
        }
        buildBoard(copy);
    }

    public void buildBoard(int[][] board) {
        if (!mBoardSchema.hasCorners()) removeCorners(board);
        if (!mBoardSchema.hasEdges()) removeEdges(board);

        int[] colorScheme = mBoardSchema.getColorScheme();

        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (board[i][j] == -1) {
                    Node node = createNode(i, j);
                    mNodes.add(node);
                } else if (board[i][j] >= 0) {
                    Node node = createNode(i, j, colorScheme[board[i][j]]);
                    mCenters.add(node);
                    mNodes.add(node);
                }
            }
        }

        assignValuesForNodes();
    }

    public void removeCorners(int[][] board) {
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (board[i][j] == -1) {
                    if (i + 1 < 7 && j + 1 < 7 && board[i+1][j+1] >= 0) board[i][j] = -2;
                    else if (i + 1 < 7 && j - 1 > 0 && board[i+1][j-1] >= 0) board[i][j] = -2;
                    else if (i - 1 > 0 && j + 1 < 7 && board[i-1][j+1] >= 0) board[i][j] = -2;
                    else if (i - 1 > 0 && j - 1 > 0 && board[i-1][j-1] >= 0) board[i][j] = -2;
                }
            }
        }
    }

    public void removeEdges(int[][] board) {
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (board[i][j] == -1) {
                    if (j + 1 < 7 && board[i][j+1] >= 0) board[i][j] = -2;
                    else if (j - 1 > 0 && board[i][j-1] >= 0) board[i][j] = -2;
                    else if (i - 1 > 0 && board[i-1][j] >= 0) board[i][j] = -2;
                    else if (i + 1 < 7 && board[i+1][j] >= 0) board[i][j] = -2;
                }
            }
        }
    }

    public boolean isLeftNodeInRow(Node node) {
        int row = ((Position) node.getPosition()).getRow();
        int column = ((Position) node.getPosition()).getColumn();
        for (Node otherNode : mNodes) {
            int otherRow = ((Position) otherNode.getPosition()).getRow();
            int otherColumn = ((Position) otherNode.getPosition()).getColumn();
            if (!(column <= otherColumn) && row == otherRow) return false;
        }
        return true;
    }

    public boolean isRightNodeInRow(Node node) {
        int row = ((Position) node.getPosition()).getRow();
        int column = ((Position) node.getPosition()).getColumn();
        for (Node otherNode : mNodes) {
            int otherRow = ((Position) otherNode.getPosition()).getRow();
            int otherColumn = ((Position) otherNode.getPosition()).getColumn();
            if (!(column >= otherColumn) && row == otherRow) return false;
        }
        return true;
    }

    public boolean isTopNodeInColumn(Node node) {
        int row = ((Position) node.getPosition()).getRow();
        int column = ((Position) node.getPosition()).getColumn();
        for (Node otherNode : mNodes) {
            int otherRow = ((Position) otherNode.getPosition()).getRow();
            int otherColumn = ((Position) otherNode.getPosition()).getColumn();
            if (!(row <= otherRow) && column == otherColumn) return false;
        }
        return true;
    }

    public boolean isBottomNodeInColumn(Node node) {
        int row = ((Position) node.getPosition()).getRow();
        int column = ((Position) node.getPosition()).getColumn();
        for (Node otherNode : mNodes) {
            int otherRow = ((Position) otherNode.getPosition()).getRow();
            int otherColumn = ((Position) otherNode.getPosition()).getColumn();
            if (!(row >= otherRow) && column == otherColumn) return false;
        }
        return true;
    }

    public abstract Board createBoard();
    public abstract Node createNode(int row, int col);
    public abstract Node createNode(int row, int col, int val);
    public abstract void assignValuesForNodes();
}
