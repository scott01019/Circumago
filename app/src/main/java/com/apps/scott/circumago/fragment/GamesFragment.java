package com.apps.scott.circumago.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.gps.GamesStatus;
import com.apps.scott.circumago.common.gps.GoogleConnection;
import com.apps.scott.circumago.common.gps.OnGoogleApiConnectedListener;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Scott on 9/22/2016.
 */
public class GamesFragment extends Fragment
        implements View.OnClickListener, Observer {

    public static int RC_ACHIEVEMENTS = 9002;
    public static int RC_LEADERBOARDS = 9003;

    private GoogleConnection mGoogleConnection;
    private OnGoogleApiConnectedListener mGoogleApiConnectedListener;
    private ImageView mGamesLeaderboardIcon;
    private ImageView mGamesControllerIcon;
    private ImageView mGamesAchievementsIcon;

    private GamesStatus mGamesStatus = GamesStatus.getInstance();
    private static boolean mExplicitSignOut = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_google_games, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    public boolean isConnected() {
        return mGoogleConnection != null && mGoogleConnection.isConnected();
    }

    public GoogleApiClient getApiClient() {
        return mGoogleConnection.getApiClient();
    }

    private void init() {
        mGamesStatus.addObserver(this);
        mGoogleConnection = new GoogleConnection(getActivity());
        mGoogleConnection.addObserver(mGamesStatus);

        mGamesAchievementsIcon = (ImageView) getView().findViewById(R.id.games_achievements);
        mGamesControllerIcon = (ImageView) getView().findViewById(R.id.games_controller);
        mGamesLeaderboardIcon = (ImageView) getView().findViewById(R.id.games_leaderboards);

        mGamesAchievementsIcon.setOnClickListener(this);
        mGamesControllerIcon.setOnClickListener(this);
        mGamesLeaderboardIcon.setOnClickListener(this);

        updateUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mExplicitSignOut || mGoogleConnection.isSignedIn())
            mGoogleConnection.connect();
        updateUI();
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.games_controller:
            if (mGoogleConnection.isSignedIn()) {
                mExplicitSignOut = true;
                mGoogleConnection.disconnect();
            } else {
                mGoogleConnection.setSignInClicked(true);
                mGoogleConnection.connect();
            }
            break;
        case R.id.games_leaderboards:
            if (mGoogleConnection.isSignedIn()) {
                startActivityForResult(mGoogleConnection.getLeaderboardsIntent(), RC_LEADERBOARDS);
            }
            break;
        case R.id.games_achievements:
            if (mGoogleConnection.isSignedIn()) {
                startActivityForResult(mGoogleConnection.getAchievementsIntent(), RC_ACHIEVEMENTS);
            }
            break;
        }
    }

    private void updateUI() {
        if (mGamesStatus.isSignedIn()) {
            mGamesAchievementsIcon.setImageResource(R.mipmap.games_achievements_green);
            mGamesControllerIcon.setImageResource(R.mipmap.games_controller_green);
            mGamesLeaderboardIcon.setImageResource(R.mipmap.games_leaderboards_green);
            if (mGoogleApiConnectedListener != null) mGoogleApiConnectedListener.onConnected();
        } else {
            if (mGoogleConnection.isConnected()) mGoogleConnection.disconnect();
            mGamesAchievementsIcon.setImageResource(R.mipmap.games_achievements_grey);
            mGamesControllerIcon.setImageResource(R.mipmap.games_controller_grey);
            mGamesLeaderboardIcon.setImageResource(R.mipmap.games_leaderboards_grey);
        }
    }

    public void setOnGoogleApiConnectedListener(OnGoogleApiConnectedListener listener) {
        mGoogleApiConnectedListener = listener;
    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        mGoogleConnection.onActivityResult(request, result, data);
    }
}