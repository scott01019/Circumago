package com.apps.scott.circumago.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.caches.FontCache;
import com.apps.scott.circumago.common.caches.StringArrayCache;
import com.apps.scott.circumago.common.twisty.board.move.Move;
import com.apps.scott.circumago.common.twisty.board.move.ParcelableMove;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.utility.listeners.OnScrambledListener;
import com.apps.scott.circumago.common.utility.listeners.OnSolvedListener;
import com.apps.scott.circumago.common.utility.Random;
import com.apps.scott.circumago.common.utility.Settings;
import com.apps.scott.circumago.common.utility.listeners.OnViewMeasuredListener;
import com.apps.scott.circumago.common.view.FontedTextView;
import com.apps.scott.circumago.common.view.board.BoardView;
import com.apps.scott.circumago.fragment.BoardSetupFragment;
import com.apps.scott.circumago.fragment.PlayGameBoardFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener
        , OnSolvedListener, OnScrambledListener, OnViewMeasuredListener, Animator.AnimatorListener {

    public static final String ELAPSED_TIME_TAG = "circumago.activity.PlayGameActivity" +
            ".ELAPSED_TIME_TAG";
    public static final String ELAPSED_TIME_STRING_TAG = "circumago.activity.PlayGameActivity" +
            ".ELAPSED_TIME_STRING_TAG";
    public static final String MOVE_STACK_TAG = "circumago.activity.PlayGameActivity" +
            ".MOVE_STACK_TAG";
    public static final String SCRAMBLE_STACK_TAG = "circumago.actiivty.PlayGameActivity" +
            ".SCRAMBLE_STACK_TAG";

    private BoardView mBoardView;
    private Chronometer mChronometer;
    private ImageView mDirection;
    private FontedTextView mWinText;
    private FontedTextView mTimerText;

    private BoardSchema mBoardSchema;

    private String mBoardName;
    private long mTimeWhenStopped = 0;
    private long mStartTime;
    private long mEndTime;
    private float mElapsedTime;
    private String[] mWinTexts;
    private boolean mSolvedAnimationFinished;
    private Move.Rotation mRotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        init(getIntent().getExtras());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Settings.isDisplayTimer(getApplicationContext())
                && mBoardView.isScrambled())
            mChronometer.setVisibility(View.VISIBLE);
        if (!Settings.isDisplayTimer(getApplicationContext()))
            mChronometer.setVisibility(View.GONE);
        mStartTime = SystemClock.elapsedRealtime();
        mChronometer.setBase(SystemClock.elapsedRealtime() + mTimeWhenStopped);
        mChronometer.start();
        setPlayGameDirectionButton();
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
        mElapsedTime += (SystemClock.elapsedRealtime() - mStartTime) / 1000.00f;
        mChronometer.stop();
    }

    @Override
    public void onBackPressed() {
        if (Settings.isShowPlayGameBackPrompt(getApplicationContext())
                && mBoardView.isScrambled() && !mBoardView.isSolved()) {
            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.play_game_back_prompt_text))
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    public void init(Bundle bundle) {
        mBoardSchema = bundle.getParcelable(BoardSetupFragment.BOARD_SCHEMA);

        mWinTexts = StringArrayCache.getStrings(
                R.array.win_texts,
                getApplicationContext()
        );

        mSolvedAnimationFinished = false;

        mBoardView = (BoardView) findViewById(R.id.fragment_play_game_board_board);
        mChronometer = (Chronometer) findViewById(R.id.fragment_play_game_board_timer);
        mDirection = (ImageView) findViewById(R.id.play_game_direction);
        mWinText = (FontedTextView) findViewById(R.id.play_game_win_text);
        mTimerText = (FontedTextView) findViewById(R.id.play_game_time_text);

        mBoardView.setOnViewMeasuredListener(this);

        mChronometer.setTypeface(
                FontCache.getTypeface("JosefinSans-SemiBold.ttf", getApplicationContext())
        );

        findViewById(R.id.play_game_container).setOnClickListener(this);
        findViewById(R.id.play_game_settings).setOnClickListener(this);
        findViewById(R.id.play_game_undo).setOnClickListener(this);
        mDirection.setOnClickListener(this);

        mBoardName = bundle.getString(BoardSetupFragment.BOARD_NAME_TAG);

        ((FontedTextView) findViewById(R.id.play_game_title))
                .setText(mBoardName);

        mRotation = Move.Rotation.CLOCKWISE;
        mElapsedTime = 0.0f;

        setPlayGameDirectionButton();
    }

    private void solvedAnimation() {
        mChronometer.setVisibility(View.GONE);

        ArrayList<Animator> animators = new ArrayList<>();

        mWinText.setSingleLine(false);
        mWinText.setText(mWinTexts[Random.getInt(mWinTexts.length - 1)]);
        animators.add(ObjectAnimator.ofFloat(mWinText, "alpha", 0.0f, 1).setDuration(2000));
        mWinText.setVisibility(View.VISIBLE);

        if (Settings.isDisplayTimer(getApplicationContext())) {
            mElapsedTime += (mEndTime - mStartTime) / 1000.00f;
            String str = getFormattedTimeString();
            mTimerText.setText(str);
            animators.add(ObjectAnimator.ofFloat(mTimerText, "alpha", 0.0f, 1).setDuration(2000));
            mTimerText.setVisibility(View.VISIBLE);
        }

        animators.get(0).addListener(this);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animators);

        animatorSet.start();
    }

    private void setPlayGameDirectionButton() {
        if (Settings.isTapToRotate(getApplicationContext())) mDirection.setVisibility(View.VISIBLE);
        else mDirection.setVisibility(View.GONE);

        mRotation = Move.Rotation.CLOCKWISE;
        mDirection.setImageResource(R.drawable.ic_clockwise_24dp);
    }


    public void invertRotationDirection() {
        mBoardView.invertRotationDirection();
        mRotation = Move.getInverse(mRotation);

        if (mRotation == Move.Rotation.CLOCKWISE)
            mDirection.setImageResource(R.drawable.ic_clockwise_24dp);
        else mDirection.setImageResource(R.drawable.ic_counter_clockwise_24dp);
    }

    private String getFormattedTimeString() {
        DecimalFormat df = new DecimalFormat("#.###");
        String str = "";
        int hours = (int) mElapsedTime / 3600;
        int minutes = (int) mElapsedTime / 60;
        float seconds = mElapsedTime - minutes * 60;

        if (hours != 0 && hours > 10) str = String.valueOf(hours) + ":";
        else if (hours != 0) str = "0" + String.valueOf(hours) + ":";

        if (minutes != 0 && minutes > 10) str += String.valueOf(minutes) + ":";
        else if (minutes != 0 && hours > 0) str += "0" + String.valueOf(minutes) + ":";
        else if (minutes != 0) str += String.valueOf(minutes) + ":";

        if (seconds < 10 && (minutes > 0 || hours > 0)) str += ("0" + df.format(seconds));
        else str += df.format(seconds);

        return str;
    }

    @Override
    public void onSolved() {
        mBoardView.setOnSolvedListener(null);
        mBoardView.setBoardViewOnTouchListener(null);
        mBoardView.clearMoveQueue();
        mChronometer.stop();
        mEndTime = SystemClock.elapsedRealtime();
        solvedAnimation();
    }

    @Override
    public void onScrambled() {
        mBoardView.setOnScrambledListener(null);
        if (Settings.isDisplayTimer(this)) mChronometer.setVisibility(View.VISIBLE);
        mStartTime = SystemClock.elapsedRealtime();
        mChronometer.setBase(mStartTime);
        mChronometer.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mSolvedAnimationFinished = true;
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onClick(View v) {
        if (!mBoardView.isScrambling()) {
            if (mSolvedAnimationFinished) {
                Intent intent = new Intent(PlayGameActivity.this, PostGameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(BoardSetupFragment.BOARD_NAME_TAG, mBoardName);
                bundle.putParcelable(BoardSetupFragment.BOARD_SCHEMA, mBoardSchema);
                bundle.putSerializable(
                        MOVE_STACK_TAG,
                        getParcelableMoves(mBoardView.getMoveStack())
                );
                bundle.putSerializable(
                        SCRAMBLE_STACK_TAG,
                        getParcelableMoves(mBoardView.getScrambleStack())
                );
                bundle.putString(ELAPSED_TIME_STRING_TAG, getFormattedTimeString());
                bundle.putFloat(ELAPSED_TIME_TAG, mElapsedTime);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.animator.post_game_activity_fade_in, 0);
                finish();
            } else if (v.getId() == R.id.play_game_settings) {
                Intent intent = new Intent(PlayGameActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.play_game_undo) {
                mBoardView.revertMove();
            } else if (v.getId() == R.id.play_game_direction) {
                if (mBoardView.isScrambled()) invertRotationDirection();
            }
        }
    }

    public Stack<ParcelableMove> getParcelableMoves(Stack<Move> moveStack) {
        Stack<ParcelableMove> parcelableMoves = new Stack<>();
        while (!moveStack.isEmpty()) {
            Move move = moveStack.pop();
            parcelableMoves.push(new ParcelableMove(
                    mBoardView.getCenterIndex(move.getCenter()),
                    move.getRotation()
            ));
        }
        return parcelableMoves;
    }

    @Override
    public void viewMeasured() {
        mBoardView.setupBoard(mBoardSchema);
        mBoardView.setOnViewMeasuredListener(null);
    }
}