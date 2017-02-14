package com.apps.scott.circumago.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.utility.billing.NoAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by Scott on 11/15/2016.
 */
public class BannerAdFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner_ad, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!NoAds.getNoAds()) {
            MobileAds.initialize(
                    getActivity().getApplicationContext(),
                    "ca-app-pub-4560952593696586~1901781752"
            );
            AdView adView = (AdView) getActivity().findViewById(R.id.fragment_banner_ad_ad);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        } else {
            AdView adView = (AdView) getActivity().findViewById(R.id.fragment_banner_ad_ad);
            adView.setVisibility(View.GONE);
        }
    }
}
