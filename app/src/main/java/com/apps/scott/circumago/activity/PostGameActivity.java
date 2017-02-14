package com.apps.scott.circumago.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.gps.OnGoogleApiConnectedListener;
import com.apps.scott.circumago.common.twisty.board.move.ParcelableMove;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.twisty.board.util.BoardUtil;
import com.apps.scott.circumago.common.twisty.node.NodeType;
import com.apps.scott.circumago.common.utility.listeners.OnSolvedListener;
import com.apps.scott.circumago.common.utility.Settings;
import com.apps.scott.circumago.common.utility.listeners.OnViewMeasuredListener;
import com.apps.scott.circumago.common.view.FontedTextView;
import com.apps.scott.circumago.common.view.board.replay.BoardReplayView;
import com.apps.scott.circumago.fragment.BoardSetupFragment;
import com.apps.scott.circumago.fragment.GamesFragment;
import com.apps.scott.circumago.fragment.ReplayBoardFragment;
import com.google.android.gms.games.Games;

import java.util.ArrayList;

public class PostGameActivity extends AppCompatActivity
        implements View.OnClickListener, OnGoogleApiConnectedListener {

    private ReplayBoardFragment mReplayFragment;
    private GamesFragment mGamesFragment;
    private FontedTextView mCongratsText;
    private BoardSchema mBoardSchema;
    private String mBoardName;
    private String mTime;
    private float mElapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_game);
        init(getIntent().getExtras());
    }

    private void init(Bundle bundle) {
        findViewById(R.id.post_game_replay_button).setOnClickListener(this);
        findViewById(R.id.post_game_new_game_button).setOnClickListener(this);
        findViewById(R.id.post_game_main_menu_button).setOnClickListener(this);

        mBoardSchema = bundle.getParcelable(BoardSetupFragment.BOARD_SCHEMA);
        mBoardName = bundle.getString(BoardSetupFragment.BOARD_NAME_TAG);
        mTime = bundle.getString(PlayGameActivity.ELAPSED_TIME_STRING_TAG);
        mElapsedTime = bundle.getFloat(PlayGameActivity.ELAPSED_TIME_TAG);

        mCongratsText = (FontedTextView) findViewById(R.id.post_game_congrats_text);

        mReplayFragment = (ReplayBoardFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_replay_board);
        mReplayFragment.setReplay(bundle);

        mGamesFragment = (GamesFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_games_container);
        mGamesFragment.setOnGoogleApiConnectedListener(this);

        setCongratsText();
    }

    public void unlockAchievement() {
        if (mGamesFragment.isConnected()
                && mBoardSchema.getNodeType() == NodeType.ROTATABLE
                && mBoardSchema.hasCorners() && mBoardSchema.hasEdges()) {
            Games.Achievements.unlock(mGamesFragment.getApiClient(), getAchievementName());
        }
    }

    public String getAchievementName() {
        String name = "achievement_";
        String[] board = mBoardName.split("\\s+");
        for (String str : board) name += str.toLowerCase() + "_";
        name += String.valueOf(BoardUtil.getColorCount(mBoardSchema.getColorScheme())) + "color";
        try {
            return getString(
                    this.getResources().getIdentifier(name, "string", this.getPackageName())
            );
        } catch (Exception e) {
            return "NO_ACHIEVEMENT";
        }
    }

    public void updateLeaderboard() {
        if (mGamesFragment.isConnected()
                && mBoardSchema.getNodeType() == NodeType.ROTATABLE
                && mBoardSchema.hasCorners() && mBoardSchema.hasEdges()
                && BoardUtil.getColorCount(mBoardSchema.getColorScheme())
                == BoardUtil.getBoard(mBoardName).getNumColors()) {
            Games.Leaderboards.submitScore(
                    mGamesFragment.getApiClient(),
                    getLeaderboardName(),
                    (long) (mElapsedTime * 1000)
            );
        }
    }

    public String getLeaderboardName() {
        String name = "leaderboard";
        String [] board = mBoardName.split("\\s+");
        for (String str : board) name += "_" + str.toLowerCase();
        try {
            return getString(
                    this.getResources().getIdentifier(name, "string", this.getPackageName())
            );
        } catch (Exception e) {
            return "NO_LEADERBOARD";
        }

    }

    public void setCongratsText() {
        mCongratsText.setSingleLine(false);

        String format = new String();
        switch (mBoardSchema.getNodeType()) {
            case ROTATABLE: format = "a rotatable";
                break;
            case ORIENTABLE: format = "an orientable";
                break;
            case PERMUTABLE: format = "a permutable";
                break;
        }

        if (Settings.isDisplayTimer(this)) {
            mCongratsText.setText(
                    "Congratulations on solving "
                            + format + " "
                            + String.valueOf(BoardUtil.getColorCount(mBoardSchema.getColorScheme()))
                            + "-color " + mBoardName + " in " + mTime + "!"
            );
        } else {
            mCongratsText.setText(
                    "Congratulations on solving a "
                    + format + " "
                    + String.valueOf(BoardUtil.getColorCount(mBoardSchema.getColorScheme()))
                    + "-color " + mBoardName + "!"
            );
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PostGameActivity.this, MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mReplayFragment.removeCallbacks();
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        getFragmentManager().findFragmentById(R.id.fragment_games_container)
                .onActivityResult(request, result, data);
    }

    @Override
    public void onClick(View v) {
        mReplayFragment.removeCallbacks();
        if (v.getId() == R.id.post_game_main_menu_button) {
            Intent intent = new Intent(PostGameActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.post_game_replay_button) {
            Intent intent = new Intent(PostGameActivity.this, PlayGameActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putString(BoardSetupFragment.BOARD_NAME_TAG, mBoardName);
            bundle.putParcelable(BoardSetupFragment.BOARD_SCHEMA, mBoardSchema);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.post_game_new_game_button) {
            Intent intent = new Intent(PostGameActivity.this, GameSetupActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onConnected() {
        unlockAchievement();
        updateLeaderboard();
    }
}
