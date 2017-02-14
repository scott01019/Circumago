package com.apps.scott.circumago.common.twisty.board.template;

/**
 * Created by Scott on 10/12/2016.
 */
public interface ConcreteBoard {
    int[][] getBoard();
    int[] getDefaultColorScheme();
    int getBoardId();
    int[] getColorScheme(int numColors, int schemeIndex);
    int[][] getColorSchemes(int numColors);
    int getNumColors();
}
