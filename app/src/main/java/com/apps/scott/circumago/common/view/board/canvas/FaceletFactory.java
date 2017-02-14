package com.apps.scott.circumago.common.view.board.canvas;


import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.NodeType;

/**
 * Created by Scott on 10/10/2016.
 */
public class FaceletFactory {
    public static Facelet createFacelet(Node node, NodeType nodeType, boolean isCenter) {
        if (isCenter) return new CenterFacelet(node, true);
        else {
            switch (nodeType) {
                case ROTATABLE:
                    return new RotatableFacelet(node, false);
                case ORIENTABLE:
                    return new OrientableFacelet(node, false);
                case PERMUTABLE:
                    return new PermutableFacelet(node, false);
            }
            return null;
        }
    }
}
