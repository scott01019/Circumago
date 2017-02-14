package com.apps.scott.circumago.fragment;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.twisty.board.template.Square9;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.utility.BoardColorSchemePicker;
import com.apps.scott.circumago.common.utility.Observable;
import com.apps.scott.circumago.common.utility.listeners.OnViewMeasuredListener;
import com.apps.scott.circumago.common.view.board.BoardView;

import java.util.Arrays;

/**
 * Created by Scott on 10/13/2016.
 */
public class BoardColorSchemePickerFragment extends Fragment
        implements View.OnClickListener, OnViewMeasuredListener {
    private BoardView mBoardView;
    private BoardColorSchemePicker mColorSchemePicker;
    private Observable mObservable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_preview, container, false);
        init(view);
        return view;
    }

    public Observable getObservable() { return mObservable; }

    public void updateColorSchemes(int[][] colorSchemes) {
        mColorSchemePicker.setColorSchemes(colorSchemes);
    }

    public void setId(int id) { mObservable.setId(id); }

    public int[] getColorScheme() {
        return mColorSchemePicker.getColorScheme();
    }

    private void init(View view) {
        mColorSchemePicker = new BoardColorSchemePicker(Square9.COLOR_SCHEMES[7], 0);
        mBoardView = ((BoardView) view.findViewById(R.id.preview_board));
        mBoardView.setOnViewMeasuredListener(this);
        mBoardView.setOnTouchListener(null);
        view.findViewById(R.id.left).setOnClickListener(this);
        view.findViewById(R.id.right).setOnClickListener(this);
        mObservable = new Observable();
    }

    public void updateBoardPreview(BoardSchema boardSchema) {
        mBoardView.setupBoard(boardSchema);
        ObjectAnimator.ofFloat(mBoardView, "alpha", 0.0f, 1).setDuration(300).start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left) {
            int[] init = getColorScheme();
            mColorSchemePicker.decrementColorSchemeIndex();
            if (!Arrays.equals(init, getColorScheme())) mObservable.notifyObservers();
        } else if (v.getId() == R.id.right) {
            int[] init = getColorScheme();
            mColorSchemePicker.incrementColorSchemeIndex();
            if (!Arrays.equals(init, getColorScheme())) mObservable.notifyObservers();
        }
    }

    @Override
    public void viewMeasured() {
        mObservable.notifyObservers();
    }
}
