package com.apps.scott.circumago.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.fragment.BoardSetupFragment;

/**
 * Created by Scott on 11/3/2016.
 */
public class PracticeSetupActivity extends AppCompatActivity implements View.OnClickListener {
    private BoardSetupFragment mBoardSetupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_setup);
        init();
    }

    private void init() {
        mBoardSetupFragment = (BoardSetupFragment) getFragmentManager()
                .findFragmentById(R.id.practice_setup_fragment_board_setup);

        findViewById(R.id.practice_setup_generate).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.practice_setup_generate) {
            Intent intent = new Intent(PracticeSetupActivity.this, PracticeActivity.class);
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
