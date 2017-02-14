package com.apps.scott.circumago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.fragment.QueryNoAdsFragment;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void finishSplash() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        if (request == QueryNoAdsFragment.RC_REQUEST) {
            QueryNoAdsFragment queryFragment = (QueryNoAdsFragment) getFragmentManager()
                    .findFragmentById(R.id.fragment_query);
            if (queryFragment.getHelper() == null) return;

            if (!queryFragment.getHelper().handleActivityResult(request, result, data)) {
                super.onActivityResult(request, result, data);
            }
        }
    }
}
