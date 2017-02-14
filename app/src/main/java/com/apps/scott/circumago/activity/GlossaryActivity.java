package com.apps.scott.circumago.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.view.FontedTextView;

/**
 * Created by Scott on 11/10/2016.
 */
public class GlossaryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);
        init();
    }

    private void init() {
        ((FontedTextView) findViewById(R.id.glossary_node_definition)).setSingleLine(false);
        ((FontedTextView) findViewById(R.id.glossary_circumago_definition)).setSingleLine(false);
        ((FontedTextView) findViewById(R.id.glossary_orientable_definition)).setSingleLine(false);
        ((FontedTextView) findViewById(R.id.glossary_permutable_definition)).setSingleLine(false);
        ((FontedTextView) findViewById(R.id.glossary_rotatable_definition)).setSingleLine(false);
    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        getFragmentManager().findFragmentById(R.id.fragment_games_container)
                .onActivityResult(request, result, data);
    }
}
