package com.apps.scott.circumago.common.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.apps.scott.circumago.activity.SettingsActivity;

/**
 * Created by Scott on 10/20/2016.
 */
public class Settings {
    public static final String SETTINGS_TAG = "circumago.common.Settings.SETTINGS_TAG";
    public static final String INPUT_TAG = "circumago.common.Settings.INPUT_TAG";
    public static final String TIMER_TAG = "circumago.common.Settings.TIMER_TAG";
    public static final String SCRAMBLE_TAG = "circumago.common.Settings.SCRAMBLE_TAG";
    public static final String GAME_BACK_PROMPT_TAG = "circumago.common.Settings" +
            ".GAME_BACK_PROMPT_TAG";

    public static boolean isTapToRotate(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SETTINGS_TAG, 0);
        int selected = settings.getInt(INPUT_TAG, 0);
        return (selected == 1 || selected == 3);
    }

    public static boolean isSwipeToRotate(Context context) {
        return !isTapToRotate(context);
    }

    public static boolean isDisplayTimer(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SETTINGS_TAG, 0);
        return settings.getBoolean(TIMER_TAG, true);
    }

    public static boolean isHideTimer(Context context) {
        return !isDisplayTimer(context);
    }

    public static boolean isInstantScramble(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SETTINGS_TAG, 0);
        return !settings.getBoolean(SCRAMBLE_TAG, true);
    }

    public static boolean isAnimatedScramble(Context context) {
        return !isInstantScramble(context);
    }

    public static boolean isShowPlayGameBackPrompt(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SETTINGS_TAG, 0);
        return settings.getBoolean(GAME_BACK_PROMPT_TAG, true);
    }

    public static boolean isHidePlayGameBackPrompt(Context context) {
        return !isShowPlayGameBackPrompt(context);
    }
}
