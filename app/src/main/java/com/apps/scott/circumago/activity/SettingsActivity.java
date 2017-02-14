package com.apps.scott.circumago.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.billing.IabBroadcastReceiver;
import com.apps.scott.circumago.common.billing.IabException;
import com.apps.scott.circumago.common.billing.IabHelper;
import com.apps.scott.circumago.common.billing.IabResult;
import com.apps.scott.circumago.common.billing.Inventory;
import com.apps.scott.circumago.common.billing.Purchase;
import com.apps.scott.circumago.common.caches.StringArrayCache;
import com.apps.scott.circumago.common.utility.Observer;
import com.apps.scott.circumago.common.utility.Settings;
import com.apps.scott.circumago.common.utility.billing.IabKey;
import com.apps.scott.circumago.fragment.BillingNoAdsFragment;
import com.apps.scott.circumago.fragment.FontedTextViewPagerFragment;
import com.apps.scott.circumago.fragment.QueryNoAdsFragment;

public class SettingsActivity extends AppCompatActivity implements Observer {

    private BillingNoAdsFragment mBillingFragment;

    private FontedTextViewPagerFragment mTimerFragment;
    private FontedTextViewPagerFragment mInputFragment;
    private FontedTextViewPagerFragment mScrambleFragment;
    private FontedTextViewPagerFragment mGamePromptFragment;

    private int mTimerSelection;
    private int mInputSelection;
    private int mScrambleSelection;
    private int mGamePromptSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSettings();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveSettings();
    }

    private void init() {
        SharedPreferences settings = getSharedPreferences(Settings.SETTINGS_TAG, 0);
        mInputSelection = settings.getInt(Settings.INPUT_TAG, 1);
        boolean showTimer = settings.getBoolean(Settings.TIMER_TAG, true);
        boolean scrambleAnimation = settings.getBoolean(Settings.SCRAMBLE_TAG, true);
        boolean gamePrompt = settings.getBoolean(Settings.GAME_BACK_PROMPT_TAG, true);

        String[] showTimerSettings = StringArrayCache.getStrings(
                R.array.show_timer_settings,
                getApplicationContext()
        );

        String[] inputSettings = StringArrayCache.getStrings(
                R.array.input_settings,
                getApplicationContext()
        );

        String[] scrambleAnimationSettings = StringArrayCache.getStrings(
                R.array.scramble_animation_settings,
                getApplicationContext()
        );

        String[] gamePromptSettings = StringArrayCache.getStrings(
                R.array.game_warning_prompt_settings,
                getApplicationContext()
        );

        mInputFragment = (FontedTextViewPagerFragment) getFragmentManager()
                .findFragmentById(R.id.settings_input);
        mInputFragment.setPageAdapter(inputSettings);

        if (mInputSelection == 3) mInputSelection = 1;
        else if (mInputSelection == 0) mInputSelection = 2;

        mInputFragment.setItem(mInputSelection);


        mTimerFragment = (FontedTextViewPagerFragment) getFragmentManager()
                .findFragmentById(R.id.settings_show_timer);
        mTimerFragment.setPageAdapter(showTimerSettings);

        if (showTimer) mTimerSelection = 1;
        else mTimerSelection = 2;

        mTimerFragment.setItem(mTimerSelection);


        mScrambleFragment = (FontedTextViewPagerFragment) getFragmentManager()
                .findFragmentById(R.id.settings_scramble_animation);
        mScrambleFragment.setPageAdapter(scrambleAnimationSettings);

        if (scrambleAnimation) mScrambleSelection = 1;
        else mScrambleSelection = 2;

        mScrambleFragment.setItem(mScrambleSelection);


        mGamePromptFragment = (FontedTextViewPagerFragment) getFragmentManager()
                .findFragmentById(R.id.settings_game_prompt);
        mGamePromptFragment.setPageAdapter(gamePromptSettings);

        if (gamePrompt) mGamePromptSelection = 1;
        else mGamePromptSelection = 2;

        mGamePromptFragment.setItem(mGamePromptSelection);

        mTimerFragment.getObservable().addObserver(this);
        mInputFragment.getObservable().addObserver(this);
        mScrambleFragment.getObservable().addObserver(this);
        mGamePromptFragment.getObservable().addObserver(this);

        mBillingFragment = (BillingNoAdsFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_premium);
    }

    private void saveSettings() {
        SharedPreferences settings = getSharedPreferences(Settings.SETTINGS_TAG, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean(
                Settings.TIMER_TAG,
                mTimerSelection == 1 || mTimerSelection == 3
        );

        editor.putInt(Settings.INPUT_TAG, mInputSelection);

        editor.putBoolean(
                Settings.SCRAMBLE_TAG,
                mScrambleSelection == 1 || mScrambleSelection == 3
        );

        editor.putBoolean(
                Settings.GAME_BACK_PROMPT_TAG,
                mGamePromptSelection == 1 || mGamePromptSelection == 3
        );

        editor.apply();
    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        if (request == 9001 || request == 9002 || request == 9003) {
            super.onActivityResult(request, result, data);
            getFragmentManager().findFragmentById(R.id.fragment_games_container)
                    .onActivityResult(request, result, data);
        } else if (request == BillingNoAdsFragment.RC_REQUEST
                || request == QueryNoAdsFragment.RC_REQUEST) {
            if (mBillingFragment.getHelper() == null) return;

            if (!mBillingFragment.getHelper().handleActivityResult(request, result, data)) {
                super.onActivityResult(request, result, data);
            }
        }
    }

    @Override
    public void update(int id) {
        mInputSelection = mInputFragment.getCurrentItem();
        mTimerSelection = mTimerFragment.getCurrentItem();
        mScrambleSelection = mScrambleFragment.getCurrentItem();
        mGamePromptSelection = mGamePromptFragment.getCurrentItem();
    }
}