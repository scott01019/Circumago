package com.apps.scott.circumago.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.apps.scott.circumago.R;

/**
 * Created by Scott on 11/9/2016.
 */
public class GuidesActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides);
        init();
    }

    private void init() {
        findViewById(R.id.guides_objective_button).setOnClickListener(this);
        findViewById(R.id.guides_glossary_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.guides_objective_button){
            startActivity(new Intent(GuidesActivity.this, ObjectiveActivity.class));
        } else if (v.getId() == R.id.guides_glossary_button) {
            startActivity(new Intent(GuidesActivity.this, GlossaryActivity.class));
        }
    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        getFragmentManager().findFragmentById(R.id.fragment_games_container)
                .onActivityResult(request, result, data);
    }
}