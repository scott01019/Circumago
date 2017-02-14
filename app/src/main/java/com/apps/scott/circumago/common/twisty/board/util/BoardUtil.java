package com.apps.scott.circumago.common.twisty.board.util;

import com.apps.scott.circumago.common.twisty.board.template.ConcreteBoard;
import com.apps.scott.circumago.common.twisty.board.template.Cross;
import com.apps.scott.circumago.common.twisty.board.template.Edges;
import com.apps.scott.circumago.common.twisty.board.template.Flower;
import com.apps.scott.circumago.common.twisty.board.template.Line2;
import com.apps.scott.circumago.common.twisty.board.template.Line3;
import com.apps.scott.circumago.common.twisty.board.template.Plus;
import com.apps.scott.circumago.common.twisty.board.template.SideBySide;
import com.apps.scott.circumago.common.twisty.board.template.Square4;
import com.apps.scott.circumago.common.twisty.board.template.Square9;
import com.apps.scott.circumago.common.twisty.board.template.Triangle3;
import com.apps.scott.circumago.common.twisty.board.template.Triangle6;
import com.apps.scott.circumago.common.twisty.board.template.Unorthodox1;
import com.apps.scott.circumago.common.twisty.board.template.Unorthodox2;
import com.apps.scott.circumago.common.twisty.board.template.Wrench;
import com.apps.scott.circumago.common.twisty.board.template.X;

import java.util.HashSet;

/**
 * Created by Scott on 10/12/2016.
 */
public class BoardUtil {

    public static int getColorCount(int[] colorScheme) {
        HashSet<Integer> counter = new HashSet<>();
        for (int i = 0; i < colorScheme.length; ++i) counter.add(colorScheme[i]);
        return counter.size();
    }

    public static ConcreteBoard getBoard(String boardName) {
        switch(boardName) {
            case "Square 3x3": return new Square9();
            case "Square 2x2": return new Square4();
            case "Cross": return new Cross();
            case "Edges": return new Edges();
            case "Line 2": return new Line2();
            case "Line 3": return new Line3();
            case "X": return new X();
            case "Triangle 3": return new Triangle3();
            case "Triangle 6": return new Triangle6();
            case "Plus" : return new Plus();
            case "Wrench": return new Wrench();
            case "Unorthodox 1": return new Unorthodox1();
            case "Unorthodox 2": return new Unorthodox2();
            case "Side By Side": return new SideBySide();
            case "Flower": return new Flower();
        }
        return null;
    }
}
