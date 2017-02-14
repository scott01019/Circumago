package com.apps.scott.circumago.common.view.board;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.apps.scott.circumago.common.twisty.board.Board;
import com.apps.scott.circumago.common.twisty.board.BoardFactory;
import com.apps.scott.circumago.common.twisty.board.move.Move;
import com.apps.scott.circumago.common.twisty.board.move.ParcelableMove;
import com.apps.scott.circumago.common.twisty.board.template.Square9;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.twisty.node.FaceType;
import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.NodePosition;
import com.apps.scott.circumago.common.twisty.node.NodeType;
import com.apps.scott.circumago.common.utility.Settings;
import com.apps.scott.circumago.common.utility.listeners.OnScrambledListener;
import com.apps.scott.circumago.common.utility.listeners.OnSolvedListener;
import com.apps.scott.circumago.common.utility.listeners.OnViewMeasuredListener;
import com.apps.scott.circumago.common.view.board.canvas.Facelet;
import com.apps.scott.circumago.common.view.board.drawable.BoardDrawContext;
import com.apps.scott.circumago.common.view.board.drawable.BoardDrawState;
import com.apps.scott.circumago.common.view.board.touchlisteners.BoardViewTouchListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Scott on 10/3/2016.
 */
public class BoardView extends View {
    protected static int FRAME_WAIT = 16;
    protected Bitmap mBitmap;

    protected int mWidth;

    private boolean mIsScrambled;
    private boolean mScrambleAnimation;

    protected OnSolvedListener mOnSolvedListener;
    private OnScrambledListener mOnScrambledListener;
    protected OnViewMeasuredListener mOnViewMeasuredListener;

    protected Board mBoard;
    protected Queue<Move> mScrambleQueue;
    protected Queue<Move> mMoveQueue;
    private BoardViewTouchListener mOnTouchListener;
    protected BoardDrawContext mBoardDrawContext;

    public BoardView(Context context) {
        super(context);
        mWidth = 500;
        init(
                new BoardSchema(NodeType.ROTATABLE, Square9.BOARD, Square9.DEFAULT_COLOR_SCHEME,
                        true, true)
        );
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWidth = 500;
        init(
                new BoardSchema(NodeType.ROTATABLE, Square9.BOARD, Square9.DEFAULT_COLOR_SCHEME,
                        true, true)
        );
    }

    protected void init(BoardSchema boardSchema) {
        mIsScrambled = false;

        BoardSpec.setDims(mWidth);

        mScrambleAnimation = Settings.isAnimatedScramble(getContext());

        mBitmap = Bitmap.createBitmap(
                BoardSpec.CANVAS_DIM,
                BoardSpec.CANVAS_DIM,
                Bitmap.Config.ARGB_8888
        );

        mMoveQueue = new LinkedList<>();
        mScrambleQueue = new LinkedList<>();

        setOnTouchListener(mOnTouchListener);

        mBoard = BoardFactory.createBoard(boardSchema);

        mBoardDrawContext = new BoardDrawContext(mBoard, boardSchema.getNodeType(), mBitmap);
    }

    public void setupBoard(BoardSchema boardSchema) { init(boardSchema); }

    public Stack<Move> getMoveStack() { return mBoard.getMoveStack(); }
    public Stack<Move> getScrambleStack() { return mBoard.getScrambleStack(); }

    public void revertMove() { mMoveQueue.add(new Move(null, Move.Rotation.REVERT)); }
    public void addMoveToQueue(Move move) { mMoveQueue.add(move); }
    public void invertRotationDirection() {
        if (mOnTouchListener != null) mOnTouchListener.invertDirection();
    }

    public void setBoardViewOnTouchListener(BoardViewTouchListener listener) {
        mOnTouchListener = listener;
        setOnTouchListener(mOnTouchListener);
    }

    public void setScrambleAnimation(boolean animation) { mScrambleAnimation = animation; }

    public void setOnSolvedListener(OnSolvedListener listener) { mOnSolvedListener = listener; }

