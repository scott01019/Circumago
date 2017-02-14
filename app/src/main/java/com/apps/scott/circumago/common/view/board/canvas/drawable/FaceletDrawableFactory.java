package com.apps.scott.circumago.common.view.board.canvas.drawable;


import com.apps.scott.circumago.common.twisty.node.Node;
import com.apps.scott.circumago.common.twisty.node.color.ColorNode;
import com.apps.scott.circumago.common.view.board.canvas.drawable.color.ColorCenterFaceletDrawable;
import com.apps.scott.circumago.common.view.board.canvas.drawable.color.ColorFaceletDrawable;

/**
 * Created by Scott on 10/10/2016.
 */
public class FaceletDrawableFactory {
    public static FaceletDrawable createFaceletDrawable(Node node, boolean isCenter) {
        if (isCenter) return new ColorCenterFaceletDrawable((ColorNode) node);
        else return new ColorFaceletDrawable((ColorNode) node);
    }
}
