package com.apps.scott.circumago.common.utility;

/**
 * Created by Scott on 10/24/2016.
 */
public class BoardColorSchemePicker {
    private int[][] mColorSchemes;
    private int mColorSchemeIndex;

    public BoardColorSchemePicker(int[][] colorSchemes, int colorSchemeIndex) {
        mColorSchemeIndex = colorSchemeIndex;
        mColorSchemes = colorSchemes;
    }

    public void setColorSchemes(int[][] colorSchemes) {
        mColorSchemes = colorSchemes;
        mColorSchemeIndex = 0;
    }

    public int[] getColorScheme() {
        return mColorSchemes[mColorSchemeIndex];
    }

    public void incrementColorSchemeIndex() {
        if (mColorSchemeIndex < mColorSchemes.length - 1) ++mColorSchemeIndex;
        else mColorSchemeIndex = 0;
    }

    public void decrementColorSchemeIndex() {
        if (mColorSchemeIndex == 0) mColorSchemeIndex = mColorSchemes.length - 1;
        else --mColorSchemeIndex;
    }
}
