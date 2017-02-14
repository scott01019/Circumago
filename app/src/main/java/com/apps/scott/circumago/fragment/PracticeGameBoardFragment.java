package com.apps.scott.circumago.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.activity.PracticeActivity;
import com.apps.scott.circumago.common.utility.Settings;
import com.apps.scott.circumago.common.utility.listeners.OnViewMeasuredListener;
import com.apps.scott.circumago.common.view.board.BoardView;
import com.apps.scott.circumago.common.view.board.touchlisteners.SwipeToRotate;
import com.apps.scott.circumago.common.view.board.touchlisteners.TapToRotate;

/**
 * Created by Scott on 11/3/2016.
 */
public class PracticeGameBoardFragment extends Fragment implements View.OnClickListener {
    private BoardView mBoardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_practice_game_board, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setBoardViewTouchListener();
    }

    private void init(View view) {
        mBoardView = (BoardView) view.findViewById(R.id.fragment_practice_game_board_board);
        view.findViewById(R.id.fragment_practice_game_board_scramble).setOnClickListener(this);
        view.findViewById(R.id.fragment_practice_game_board_solve).setOnClickListener(this);
        setBoardViewTouchListener();
    }

    public void prepareBoard() {
        mBoardView.setupBoard(((PracticeActivity) getActivity()).getBoardSchema());
    }

    protected void setBoardViewTouchListener() {
        if (Settings.isTapToRotate(getActivity()))
            mBoardView.setBoardViewOnTouchListener(new TapToRotate());
        else if (Settings.isSwipeToRotate(getActivity()))
            mBoardView.setBoardViewOnTouchListener(new SwipeToRotate());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_practice_game_board_scramble) {
            setBoardViewTouchListener();
            mBoardView.scramble();
        } else if (v.getId() == R.id.fragment_practice_game_board_solve) {
            prepareBoard();
        }
    }
}
