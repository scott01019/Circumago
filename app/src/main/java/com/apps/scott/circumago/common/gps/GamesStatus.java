package com.apps.scott.circumago.common.gps;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Scott on 9/22/2016.
 */
public class GamesStatus extends Observable implements Observer {
    private static GamesStatus mGamesStatus = new GamesStatus();

    private boolean mIsSignedIn = false;

    public static GamesStatus getInstance() {
        if (mGamesStatus == null) mGamesStatus = new GamesStatus();
        return mGamesStatus;
    }

    private GamesStatus() {}

    @Override
    public void update(Observable o, Object arg) {
        mIsSignedIn = (boolean) arg;
        setChanged();
        notifyObservers();
    }

    public boolean isSignedIn() { return mIsSignedIn; }
}
