package com.apps.scott.circumago.common.utility;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.view.board.drawable.BoardDrawContext;
import com.apps.scott.circumago.common.view.board.drawable.BoardDrawable;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Scott on 9/26/2016.
 */
public class Colors {
    public static int[] COLORS = {
            R.color.circ_purple, R.color.circ_green, R.color.circ_red,
            R.color.circ_blue,  R.color.circ_yellow,  R.color.circ_light_blue,
            R.color.circ_orange, R.color.circ_light_green, R.color.circ_pink
    };

    public static int BACKGROUND_COLOR;

    private static HashMap<Integer, Integer> mColorsMap = new HashMap<>();

    public static int getColor(int color) {
        switch (BoardDrawContext.NUM_COLORS) {
            case 2: {
                switch (color) {
                    case 0: return mColorsMap.get(COLORS[3]);
                    case 1: return mColorsMap.get(COLORS[6]);
                }
            }
                break;
            case 3: {
                switch (color) {
                    case 0: return mColorsMap.get(COLORS[3]);
                    case 1: return mColorsMap.get(COLORS[4]);
                    case 2: return mColorsMap.get(COLORS[2]);
                }
            }
                break;
            case 4: {
                switch (color) {
                    case 0: return mColorsMap.get(COLORS[3]);
                    case 1: return mColorsMap.get(COLORS[4]);
                    case 2: return mColorsMap.get(COLORS[2]);
                    case 3: return mColorsMap.get(COLORS[1]);
                }
            }
                break;
            case 5: {
                switch (color) {
                    case 0: return mColorsMap.get(COLORS[3]);
                    case 1: return mColorsMap.get(COLORS[2]);
                    case 2: return mColorsMap.get(COLORS[4]);
                    case 3: return mColorsMap.get(COLORS[1]);
                    case 4: return mColorsMap.get(COLORS[6]);
                }
            }
                break;
            case 6: {
                switch (color) {
                    case 0: return mColorsMap.get(COLORS[1]);
                    case 1: return mColorsMap.get(COLORS[2]);
                    case 2: return mColorsMap.get(COLORS[3]);
                    case 3: return mColorsMap.get(COLORS[5]);
                    case 4: return mColorsMap.get(COLORS[6]);
                    case 5: return mColorsMap.get(COLORS[7]);
                }
            }
            case 7: {
                switch (color) {
                    case 0: return mColorsMap.get(COLORS[1]);
                    case 1: return mColorsMap.get(COLORS[2]);
                    case 2: return mColorsMap.get(COLORS[3]);
                    case 3: return mColorsMap.get(COLORS[4]);
                    case 4: return mColorsMap.get(COLORS[5]);
                    case 5: return mColorsMap.get(COLORS[6]);
                    case 6: return mColorsMap.get(COLORS[7]);
                }
            }
            case 8: {
                switch (color) {
                    case 0: return mColorsMap.get(COLORS[0]);
                    case 1: return mColorsMap.get(COLORS[1]);
                    case 2: return mColorsMap.get(COLORS[2]);
                    case 3: return mColorsMap.get(COLORS[3]);
                    case 4: return mColorsMap.get(COLORS[5]);
                    case 5: return mColorsMap.get(COLORS[6]);
                    case 6: return mColorsMap.get(COLORS[7]);
                    case 7: return mColorsMap.get(COLORS[8]);
                }
            }
            case 9: {
                switch (color) {
                    case 0: return mColorsMap.get(COLORS[0]);
                    case 1: return mColorsMap.get(COLORS[1]);
                    case 2: return mColorsMap.get(COLORS[2]);
                    case 3: return mColorsMap.get(COLORS[3]);
                    case 4: return mColorsMap.get(COLORS[4]);
                    case 5: return mColorsMap.get(COLORS[5]);
                    case 6: return mColorsMap.get(COLORS[6]);
                    case 7: return mColorsMap.get(COLORS[7]);
                    case 8: return mColorsMap.get(COLORS[8]);
                }
            }
        }
        return BACKGROUND_COLOR;
    }

    public static void loadColors(WeakReference<Context> weakReferenceContext) {
        for (int i = 0; i < COLORS.length; ++i) {
            mColorsMap.put(
                    COLORS[i],
                    ContextCompat.getColor(weakReferenceContext.get(), COLORS[i])
            );
        }
    }

    public static void setBackgroundColor(int color) { BACKGROUND_COLOR = color; }
}
