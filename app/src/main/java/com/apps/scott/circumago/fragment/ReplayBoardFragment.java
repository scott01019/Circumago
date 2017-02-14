package com.apps.scott.circumago.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.activity.PlayGameActivity;
import com.apps.scott.circumago.common.twisty.board.move.ParcelableMove;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.utility.listeners.OnSolvedListener;
import com.apps.scott.circumago.common.utility.listeners.OnViewMeasuredListener;
import com.apps.scott.circumago.common.view.board.replay.BoardReplayView;

import java.util.ArrayList;

/**
 * Created by Scott on 11/23/2016.
 */
public class ReplayBoardFragment extends Fragment implements OnSolvedListener,
        OnViewMeasuredListener {

    private FrameLayout mReplayBoardContainer;
    private BoardReplayView mReplayBoard;

    private ArrayList<ParcelableMove> mMoveStack;
    private ArrayList<ParcelableMove> mScrambleStack;
    private BoardSchema mBoardSchema;

    private Handler mReplayThread;
    private Runnable mRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_replay_board, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mReplayBoard = (BoardReplayView) view.findViewById(R.id.replay_board);
        mReplayBoard.setOnViewMeasuredListener(this);
        mReplayBoardContainer = (FrameLayout) view.findViewById(R.id.replay_board_container);
    }

    public void setReplay(Bundle bundle) {
        mMoveStack = (ArrayList<ParcelableMove>) bundle
                .getSerializable(PlayGameActivity.MOVE_STACK_TAG);
        mScrambleStack = (ArrayList<ParcelableMove>) bundle
                .getSerializable(PlayGameActivity.SCRAMBLE_STACK_TAG);
        mBoardSchema = bundle.getParcelable(BoardSetupFragment.BOARD_SCHEMA);
    }

    public void setReplay(BoardSchema boardSchema, ArrayList<ParcelableMove> moveStack,
                          ArrayList<ParcelableMove> scrambleStack) {
        mBoardSchema = boardSchema;
        mMoveStack = moveStack;
        mScrambleStack = scrambleStack;
    }

    public void removeCallbacks() {
        mReplayThread.removeCallbacks(mRunnable);
    }

    public void initReplay() {
        mReplayBoard.setOnSolvedListener(this);
        mReplayBoard.clearMoveQueues();
        mReplayBoard.prepareReplayBoard(
                mBoardSchema,
                mMoveStack,
                mScrambleStack
        );
    }

    @Override
    public void viewMeasured() {
        mReplayBoard.prepareReplayBoard(mBoardSchema, mMoveStack, mScrambleStack);
        mReplayBoard.setOnViewMeasuredListener(null);

        int childHeight = mReplayBoard.getHeight();
        boolean isNotContained
                = mReplayBoardContainer.getHeight()
                < childHeight
                + mReplayBoardContainer.getPaddingTop()
                + mReplayBoardContainer.getPaddingBottom();

        if (isNotContained) {
            mReplayBoardContainer.setVisibility(View.GONE);
            mReplayBoard.setVisibility(View.GONE);
        } else {
            mReplayBoard.setRotationMaxCount(15);
            mReplayBoard.setOnSolvedListener(this);
            mReplayBoard.setOnViewMeasuredListener(this);

            mReplayThread = new Handler();
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    initReplay();
                }
            };
        }
    }

    @Override
    public void onSolved() {
        mReplayBoard.setOnSolvedListener(null);
        mReplayThread.postDelayed(mRunnable, 5000);
    }
}
