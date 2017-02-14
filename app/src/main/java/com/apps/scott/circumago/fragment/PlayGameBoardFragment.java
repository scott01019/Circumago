package com.apps.scott.circumago.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.twisty.board.util.BoardSchema;
import com.apps.scott.circumago.common.utility.listeners.OnScrambledListener;
import com.apps.scott.circumago.common.utility.listeners.OnSolvedListener;
import com.apps.scott.circumago.common.utility.Settings;
import com.apps.scott.circumago.common.view.board.BoardView;
import com.apps.scott.circumago.common.view.board.touchlisteners.SwipeToRotate;
import com.apps.scott.circumago.common.view.board.touchlisteners.TapToRotate;


/**
 * Created by Scott on 10/14/2016.
 */
public class PlayGameBoardFragment extends Fragment implements View.OnClickListener {

    private BoardView mBoardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_game_board, container, false);
        init(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBoardView.isScrambled() && !mBoardView.isSolved()) setBoardViewTouchListener();
        if (!mBoardView.isScrambled()) setBoardViewScrambleAnimation();
    }

    private void init(View view) {
        mBoardView = (BoardView) view.findViewById(R.id.fragment_play_game_board_board);

        mBoardView.setOnScrambledListener((OnScrambledListener) getActivity());
        view.findViewById(R.id.fragment_play_game_board_scramble).setOnClickListener(this);

        view.findViewById(R.id.fragment_play_game_board_timer).setVisibility(View.GONE);

        setBoardViewScrambleAnimation();
    }

    protected void setBoardViewScrambleAnimation() {
        mBoardView.setScrambleAnimation(Settings.isAnimatedScramble(getActivity()));
    }

    protected void setBoardViewTouchListener() {
        if (Settings.isTapToRotate(getActivity()))
            mBoardView.setBoardViewOnTouchListener(new TapToRotate());
        else if (Settings.isSwipeToRotate(getActivity()))
            mBoardView.setBoardViewOnTouchListener(new SwipeToRotate());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_play_game_board_scramble) {
            setBoardViewTouchListener();
            mBoardView.scramble();
            mBoardView.setOnSolvedListener((OnSolvedListener) getActivity());
            v.setVisibility(View.GONE);
        }
    }
}
