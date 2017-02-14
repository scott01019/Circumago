package com.apps.scott.circumago.common.view.board;

import android.util.Log;

/**
 * Created by Scott on 9/26/2016.
 */
public class BoardSpec {
    public static int CANVAS_DIM;

    public static int CELLS_PER_ROW = 7;

    public static int BOARD_DIM;
    public static int BOARD_PADDING = 10;

    public static int CELL_DIM;

    public static int CENTER_CELL_PADDING;
    public static int CENTER_BLOCK_DIM;

    public static int QUAD_CELL_PADDING;
    public static int QUAD_CELL_CENTER_OFFSET;
    public static int QUAD_BLOCK_DIM;

    public static void setDims(int canvasDim) {
        CANVAS_DIM = canvasDim;
        BOARD_DIM = CANVAS_DIM - 2 * BOARD_PADDING;
        BOARD_DIM = BOARD_DIM - (BOARD_DIM % CELLS_PER_ROW);
        CELL_DIM = BOARD_DIM / CELLS_PER_ROW;
        CENTER_CELL_PADDING = 0;
        CENTER_BLOCK_DIM = CELL_DIM - CENTER_CELL_PADDING;
        QUAD_CELL_CENTER_OFFSET = 2;
        QUAD_CELL_PADDING = (CELL_DIM / 2 -  QUAD_CELL_CENTER_OFFSET) * 30 / 100;
        QUAD_BLOCK_DIM = CELL_DIM / 2 - QUAD_CELL_PADDING - QUAD_CELL_CENTER_OFFSET;

        if (BOARD_PADDING < Math.floor(0.63 * CELL_DIM - 1.42 * QUAD_CELL_PADDING)) {
            BOARD_PADDING += 1;
            setDims(canvasDim);
        } else if ((BOARD_PADDING - 1) > Math.floor(0.63 * CELL_DIM - 1.42 * QUAD_CELL_PADDING)) {
            BOARD_PADDING -= 1;
            setDims(canvasDim);
        }
    }
}
