package com.apps.scott.circumago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.caches.StringArrayCache;
import com.apps.scott.circumago.common.twisty.board.template.ConcreteBoard;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.twisty.board.util.BoardUtil;
import com.apps.scott.circumago.common.twisty.node.NodeType;
import com.apps.scott.circumago.common.utility.Observer;
import com.apps.scott.circumago.fragment.BoardColorSchemePickerFragment;
import com.apps.scott.circumago.fragment.BoardSetupFragment;
import com.apps.scott.circumago.fragment.FontedTextViewPagerFragment;

import java.util.ArrayList;

public class GameSetupActivity extends AppCompatActivity implements View.OnClickListener {
    private BoardSetupFragment mBoardSetupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
        init();
    }

    private void init() {
        mBoardSetupFragment = (BoardSetupFragment) getFragmentManager()
                .findFragmentById(R.id.game_setup_fragment_board_setup);

        findViewById(R.id.game_setup_generate).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.game_setup_generate) {
            Intent intent = new Intent(GameSetupActivity.this, PlayGameActivity.class);
            intent.putExtras(mBoardSetupFragment.getBoardBundle());
            startActivity(intent);
        }
    }


    @Override
    public void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        getFragmentManager().findFragmentById(R.id.fragment_games_container)
                .onActivityResult(request, result, data);
    }
}
