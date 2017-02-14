package com.apps.scott.circumago.common.twisty.board;


import android.util.Log;

import com.apps.scott.circumago.common.twisty.board.move.Move;
import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.NodePosition;
import com.apps.scott.circumago.common.twisty.node.NodeValue;
import com.apps.scott.circumago.common.twisty.node.Solvable;
import com.apps.scott.circumago.common.twisty.node.color.Color;
import com.apps.scott.circumago.common.twisty.node.color.ColorNode;
import com.apps.scott.circumago.common.twisty.node.position.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Created by Scott on 9/15/2016.
 */
public class Board implements Solvable {
    private ArrayList<Node> mCenters;
    private ArrayList<Node> mNodes;
    private HashMap<Integer, Node> mNodesByPosition;
    private HashMap<Integer, HashSet<Integer>> mSolvedPositionsByValue;

    private Stack<Move> mMoveStack;
    private Stack<Move> mScrambleStack;

    public Board(ArrayList<Node> centers, ArrayList<Node> nodes) {
        mCenters = centers;
        mNodes = nodes;
        mNodesByPosition = new HashMap<>();
        mSolvedPositionsByValue = new HashMap<>();
        mMoveStack = new Stack<>();
        mScrambleStack = new Stack<>();
        setNodesByPosition();
        setSolvedMaps();
    }

    public ArrayList<Node> getNodes() { return mNodes; }
    public ArrayList<Node> getCenters() { return mCenters; }

    public boolean isCenter(Node node) {
        for (Node center : mCenters)
            if (node.equals(center)) return true;
        return false;
    }

    @Override
    public boolean isSolved() {
        for (Node node : mNodes) {
            HashSet<Integer> solvedPositions
                    = mSolvedPositionsByValue.get(node.getValue().getKey());
            if (solvedPositions == null) return false;
            if (!solvedPositions.contains(node.getPosition().getKey())) return false;
        }
        return true;
    }

    public int getNumCenters() {
        ArrayList<NodeValue> values = new ArrayList<>();
        for (Node node : mCenters) {
            boolean alreadyThere = false;
            for (NodeValue value : values) {
                if (node.getValue().equals(value)) {
                    alreadyThere = true;
                }
            }
            if (!alreadyThere) values.add(node.getValue());
        }
        return values.size();
    }

    public void setSolvedMaps() {
        for (Node node : mNodes) {
            HashSet<Integer> solvedPositions = new HashSet<>();
            for (Node otherNode : mNodes) {
                if (otherNode.getValue().equals(node.getValue())) {
                    solvedPositions.add(otherNode.getPosition().getKey());
                }
            }

            if (mSolvedPositionsByValue.containsKey(node.getValue().getKey())) {
                HashSet<Integer> otherPositions
                        = mSolvedPositionsByValue.get(node.getValue().getKey());
                otherPositions.addAll(solvedPositions);
                mSolvedPositionsByValue.put(node.getValue().getKey(), otherPositions);
            } else {
                mSolvedPositionsByValue.put(node.getValue().getKey(), solvedPositions);
            }
        }
    }

    public void setNodesByPosition() {
        for (Node node : mNodes)
            mNodesByPosition.put(getKey(node), node);
    }

    public void rotateFaceClockwise(Node center) {
        for (Node node : mNodes) {
            if (center.isAdjacent(node)) {
                node.rotateClockwise(center.getPosition());
                mNodesByPosition.put(getKey(node), node);
            }
        }
    }

    public void rotateFaceCounterClockwise(Node center) {
        for (Node node : mNodes) {
            if (center.isAdjacent(node)) {
                node.rotateCounterClockwise(center.getPosition());
                mNodesByPosition.put(getKey(node), node);
            }
        }
    }

    public static int getKey(NodePosition nodePosition) {
        return nodePosition.getKey();
    }

    public static int getKey(Node node) {
        return node.getPosition().getKey();
    }

    public void moveScramble(Move move) {
        Node center = move.getCenter();
        mScrambleStack.push(move);
        switch (move.getRotation()) {
            case CLOCKWISE: rotateFaceClockwise(center);
                break;
            case COUNTER_CLOCKWISE:
                rotateFaceCounterClockwise(center);
                break;
        }
    }

    public void move(Move move) {
        Node center = move.getCenter();
        mMoveStack.push(move);
        switch (move.getRotation()) {
            case CLOCKWISE: rotateFaceClockwise(center);
                break;
            case COUNTER_CLOCKWISE:
                rotateFaceCounterClockwise(center);
                break;
        }
    }

    public Move revertMove() {
        if (!mMoveStack.isEmpty()) {
            Move move = mMoveStack.pop();
            Node center = move.getCenter();
            switch(Move.getInverse(move.getRotation())) {
                case CLOCKWISE: rotateFaceClockwise(center);
                    break;
                case COUNTER_CLOCKWISE:
                    rotateFaceCounterClockwise(center);
                    break;
            }
            return move;
        }
        return null;
    }

    public Node getNodeByPosition(NodePosition nodePosition) {
        return mNodesByPosition.get(nodePosition.getKey());
    }

    public Integer getCenterIndex(Node center) {
        for (int i = 0; i < mCenters.size(); ++i)
            if (center.equals(mCenters.get(i))) return i;
        return -1;
    }

    public Node getCenterAt(int index) {
        return mCenters.get(index);
    }

    public void clearMoveStack() { mMoveStack.clear(); }

    public Stack<Move> getMoveStack()  { return mMoveStack; }
    public Stack<Move> getScrambleStack() { return mScrambleStack; }
}
