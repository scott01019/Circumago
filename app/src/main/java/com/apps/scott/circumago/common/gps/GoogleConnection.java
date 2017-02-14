package com.apps.scott.circumago.common.gps;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import java.lang.ref.WeakReference;
import java.util.Observable;

/**
 * Created by Scott on 9/22/2016.
 */
public class GoogleConnection extends Observable implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;
    private static boolean mSignedIn = false;

    private WeakReference<Activity> mActivityWeakReference;

    private GoogleApiClient mGoogleApiClient;

    public GoogleConnection(Activity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
        mGoogleApiClient = new GoogleApiClient.Builder(mActivityWeakReference.get())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
        mSignedIn = GamesStatus.getInstance().isSignedIn();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignedIn = true;
        setChanged();
        notifyObservers(mSignedIn);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) return;

        if (mSignInClicked || mAutoStartSignInflow) {
            mAutoStartSignInflow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            try {
                connectionResult.startResolutionForResult(mActivityWeakReference.get(), RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    public void onActivityResult(int request, int result, Intent intent) {
        if (request == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (result == Activity.RESULT_OK) {
                mGoogleApiClient.connect();
            }
        }
    }

    public void disconnect() {
        if (mGoogleApiClient.isConnected()) {
            if (mSignedIn)
                Games.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mSignedIn = false;
        }
        setChanged();
        notifyObservers(mSignedIn);
    }

    public void connect() {
        mGoogleApiClient.connect();
    }

    public static boolean isSignedIn() { return mSignedIn; }

    public GoogleApiClient getApiClient() { return mGoogleApiClient; }

    public boolean isConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    public void setSignInClicked(boolean clicked) { mSignInClicked = clicked; }

    public Intent getAchievementsIntent() {
        return Games.Achievements.getAchievementsIntent(mGoogleApiClient);
    }

    public Intent getLeaderboardsIntent() {
        return Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient);
    }
}
