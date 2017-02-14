package com.apps.scott.circumago.common.twisty.board.template;

/**
 * Created by Scott on 12/5/2016.
 */
public class Wrench implements ConcreteBoard {
    public static int[][] BOARD = {
            { -2, -1, -1, -1, -2, -2, -2 },
            { -2, -1, 0, -1, -2, -2, -2 },
            { -1, -1, -1, -1, -1, -1, -1 },
            { -1, 1, -1, 2, -1, 3, -1 },
            { -1, -1, -1, -1, -1, -1, -1 },
            { -2, -2, -2, -1, 4, -1, -2 },
            { -2, -2, -2, -1, -1, -1, -2 }
    };

    public static int BOARD_ID = 11000;

    public static int[] DEFAULT_COLOR_SCHEME = { 0, 1, 2, 3, 4 };

    public static int[][][] COLOR_SCHEMES = {
      /*2-color*/      {
            { 0, 0, 0, 0, 1 },
            { 0, 1, 1, 1, 0 },
            { 0, 1, 0, 1, 0 },
            { 0, 1, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 1, 1 }
    },
      /*3-color*/ {
            { 0, 1, 2, 2, 2 },
            { 0, 1, 1, 2, 2 },
            { 0, 1, 2, 1, 2 },
            { 0, 1, 2, 1, 0 },
            { 0, 0, 1, 2, 2 },
            { 0, 1, 0, 2, 0 }
    },
      /*4-color*/ {
            { 0, 1, 2, 3, 3 },
            { 0, 1, 1, 2, 3 },
            { 0, 1, 2, 1, 3 }
    },
      /*5-color*/ {
            { 0, 1, 2, 3, 4 }
    }
    };

    @Override
    public int[][] getBoard() {
        return BOARD;
    }

    @Override
    public int[] getDefaultColorScheme() {
        return DEFAULT_COLOR_SCHEME;
    }

    @Override
    public int getBoardId() {
        return BOARD_ID;
    }

    @Override
    public int[] getColorScheme(int numColors, int schemeIndex) {
        return COLOR_SCHEMES[numColors-2][schemeIndex % COLOR_SCHEMES[numColors-2].length];
    }

    @Override
    public int getNumColors() { return DEFAULT_COLOR_SCHEME.length; }

    @Override
    public int[][] getColorSchemes(int numColors) {
        return COLOR_SCHEMES[numColors - 2];
    }
}