    public void setOnViewMeasuredListener(OnViewMeasuredListener listener) {
        mOnViewMeasuredListener = listener;
    }

    public void setOnScrambledListener(OnScrambledListener listener) {
        mOnScrambledListener = listener;
    }

    public boolean isScrambling() { return !mScrambleQueue.isEmpty(); }

    public boolean isScrambled() { return mScrambleQueue.isEmpty() && mIsScrambled; }

    public boolean isSolved() { return mBoard.isSolved(); }

    public void clearMoveQueue() { mMoveQueue.clear(); }

    public ArrayList<Facelet> getCenterFacelets() {
        return mBoardDrawContext.getCenterFacelets();
    }

    public NodePosition getCenterPositionFromFacelet(Facelet center) {
        return mBoardDrawContext.getCenterPositionByFacelet(center);
    }

    public Node getNodeByPosition(NodePosition position) {
        return mBoard.getNodeByPosition(position);
    }

    public Integer getCenterIndex(Node center) {
        return mBoard.getCenterIndex(center);
    }

    protected void attemptConsumeMove() {
        if (mBoardDrawContext.getBoardDrawState() == BoardDrawState.STATIC
                && !mMoveQueue.isEmpty()) {
            Move move = mMoveQueue.remove();
            if (move.getRotation() == Move.Rotation.REVERT) {
                move = mBoard.revertMove();
                if (move != null) {
                    move.invertRotation();
                    mBoardDrawContext.prepareMoveDraw(move);
                }
            } else {
                mBoard.move(move);
                mBoardDrawContext.prepareMoveDraw(move);
            }
        }
    }

    protected void attemptConsumeScrambleMove() {
        if (mScrambleAnimation) {
            if (mBoardDrawContext.getBoardDrawState() == BoardDrawState.STATIC) {
                Move move = mScrambleQueue.remove();
                mBoard.moveScramble(move);
                mBoardDrawContext.prepareMoveDraw(move);
            }
        } else {
            while (!mScrambleQueue.isEmpty()) {
                Move move = mScrambleQueue.remove();
                mBoard.moveScramble(move);
            }
        }
        if (mScrambleQueue.isEmpty()) {
            setOnTouchListener(mOnTouchListener);
            notifyScrambledListener();
            mBoardDrawContext.drawBoard();
        }
    }

    public void scramble() {
        setOnTouchListener(null);
        ArrayList<Node> centers = mBoard.getCenters();
        ArrayList<Move> moves = FaceType.COLOR.scramble(centers);
        for (Move move : moves) mScrambleQueue.add(move);
        mBoard.clearMoveStack();
        mIsScrambled = true;
    }

    public void scrambleParcelableMoves(ArrayList<ParcelableMove> moves) {
        setOnTouchListener(null);
        for (int i = moves.size() - 1; i >= 0; --i) {
            mScrambleQueue.add(
                    new Move(
                            mBoard.getCenterAt(moves.get(i).getCenter()),
                            moves.get(i).getRotation()
                    )
            );
        }
        mBoard.clearMoveStack();
        mIsScrambled = true;
    }

    protected void notifySolvedListener() {
        if (mOnSolvedListener != null) mOnSolvedListener.onSolved();
    }

    protected void notifyScrambledListener() {
        if (mOnScrambledListener != null) mOnScrambledListener.onScrambled();
    }

    protected void notifyViewMeasuredListener() {
        if (mOnViewMeasuredListener != null) mOnViewMeasuredListener.viewMeasured();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mScrambleQueue.isEmpty()) {
            if (mBoard.isSolved()) {
                notifySolvedListener();
            }
            attemptConsumeMove();
        } else {
            attemptConsumeScrambleMove();
        }
        mBoardDrawContext.draw();
        canvas.drawBitmap(mBitmap, 0, 0, null);
        postInvalidateDelayed(FRAME_WAIT);
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, width);
        if (width == mWidth) setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        if (mWidth == 0) mWidth = getResources().getDisplayMetrics().widthPixels;
        super.onSizeChanged(mWidth, mWidth, oldw, oldh);
        notifyViewMeasuredListener();
    }
}