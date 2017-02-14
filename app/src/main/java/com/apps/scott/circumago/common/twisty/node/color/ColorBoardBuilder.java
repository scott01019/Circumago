package com.apps.scott.circumago.common.twisty.node.color;

import android.util.Log;

import com.apps.scott.circumago.common.twisty.board.Board;
import com.apps.scott.circumago.common.twisty.board.BoardBuilder;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.NodePosition;
import com.apps.scott.circumago.common.twisty.node.NodeType;
import com.apps.scott.circumago.common.twisty.node.position.Position;

import java.util.ArrayList;

/**
 * Created by Scott on 10/17/2016.
 */
public class ColorBoardBuilder extends BoardBuilder {
    public ColorBoardBuilder(BoardSchema boardSchema) {
        super(boardSchema);
    }

    public int getUnassigned(int topLeft, int topRight, int botLeft, int botRight) {
        int count = 0;
        if (topLeft == -1) ++count;
        if (topRight == -1) ++count;
        if (botLeft == -1) ++count;
        if (botRight == -1) ++count;
        return count;
    }

    public boolean isMonochrome(ArrayList<ColorNode> centers) {
        for (ColorNode node : centers) {
            if (centers.get(0).getBotLeft() != node.getBotLeft())
                return false;
        }
        return true;
    }

