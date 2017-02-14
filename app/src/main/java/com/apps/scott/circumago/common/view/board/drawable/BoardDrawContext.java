package com.apps.scott.circumago.common.view.board.drawable;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.apps.scott.circumago.common.twisty.board.Board;
import com.apps.scott.circumago.common.twisty.board.move.Move;
import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.NodePosition;
import com.apps.scott.circumago.common.twisty.node.NodeType;
import com.apps.scott.circumago.common.utility.CanvasUtil;
import com.apps.scott.circumago.common.view.board.canvas.Facelet;
import com.apps.scott.circumago.common.view.board.canvas.FaceletFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Scott on 10/3/2016.
 */
public class BoardDrawContext implements Observer {
    public static int NUM_COLORS;
    private NodeType mNodeType;
    private ArrayList<Facelet> mCenters;
    private ArrayList<Facelet> mFacelets;
    private HashMap<Facelet, NodePosition> mCenterPositionByFacelet;
    private HashMap<Integer, Facelet> mCenterFaceletByPosition;
    private Canvas mCanvas;
    private BoardDrawState mBoardDrawState;
    private BoardDrawable mBoardDrawable;
    private int mMaxCount;


    public BoardDrawContext(Board board, NodeType nodeType, Bitmap bitmap) {
        init(board, nodeType, bitmap);
    }

    private void init(Board board, NodeType nodeType, Bitmap bitmap) {
        mNodeType = nodeType;
        mCanvas = new Canvas(bitmap);
        mBoardDrawState = BoardDrawState.INITIAL;

        mMaxCount = 6;

        mCenters = new ArrayList<>();
        mFacelets = new ArrayList<>();
        mCenterFaceletByPosition = new HashMap<>();
        mCenterPositionByFacelet = new HashMap<>();

        NUM_COLORS = board.getNumCenters();

        for (Node node : board.getNodes()) {
            if (board.isCenter(node)) {
                Facelet facelet = FaceletFactory.createFacelet(node, mNodeType, true);
                mCenters.add(facelet);
                mCenterPositionByFacelet.put(facelet, node.getPosition());
                mCenterFaceletByPosition.put(node.getPosition().getKey(), facelet);
            } else {
                Facelet facelet = FaceletFactory.createFacelet(node, mNodeType, false);
                mFacelets.add(facelet);
            }
        }

        mBoardDrawable = new BoardInitialDraw(mCenters, mFacelets);
        mBoardDrawable.addObserver(this);
    }

    public void draw() {
        mBoardDrawable.draw(mCanvas);
    }

    public void prepareMoveDraw(Move move) {
        mBoardDrawState = BoardDrawState.MOVE;
        mBoardDrawable = new BoardMoveDraw(
                mCenters,
                mFacelets,
                move,
                mCenterFaceletByPosition.get(move.getCenter().getPosition().getKey()),
                mMaxCount
        );
        mBoardDrawable.addObserver(this);
    }

    public void setRotationMaxCount(int maxCount) {
        mMaxCount = maxCount;
    }

    public ArrayList<Facelet> getCenterFacelets() { return mCenters; }

    public void drawBoard() {
        CanvasUtil.clearCanvas(mCanvas);
        mBoardDrawState = BoardDrawState.INITIAL;
        mBoardDrawable = new BoardInitialDraw(mCenters, mFacelets);
        mBoardDrawable.addObserver(this);
    }
    public BoardDrawState getBoardDrawState() { return mBoardDrawState; }

    public NodePosition getCenterPositionByFacelet(Facelet center) {
        return mCenterPositionByFacelet.get(center);
    }

    @Override
    public void update(Observable o, Object arg) {
        mBoardDrawState = BoardDrawState.STATIC;
        mBoardDrawable = new BoardStaticDraw();
        mBoardDrawable.addObserver(this);
    }
}
