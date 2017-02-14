package com.apps.scott.circumago.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.activity.SplashActivity;
import com.apps.scott.circumago.common.billing.IabBroadcastReceiver;
import com.apps.scott.circumago.common.billing.IabException;
import com.apps.scott.circumago.common.billing.IabHelper;
import com.apps.scott.circumago.common.billing.IabResult;
import com.apps.scott.circumago.common.billing.Inventory;
import com.apps.scott.circumago.common.billing.Purchase;
import com.apps.scott.circumago.common.utility.billing.IabKey;
import com.apps.scott.circumago.common.utility.billing.NoAds;

/**
 * Created by Scott on 11/30/2016.
 */
public class QueryNoAdsFragment extends Fragment
        implements IabBroadcastReceiver.IabBroadcastListener {
    public static final int RC_REQUEST = 100002;
    public static final String SKU_PREMIUM = "premium";
    private IabHelper mHelper;
    private boolean mPremium;
    IabBroadcastReceiver mBroadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBroadcastReceiver != null) {
            getActivity().unregisterReceiver(mBroadcastReceiver);
        }

        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBiller();
    }

    private void initBiller() {
        String base64EncodedPublicKey = IabKey.getKey();
        mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                mBroadcastReceiver = new IabBroadcastReceiver(QueryNoAdsFragment.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                getActivity().registerReceiver(mBroadcastReceiver, broadcastFilter);

                try {
                    Purchase purchase = mHelper.queryInventory().getPurchase(SKU_PREMIUM);
                    if (purchase != null && purchase.getPurchaseState() == 0) mPremium = true;
                    else mPremium = false;
                    NoAds.setNoAds(mPremium);
                    ((SplashActivity) getActivity()).finishSplash();
                } catch (IabException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            if (mHelper == null) return;

            if (result.isFailure()) {
                return;
            }

            Purchase noAdsPurchase = inventory.getPurchase(SKU_PREMIUM);
            mPremium = (noAdsPurchase != null);
            NoAds.setNoAds(mPremium);
            ((SplashActivity) getActivity()).finishSplash();
        }
    };

    public IabHelper getHelper() { return mHelper; }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        if (request == 10002) {
            super.onActivityResult(request, result, data);
        }
    }

    @Override
    public void receivedBroadcast() {
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
        }
    }
}
