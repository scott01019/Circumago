package com.apps.scott.circumago.common.twisty.node;

import com.apps.scott.circumago.common.twisty.board.move.Move;
import com.apps.scott.circumago.common.twisty.node.color.ColorNode;
import com.apps.scott.circumago.common.utility.Random;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Scott on 10/25/2016.
 */
public enum FaceType {
    COLOR {
        @Override
        public ArrayList<Move> scramble(ArrayList<Node> centers) {
            HashMap<Integer, ArrayList<ColorNode>> nodesByColor = new HashMap<>();
            for (Node node : centers) {
                int value = ((ColorNode) node).getTopLeft();
                if (nodesByColor.containsKey(value)) {
                    ArrayList<ColorNode> nodes = nodesByColor.get(value);
                    nodes.add((ColorNode) node);
                    nodesByColor.put(value, nodes);
                } else {
                    ArrayList<ColorNode> nodes = new ArrayList<>();
                    nodes.add((ColorNode) node);
                    nodesByColor.put(value, nodes);
                }
            }

            ArrayList<Move> moves = new ArrayList<>();
            int numColors = nodesByColor.keySet().size();
            for (int i = 0; i < Math.ceil(75 / numColors); ++i) {
                for (Integer color : nodesByColor.keySet()) {
                    ArrayList<ColorNode> nodes = nodesByColor.get(color);
                    Move.Rotation direction = (Random.getInt(1) == 0) ?
                            Move.Rotation.CLOCKWISE : Move.Rotation.COUNTER_CLOCKWISE;
                    moves.add(new Move(nodes.get(Random.getInt(nodes.size() - 1)), direction));
                }
            }
            return moves;
        }
    };
    public abstract ArrayList<Move> scramble(ArrayList<Node> centers);
}
