package com.apps.scott.circumago.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.caches.StringArrayCache;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.twisty.board.util.BoardUtil;
import com.apps.scott.circumago.common.twisty.node.NodeType;
import com.apps.scott.circumago.common.utility.Observer;

import java.util.ArrayList;

/**
 * Created by Scott on 11/3/2016.
 */
public class BoardSetupFragment extends Fragment
    implements Observer {
    public static final String BOARD_SCHEMA = "circumago.activity.BoardSetupFragment.BOARD_SCHEMA";
    public static final String BOARD_NAME_TAG = "circumago.activity.BoardSetupFragment.BOARD_NAME";
    private BoardSchema mBoardSchema;

    private FontedTextViewPagerFragment mBoardSelector;
    private FontedTextViewPagerFragment mNodeSelector;
    private FontedTextViewPagerFragment mBoardFormatSelector;
    private FontedTextViewPagerFragment mColorSelector;
    private BoardColorSchemePickerFragment mColorSchemeSelector;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_setup, container, false);
        init();
        return view;
    }

    private void init() {
        String[] nodeTypes = StringArrayCache.getStrings(
                R.array.node_types,
                getActivity().getApplicationContext()
        );
        String[] boardTypes = StringArrayCache.getStrings(
                R.array.board_types_corners_and_edges,
                getActivity().getApplicationContext()
        );
        String[] boardFormats = StringArrayCache.getStrings(
                R.array.board_formats,
                getActivity().getApplicationContext()
        );

        mBoardFormatSelector = (FontedTextViewPagerFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_board_setup_fragment_board_format);
        mBoardFormatSelector.setPageAdapter(boardFormats);

        mBoardSelector = (FontedTextViewPagerFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_board_setup_fragment_board_type);
        mBoardSelector.setPageAdapter(boardTypes);

        mNodeSelector = (FontedTextViewPagerFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_board_setup_fragment_node_type);
        mNodeSelector.setPageAdapter(nodeTypes);

        mColorSelector = (FontedTextViewPagerFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_board_setup_fragment_num_colors);

        setColorSelector();
        mColorSelector.setItem(8);

        mColorSchemeSelector = (BoardColorSchemePickerFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_board_setup_fragment_preview_board);

        mBoardSchema = new BoardSchema(
                getNodeType(),
                getSelectedBoard(),
                getSelectedColorScheme(),
                hasCorners(),
                hasEdges()
        );

        mBoardSelector.getObservable().addObserver(this);
        mBoardSelector.setId(R.id.fragment_board_setup_fragment_board_type);
        mNodeSelector.getObservable().addObserver(this);
        mNodeSelector.setId(R.id.fragment_board_setup_fragment_node_type);
        mBoardFormatSelector.getObservable().addObserver(this);
        mBoardFormatSelector.setId(R.id.fragment_board_setup_fragment_board_format);
        mColorSelector.getObservable().addObserver(this);
        mColorSelector.setId(R.id.fragment_board_setup_fragment_num_colors);
        mColorSchemeSelector.getObservable().addObserver(this);
        mColorSchemeSelector.setId(R.id.fragment_board_setup_fragment_preview_board);
    }

    private int[][] getColorSchemes() {
        return BoardUtil.getBoard(mBoardSelector.getSelection())
                .getColorSchemes(getSelectedNumberOfColors());
    }

    private int getNumFacesOfBoard() {
        return BoardUtil.getBoard(mBoardSelector.getSelection()).getNumColors();
    }

    private int[][] getSelectedBoard() {
        return BoardUtil.getBoard(mBoardSelector.getSelection()).getBoard();
    }

    private int[] getSelectedColorScheme() {
        return mColorSchemeSelector.getColorScheme();
    }

    private int getSelectedNumberOfColors() {
        return Integer.valueOf(mColorSelector.getSelection());
    }

    private boolean hasCorners() {
        String current = mBoardFormatSelector.getSelection();
        return (current.equals("Corners and Edges") || current.equals("Corners Only"));
    }

    private boolean hasEdges() {
        String current = mBoardFormatSelector.getSelection();
        return (current.equals("Corners and Edges") || current.equals("Edges Only"));
    }

    private NodeType getNodeType() {
        String current = mNodeSelector.getSelection();
        if (current.equals("Rotatable"))
            return NodeType.ROTATABLE;
        if (current.equals("Permutable"))
            return NodeType.PERMUTABLE;
        if (current.equals("Orientable"))
            return NodeType.ORIENTABLE;
        return null;
    }

    private void updateBoardSelector() {
        String[] boardTypes = StringArrayCache.getStrings(
                getBoards(),
                getActivity().getApplicationContext()
        );
        mBoardSelector.setPageAdapter(boardTypes);
    }

    private void updateColorSelector() {
        setColorSelector();
        mColorSelector.setItem(getNumFacesOfBoard() - 1);
    }

    private void updateColorSchemeSelector() {
        mColorSchemeSelector.updateColorSchemes(getColorSchemes());
    }

    private int getBoards() {
        if (mBoardSchema.hasCorners()) {
            if (mBoardSchema.hasEdges()) return R.array.board_types_corners_and_edges;
            else return R.array.board_types_corners;
        } else return R.array.board_types_edges;
    }

    private void setColorSelector() {
        ArrayList<String> numColors = new ArrayList<>();
        for (int i = 2; i <= getNumFacesOfBoard(); ++i) numColors.add(String.valueOf(i));
        mColorSelector.setPageAdapter(numColors.toArray(new String[0]));
    }

    private void updateBoardSchema() {
        mBoardSchema.setHasCorners(hasCorners());
        mBoardSchema.setHasEdges(hasEdges());
        mBoardSchema.setBoard(getSelectedBoard());
        mBoardSchema.setNodeType(getNodeType());
        mBoardSchema.setColorScheme(getSelectedColorScheme());
    }

    public Bundle getBoardBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BOARD_SCHEMA, mBoardSchema);
        bundle.putString(BOARD_NAME_TAG, mBoardSelector.getSelection());
        return bundle;
    }

    @Override
    public void update(int id) {
        updateBoardSchema();
        switch (id) {
            case R.id.fragment_board_setup_fragment_board_format:
                updateBoardSelector();
                updateColorSelector();
                updateColorSchemeSelector();
                updateBoardSchema();
                mColorSchemeSelector.updateBoardPreview(mBoardSchema);
                break;
            case R.id.fragment_board_setup_fragment_board_type:
                updateColorSelector();
                updateColorSchemeSelector();
                updateBoardSchema();
                mColorSchemeSelector.updateBoardPreview(mBoardSchema);
                break;
            case R.id.fragment_board_setup_fragment_node_type:
                updateBoardSchema();
                break;
            case R.id.fragment_board_setup_fragment_num_colors:
                updateColorSchemeSelector();
                updateBoardSchema();
                mColorSchemeSelector.updateBoardPreview(mBoardSchema);
                break;
            case R.id.fragment_board_setup_fragment_preview_board:
                updateBoardSchema();
                mColorSchemeSelector.updateBoardPreview(mBoardSchema);
                break;
        }
    }
}