    @Override
    public void assignValuesForNodes() {
        for (Node node : mNodes) {
            if (((ColorNode) node).getBotLeft() == -1) {
                ArrayList<ColorNode> neighboringCenters = new ArrayList<>();
                for (Node center : mCenters)
                    if (node.isAdjacent(center))
                        neighboringCenters.add((ColorNode) center);

                int topLeft = -1, topRight = -1, botLeft = -1, botRight = -1;

                if (isMonochrome(neighboringCenters)) {
                    topLeft = topRight = botLeft = botRight = neighboringCenters.get(0)
                            .getBotLeft();
                } else {
                    int nodeRow = ((Position) node.getPosition()).getRow();
                    int nodeColumn = ((Position) node.getPosition()).getColumn();

                    for (Node center : neighboringCenters) {
                        int centerRow = ((Position) center.getPosition()).getRow();
                        int centerColumn = ((Position) center.getPosition()).getColumn();
                        int value = ((ColorNode) center).getTopLeft();

                        if (nodeRow - 1 == centerRow && nodeColumn - 1 == centerColumn) {
                            topLeft = value;
                        } else if (nodeRow - 1 == centerRow && nodeColumn == centerColumn) {
                            topLeft = value;
                            topRight = value;
                        } else if (nodeRow - 1 == centerRow && nodeColumn + 1 == centerColumn) {
                            topRight = value;
                        } else if (nodeRow == centerRow && nodeColumn - 1 == centerColumn) {
                            topLeft = value;
                            botLeft = value;
                        } else if (nodeRow == centerRow && nodeColumn + 1 == centerColumn) {
                            topRight = value;
                            botRight = value;
                        } else if (nodeRow + 1 == centerRow && nodeColumn - 1 == centerColumn) {
                            botLeft = value;
                        } else if (nodeRow + 1 == centerRow && nodeColumn == centerColumn) {
                            botLeft = value;
                            botRight = value;
                        } else if (nodeRow + 1 == centerRow && nodeColumn + 1 == centerColumn) {
                            botRight = value;
                        }
                    }

                    if (isLeftNodeInRow(node)) {
                        if (isTopNodeInColumn(node)) {
                            topLeft = topRight = botLeft = botRight;
                        } else if (isBottomNodeInColumn(node)) {
                            topLeft = botRight = botLeft = topRight;
                        } else {
                            topLeft = topRight;
                            botLeft = botRight;
                        }
                    } else if (isRightNodeInRow(node)) {
                        if (isTopNodeInColumn(node)) {
                            topLeft = topRight = botRight = botLeft;
                        } else if (isBottomNodeInColumn(node)) {
                            topRight = botLeft = botRight = topLeft;
                        } else {
                            topRight = topLeft;
                            botRight = botLeft;
                        }
                    } else if (isTopNodeInColumn(node)) {
                        topLeft = botLeft;
                        topRight = botRight;
                    } else if (isBottomNodeInColumn(node)) {
                        botLeft = topLeft;
                        botRight = topRight;
                    }

                    if ((neighboringCenters.size() == 2 || neighboringCenters.size() == 3)
                            && getUnassigned(topLeft, topRight, botLeft, botRight) == 1) {
                        if (topLeft == topRight) {
                            if (botLeft == -1) botLeft = botRight;
                            else botRight = botLeft;
                        } else if (topLeft == botLeft) {
                            if (topRight == -1) topRight = botRight;
                            else botRight = topRight;
                        } else if (botLeft == botRight) {
                            if (topLeft == -1) topLeft = topRight;
                            else topRight = topLeft;
                        } else if (topRight == botRight) {
                            if (topLeft == -1) topLeft = botLeft;
                            else botLeft = topLeft;
                        }
                    }

                }
                ((ColorNode) node).setColor(topLeft, topRight, botLeft, botRight);
            }
        }

        for (Node node : mNodes) {
            if (((ColorNode) node).getTopLeft() == -1) {
                ColorNode aboveNode = null;
                ColorNode leftNode = null;

                Position above = new Position(
                        ((Position) node.getPosition()).getRow() - 1,
                        ((Position) node.getPosition()).getColumn()
                );

                Position left = new Position(
                        ((Position) node.getPosition()).getRow(),
                        ((Position) node.getPosition()).getColumn() - 1
                );

                for (Node other : mNodes) {
                    if (other.getPosition().equals(above)) {aboveNode = (ColorNode) other; }
                    if (other.getPosition().equals(left)) { leftNode = (ColorNode) other; }
                }

                if (aboveNode != null) {
                    if (aboveNode.getTopLeft() != aboveNode.getTopRight()
                            || aboveNode.getTopLeft() != aboveNode.getBotLeft()
                            || aboveNode.getTopLeft() != aboveNode.getBotRight()) {
                        aboveNode = null;
                    } else {
                        boolean shareCenter = false;
                        for (Node center : mCenters) {
                            if (aboveNode.isAdjacent(center) && node.isAdjacent(center))
                                shareCenter = true;
                        }
                        if (!shareCenter) aboveNode = null;
                    }
                }

                if (leftNode != null) {
                    if (leftNode.getTopLeft() != leftNode.getTopRight()
                            || leftNode.getTopLeft() != leftNode.getBotLeft()
                            || leftNode.getTopLeft() != leftNode.getBotRight()) {
                        leftNode = null;
                    } else {
                        boolean shareCenter = false;
                        for (Node center : mCenters) {
                            if (leftNode.isAdjacent(center) && node.isAdjacent(center))
                                shareCenter = true;
                        }
                        if (!shareCenter) leftNode = null;
                    }
                }

                if (aboveNode != null && leftNode != null) {
                    if (aboveNode.getTopLeft() == leftNode.getTopLeft()) {
                        ((ColorNode) node).setTopLeft(aboveNode.getTopLeft());
                    }   else {
                        for (Node other : mNodes) {
                            if (other.isAdjacent(aboveNode) && other.isAdjacent(leftNode)
                                    && other.isAdjacent(node)) {
                                if (((ColorNode) other).getTopLeft() == aboveNode.getTopLeft()) {
                                    ((ColorNode) node).setTopLeft(leftNode.getTopLeft());
                                } else if (((ColorNode) other).getTopLeft() == leftNode
                                        .getTopLeft()) {
                                    ((ColorNode) node).setTopLeft(aboveNode.getTopLeft());
                                }
                            }
                        }
                    }
                } else if (aboveNode != null) {
                    ((ColorNode) node).setTopLeft(aboveNode.getTopLeft());
                } else if (leftNode != null) {
                    ((ColorNode) node).setTopLeft(leftNode.getTopLeft());
                }
            }
            if (((ColorNode) node).getTopRight() == - 1) {
                ColorNode aboveNode = null;
                ColorNode rightNode = null;

                Position above = new Position(
                        ((Position) node.getPosition()).getRow() - 1,
                        ((Position) node.getPosition()).getColumn()
                );

                Position right = new Position(
                        ((Position) node.getPosition()).getRow(),
                        ((Position) node.getPosition()).getColumn() + 1
                );

                for (Node other : mNodes) {
                    if (other.getPosition().equals(above)) { aboveNode = (ColorNode) other; }
                    if (other.getPosition().equals(right)) { rightNode = (ColorNode) other; }
                }

                if (aboveNode != null) {
                    if (aboveNode.getTopLeft() != aboveNode.getTopRight()
                            || aboveNode.getTopLeft() != aboveNode.getBotLeft()
                            || aboveNode.getTopLeft() != aboveNode.getBotRight()) {
                        aboveNode = null;
                    } else {
                        boolean shareCenter = false;
                        for (Node center : mCenters) {
                            if (aboveNode.isAdjacent(center) && node.isAdjacent(center))
                                shareCenter = true;
                        }
                        if (!shareCenter) aboveNode = null;
                    }
                }

                if (rightNode != null) {
                    if (rightNode.getTopLeft() != rightNode.getTopRight()
                            || rightNode.getTopLeft() != rightNode.getBotLeft()
                            || rightNode.getTopLeft() != rightNode.getBotRight()) {
                        rightNode = null;
                    } else {
                        boolean shareCenter = false;
                        for (Node center : mCenters) {
                            if (rightNode.isAdjacent(center) && node.isAdjacent(center))
                                shareCenter = true;
                        }
                        if (!shareCenter) rightNode = null;
                    }
                }

                if (aboveNode != null && rightNode != null) {
                    if (aboveNode.getTopLeft() == rightNode.getTopLeft()) {
                        ((ColorNode) node).setTopRight(aboveNode.getTopLeft());
                    }  else {
                        for (Node other : mNodes) {
                            if (other.isAdjacent(aboveNode) && other.isAdjacent(rightNode)
                                    && other.isAdjacent(node)) {
                                if (((ColorNode) other).getTopLeft() == aboveNode.getTopLeft()) {
                                    ((ColorNode) node).setTopRight(rightNode.getTopLeft());
                                } else if (((ColorNode) other).getTopLeft() == rightNode
                                        .getTopLeft()) {
                                    ((ColorNode) node).setTopRight(aboveNode.getTopLeft());
                                }
                            }
                        }
                    }
                } else if (aboveNode != null) {
                    ((ColorNode) node).setTopRight(aboveNode.getTopLeft());
                } else if (rightNode != null) {
                    ((ColorNode) node).setTopRight(rightNode.getTopLeft());
                }
            }
            if (((ColorNode) node).getBotLeft() == -1) {
                ColorNode belowNode = null;
                ColorNode leftNode = null;

                Position below = new Position(
                        ((Position) node.getPosition()).getRow() + 1,
                        ((Position) node.getPosition()).getColumn()
                );

                Position left = new Position(
                        ((Position) node.getPosition()).getRow(),
                        ((Position) node.getPosition()).getColumn() - 1
                );

                for (Node other : mNodes) {
                    if (other.getPosition().equals(below)) { belowNode = (ColorNode) other; }
                    if (other.getPosition().equals(left)) { leftNode = (ColorNode) other; }
                }

                if (belowNode != null) {
                    if (belowNode.getTopLeft() != belowNode.getTopRight()
                            || belowNode.getTopLeft() != belowNode.getBotLeft()
                            || belowNode.getTopLeft() != belowNode.getBotRight()) {
                        belowNode = null;
                    }  else {
                        boolean shareCenter = false;
                        for (Node center : mCenters) {
                            if (belowNode.isAdjacent(center) && node.isAdjacent(center))
                                shareCenter = true;
                        }
                        if (!shareCenter) belowNode = null;
                    }
                }

                if (leftNode != null) {
                    if (leftNode.getTopLeft() != leftNode.getTopRight()
                            || leftNode.getTopLeft() != leftNode.getBotLeft()
                            || leftNode.getTopLeft() != leftNode.getBotRight()) {
                        leftNode = null;
                    } else {
                        boolean shareCenter = false;
                        for (Node center : mCenters) {
                            if (leftNode.isAdjacent(center) && node.isAdjacent(center))
                                shareCenter = true;
                        }
                        if (!shareCenter) leftNode = null;
                    }
                }

                if (belowNode != null && leftNode != null) {
                    if (belowNode.getTopLeft() == leftNode.getTopLeft()) {
                        ((ColorNode) node).setBotLeft(belowNode.getTopLeft());
                    }  else {
                        for (Node other : mNodes) {
                            if (other.isAdjacent(belowNode) && other.isAdjacent(leftNode)
                                    && other.isAdjacent(node)) {
                                if (((ColorNode) other).getTopLeft() == belowNode.getTopLeft()) {
                                    ((ColorNode) node).setBotLeft(leftNode.getTopLeft());
                                } else if (((ColorNode) other).getTopLeft() == leftNode
                                        .getTopLeft()) {
                                    ((ColorNode) node).setBotLeft(belowNode.getTopLeft());
                                }
                            }
                        }
                    }
                } else if (belowNode != null) {
                    ((ColorNode) node).setBotLeft(belowNode.getTopLeft());
                } else if (leftNode != null) {
                    ((ColorNode) node).setBotLeft(leftNode.getTopLeft());
                }
            }
            if (((ColorNode) node).getBotRight() == -1) {
                ColorNode belowNode = null;
                ColorNode rightNode = null;

                Position below = new Position(
                        ((Position) node.getPosition()).getRow() + 1,
                        ((Position) node.getPosition()).getColumn()
                );

                Position right = new Position(
                        ((Position) node.getPosition()).getRow(),
                        ((Position) node.getPosition()).getColumn() + 1
                );

                for (Node other : mNodes) {
                    if (other.getPosition().equals(below)) { belowNode = (ColorNode) other; }
                    if (other.getPosition().equals(right)) { rightNode = (ColorNode) other; }
                }

                if (belowNode != null) {
                    if (belowNode.getTopLeft() != belowNode.getTopRight()
                            || belowNode.getTopLeft() != belowNode.getBotLeft()
                            || belowNode.getTopLeft() != belowNode.getBotRight()) {
                        belowNode = null;
                    } else {
                        boolean shareCenter = false;
                        for (Node center : mCenters) {
                            if (belowNode.isAdjacent(center) && node.isAdjacent(center))
                                shareCenter = true;
                        }
                        if (!shareCenter) belowNode = null;
                    }
                }

                if (rightNode != null) {
                    if (rightNode.getTopLeft() != rightNode.getTopRight()
                            || rightNode.getTopLeft() != rightNode.getBotLeft()
                            || rightNode.getTopLeft() != rightNode.getBotRight()) {
                        rightNode = null;
                    } else {
                        boolean shareCenter = false;
                        for (Node center : mCenters) {
                            if (rightNode.isAdjacent(center) && node.isAdjacent(center))
                                shareCenter = true;
                        }
                        if (!shareCenter) rightNode = null;
                    }
                }

                if (belowNode != null && rightNode != null) {
                    if (belowNode.getTopLeft() == rightNode.getTopLeft()) {
                        ((ColorNode) node).setBotRight(belowNode.getTopLeft());
                    } else {
                        for (Node other : mNodes) {
                            if (other.isAdjacent(belowNode) && other.isAdjacent(rightNode)
                                    && other.isAdjacent(node)) {
                                if (((ColorNode) other).getTopLeft() == belowNode.getTopLeft()) {
                                    ((ColorNode) node).setBotRight(rightNode.getTopLeft());
                                } else if (((ColorNode) other).getTopLeft() == rightNode
                                        .getTopLeft()) {
                                    ((ColorNode) node).setBotRight(belowNode.getTopLeft());
                                }
                            }
                        }
                    }
                } else if (belowNode != null) {
                    ((ColorNode) node).setBotRight(belowNode.getTopLeft());
                } else if (rightNode != null) {
                    ((ColorNode) node).setBotRight(rightNode.getTopLeft());
                }
            }
        }
    }

    @Override
    public Board createBoard() {
        return new Board(mCenters, mNodes);
    }

    @Override
    public Node createNode(int row, int col) {
        switch (mBoardSchema.getNodeType()) {
            case ORIENTABLE:
                return new OrientableColorNode(
                        new Position(row, col),
                        new Color()
                );
            case PERMUTABLE:
                return new PermutableColorNode(
                        new Position(row, col),
                        new Color()
                );
            case ROTATABLE:
                return new RotatableColorNode(
                        new Position(row, col),
                        new Color()
                );
        }
        return new RotatableColorNode(
                new Position(row, col),
                new Color()
        );
    }

    @Override
    public Node createNode(int row, int col, int val) {
        switch (mBoardSchema.getNodeType()) {
            case ORIENTABLE:
                return new OrientableColorNode(
                        new Position(row, col),
                        new Color(val)
                );
            case PERMUTABLE:
                return new PermutableColorNode(
                        new Position(row, col),
                        new Color(val)
                );
            case ROTATABLE:
                return new RotatableColorNode(
                        new Position(row, col),
                        new Color(val)
                );
        }
        return new RotatableColorNode(
                new Position(row, col),
                new Color(val)
        );
    }
}
