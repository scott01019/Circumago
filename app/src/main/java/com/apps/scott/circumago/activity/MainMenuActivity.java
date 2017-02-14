package com.apps.scott.circumago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.utility.Colors;
import com.apps.scott.circumago.common.utility.billing.IabKey;

import java.lang.ref.WeakReference;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        //findViewById(R.id.main_menu_tutorial_button).setOnClickListener(this);
        findViewById(R.id.main_menu_play_button).setOnClickListener(this);
        findViewById(R.id.main_menu_settings_button).setOnClickListener(this);
        findViewById(R.id.main_menu_practice_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Colors.loadColors(new WeakReference<>(getApplicationContext()));
        Colors.setBackgroundColor(
                getApplicationContext().getResources().getColor(android.R.color.transparent)
        );
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.main_menu_tutorial_button) {
//            startActivity(new Intent(MainMenuActivity.this, GuidesActivity.class));
//        }
        if (v.getId() == R.id.main_menu_play_button) {
            startActivity(new Intent(MainMenuActivity.this, GameSetupActivity.class));
        } else if (v.getId() == R.id.main_menu_settings_button) {
            startActivity(new Intent(MainMenuActivity.this, SettingsActivity.class));
        } else if (v.getId() == R.id.main_menu_practice_button) {
            startActivity(new Intent(MainMenuActivity.this, PracticeSetupActivity.class));
        }
    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        getFragmentManager().findFragmentById(R.id.fragment_games_container)
                .onActivityResult(request, result, data);
    }
}