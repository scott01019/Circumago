package com.apps.scott.circumago.common.twisty.board;


import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.twisty.node.NodeType;
import com.apps.scott.circumago.common.twisty.node.color.ColorBoardBuilder;

/**
 * Created by Scott on 9/27/2016.
 */
public class BoardFactory {
    public static Board createBoard(BoardSchema boardSchema) {
        return new ColorBoardBuilder(boardSchema).createBoard();
    }
}
