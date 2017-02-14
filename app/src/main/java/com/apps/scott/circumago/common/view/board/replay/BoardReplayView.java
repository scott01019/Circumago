package com.apps.scott.circumago.common.view.board.replay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import com.apps.scott.circumago.common.twisty.board.BoardFactory;
import com.apps.scott.circumago.common.twisty.board.move.Move;
import com.apps.scott.circumago.common.twisty.board.move.ParcelableMove;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.view.board.BoardSpec;
import com.apps.scott.circumago.common.view.board.BoardView;
import com.apps.scott.circumago.common.view.board.drawable.BoardDrawContext;

import java.util.ArrayList;

/**
 * Created by Scott on 10/20/2016.
 */
public class BoardReplayView extends BoardView {
    public BoardReplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void prepareReplayBoard(BoardSchema boardSchema,
                                   ArrayList<ParcelableMove> moveStack,
                                   ArrayList<ParcelableMove> scrambleStack) {
        BoardSpec.setDims(mWidth);

        mBitmap = Bitmap.createBitmap(
                BoardSpec.CANVAS_DIM,
                BoardSpec.CANVAS_DIM,
                Bitmap.Config.ARGB_8888
        );

        mBoard = BoardFactory.createBoard(boardSchema);

        mBoardDrawContext = new BoardDrawContext(mBoard, boardSchema.getNodeType(), mBitmap);
        mBoardDrawContext.setRotationMaxCount(15);

        makeScrambleMoves(scrambleStack);
        makeMoves(moveStack);
        mBoardDrawContext.drawBoard();
    }

    protected void makeScrambleMoves(ArrayList<ParcelableMove> moves) {
        for (int i = moves.size() - 1; i >= 0; --i) {
            ParcelableMove parcelableMove = moves.get(i);
            mBoard.move(
                    new Move(
                            mBoard.getCenterAt(parcelableMove.getCenter()),
                            parcelableMove.getRotation()
                    )
            );
        }
    }

    protected void makeMoves(ArrayList<ParcelableMove> moves) {
        for (int i = moves.size() - 1; i >= 0; --i) {
            ParcelableMove parcelableMove = moves.get(i);
            mMoveQueue.add(
                    new Move(
                            mBoard.getCenterAt(parcelableMove.getCenter()),
                            parcelableMove.getRotation()
                    )
            );
        }
    }

    public void setRotationMaxCount(int maxCount) {
        mBoardDrawContext.setRotationMaxCount(maxCount);
    }

    public void clearMoveQueues() {
        mMoveQueue.clear();
        mScrambleQueue.clear();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBoard.isSolved()) {
            notifySolvedListener();
        }
        attemptConsumeMove();
        mBoardDrawContext.draw();
        canvas.drawBitmap(mBitmap, 0, 0, null);
        postInvalidateDelayed(FRAME_WAIT);
    }
}
