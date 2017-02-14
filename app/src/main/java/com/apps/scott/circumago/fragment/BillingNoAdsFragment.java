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
import com.apps.scott.circumago.common.billing.IabBroadcastReceiver;
import com.apps.scott.circumago.common.billing.IabException;
import com.apps.scott.circumago.common.billing.IabHelper;
import com.apps.scott.circumago.common.billing.IabResult;
import com.apps.scott.circumago.common.billing.Inventory;
import com.apps.scott.circumago.common.billing.Purchase;
import com.apps.scott.circumago.common.utility.billing.IabKey;
import com.apps.scott.circumago.common.utility.billing.NoAds;
import com.apps.scott.circumago.common.view.FontedTextView;

/**
 * Created by Scott on 11/30/2016.
 */
public class BillingNoAdsFragment extends Fragment
        implements IabBroadcastReceiver.IabBroadcastListener, View.OnClickListener {

    public static final int RC_REQUEST = 10001;
    public static final String SKU_PREMIUM = "premium";
    private IabHelper mHelper;
    private boolean mPremium;
    IabBroadcastReceiver mBroadcastReceiver;
    private FontedTextView mGetPremium;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_premium, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBiller();
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

    private void initBiller() {
        String base64EncodedPublicKey = IabKey.getKey();
        mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                mBroadcastReceiver = new IabBroadcastReceiver(BillingNoAdsFragment.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                getActivity().registerReceiver(mBroadcastReceiver, broadcastFilter);

                try {
                    Purchase purchase = mHelper.queryInventory().getPurchase(SKU_PREMIUM);
                    if (purchase != null && purchase.getPurchaseState() == 0) mPremium = true;
                    else mPremium = false;
                    updateUi();
                } catch (IabException e) {
                    e.printStackTrace();
                }
            }
        });

        mGetPremium = (FontedTextView) getActivity().findViewById(R.id.fragment_get_premium);
        mGetPremium.setOnClickListener(this);
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

            updateUi();
        }
    };

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (mHelper == null) return;

            if (result.isFailure()) {
                return;
            }

            mPremium = true;
            updateUi();
        }
    };

    private void updateUi() {
        NoAds.setNoAds(mPremium);
        if (mPremium) {
            mGetPremium.setText(getString(R.string.fragment_no_ads_thank_you));
            mGetPremium.setOnClickListener(null);
        } else {
            mGetPremium.setVisibility(View.VISIBLE);
        }
    }

    public IabHelper getHelper() { return mHelper; }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        if (request == 10001 || request == 10002) {
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_get_premium) {
            try {
                mHelper.launchPurchaseFlow(
                        getActivity(),
                        SKU_PREMIUM,
                        RC_REQUEST,
                        mPurchaseFinishedListener,
                        ""
                );
            } catch (IabHelper.IabAsyncInProgressException e) {
            }
        }
    }
}
