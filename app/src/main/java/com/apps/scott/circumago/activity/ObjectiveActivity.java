package com.apps.scott.circumago.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.twisty.board.move.Move;
import com.apps.scott.circumago.common.twisty.board.move.ParcelableMove;
import com.apps.scott.circumago.common.twisty.board.template.Square4;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.twisty.node.NodeType;
import com.apps.scott.circumago.common.view.FontedTextView;
import com.apps.scott.circumago.fragment.ReplayBoardFragment;

import java.util.ArrayList;

/**
 * Created by Scott on 11/9/2016.
 */
public class ObjectiveActivity extends AppCompatActivity {
    private ReplayBoardFragment mReplayFragment;
    private BoardSchema mBoardSchema;
    private ArrayList<ParcelableMove> mMoveStack;
    private ArrayList<ParcelableMove> mScrambleStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective);
        init();
    }

    private void init() {
        mBoardSchema = new BoardSchema(
                NodeType.ROTATABLE,
                Square4.BOARD,
                Square4.DEFAULT_COLOR_SCHEME,
                true,
                true
        );

        ((FontedTextView) findViewById(R.id.objective_explanation)).setSingleLine(false);

        mMoveStack = new ArrayList<>();
        mMoveStack.add(new ParcelableMove(0, Move.Rotation.CLOCKWISE));
        mMoveStack.add(new ParcelableMove(1, Move.Rotation.COUNTER_CLOCKWISE));
        mMoveStack.add(new ParcelableMove(3, Move.Rotation.CLOCKWISE));
        mMoveStack.add(new ParcelableMove(3, Move.Rotation.CLOCKWISE));
        mMoveStack.add(new ParcelableMove(2, Move.Rotation.COUNTER_CLOCKWISE));
        mMoveStack.add(new ParcelableMove(1, Move.Rotation.COUNTER_CLOCKWISE));
        mMoveStack.add(new ParcelableMove(0, Move.Rotation.COUNTER_CLOCKWISE));
        mMoveStack.add(new ParcelableMove(1, Move.Rotation.CLOCKWISE));

        mScrambleStack = new ArrayList<>();
        mScrambleStack.add(new ParcelableMove(1, Move.Rotation.COUNTER_CLOCKWISE));
        mScrambleStack.add(new ParcelableMove(0, Move.Rotation.CLOCKWISE));
        mScrambleStack.add(new ParcelableMove(1, Move.Rotation.CLOCKWISE));
        mScrambleStack.add(new ParcelableMove(2, Move.Rotation.CLOCKWISE));
        mScrambleStack.add(new ParcelableMove(3, Move.Rotation.COUNTER_CLOCKWISE));
        mScrambleStack.add(new ParcelableMove(3, Move.Rotation.COUNTER_CLOCKWISE));
        mScrambleStack.add(new ParcelableMove(1, Move.Rotation.CLOCKWISE));
        mScrambleStack.add(new ParcelableMove(0, Move.Rotation.COUNTER_CLOCKWISE));

        mReplayFragment = (ReplayBoardFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_replay_board);
        mReplayFragment.setReplay(mBoardSchema, mMoveStack, mScrambleStack);
    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        getFragmentManager().findFragmentById(R.id.fragment_games_container)
                .onActivityResult(request, result, data);
    }
}
