package com.apps.scott.circumago.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.twisty.board.move.Move;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.utility.Settings;
import com.apps.scott.circumago.common.utility.listeners.OnViewMeasuredListener;
import com.apps.scott.circumago.common.view.FontedTextView;
import com.apps.scott.circumago.common.view.board.BoardView;
import com.apps.scott.circumago.fragment.BoardSetupFragment;

/**
 * Created by Scott on 11/3/2016.
 */
public class PracticeActivity extends AppCompatActivity
        implements View.OnClickListener, OnViewMeasuredListener {
    private BoardView mBoardView;
    private ImageView mDirection;
    private BoardSchema mBoardSchema;
    private String mBoardName;
    private Move.Rotation mRotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        init(getIntent().getExtras());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPlayGameDirectionButton();
    }

    private void init(Bundle bundle) {
        mBoardName = bundle.getString(BoardSetupFragment.BOARD_NAME_TAG);
        mBoardSchema = bundle.getParcelable(BoardSetupFragment.BOARD_SCHEMA);
        mRotation = Move.Rotation.CLOCKWISE;
        mDirection = (ImageView) findViewById(R.id.practice_direction);
        mBoardView = (BoardView) findViewById(R.id.fragment_practice_game_board_board);
        mBoardView.setOnViewMeasuredListener(this);
        ((FontedTextView) findViewById(R.id.practice_title)).setText(mBoardName);

        mDirection.setOnClickListener(this);
        findViewById(R.id.practice_settings).setOnClickListener(this);
        findViewById(R.id.practice_undo).setOnClickListener(this);
    }

    public BoardSchema getBoardSchema() { return mBoardSchema; }

    public void invertRotationDirection() {
        mBoardView.invertRotationDirection();
        mRotation = Move.getInverse(mRotation);

        if (mRotation == Move.Rotation.CLOCKWISE)
            mDirection.setImageResource(R.drawable.ic_clockwise_24dp);
        else mDirection.setImageResource(R.drawable.ic_counter_clockwise_24dp);
    }

    private void setPlayGameDirectionButton() {
        if (Settings.isTapToRotate(getApplicationContext())) mDirection.setVisibility(View.VISIBLE);
        else mDirection.setVisibility(View.GONE);

        mRotation = Move.Rotation.CLOCKWISE;
        mDirection.setImageResource(R.drawable.ic_clockwise_24dp);
    }

    @Override
    public void onClick(View v) {
        if (!mBoardView.isScrambling()) {
            if (v.getId() == R.id.practice_settings) {
                Intent intent = new Intent(PracticeActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.practice_undo) {
                mBoardView.revertMove();
            } else if (v.getId() == R.id.practice_direction) {
                invertRotationDirection();
            }
        }
    }

    @Override
    public void viewMeasured() {
        mBoardView.setupBoard(mBoardSchema);
        mBoardView.setOnViewMeasuredListener(null);
    }
}