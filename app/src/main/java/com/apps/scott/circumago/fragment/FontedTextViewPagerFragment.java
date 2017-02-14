package com.apps.scott.circumago.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.utility.Observable;
import com.apps.scott.circumago.common.view.pager.FontedTextViewPager;
import com.apps.scott.circumago.common.view.pager.adapter.FontedTextViewPagerAdapter;


/**
 * Created by Scott on 10/13/2016.
 */
public class FontedTextViewPagerFragment extends Fragment
        implements View.OnClickListener {

    protected FontedTextViewPager mViewPager;
    protected Observable mObservable;
    private int mCurrentPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fonted_view_pager, container, false);
        init(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public Observable getObservable() { return mObservable; }
    public void setId(int id) { mObservable.setId(id); }
    public int getCurrentItem() { return mViewPager.getCurrentItem(); }

    public String getSelection() {
        return ((FontedTextViewPagerAdapter) mViewPager.getAdapter())
                .getStringOfItem(mViewPager.getCurrentItem());
    }

    public void setPageAdapter(String[] strings) {
        mViewPager.setAdapter(new FontedTextViewPagerAdapter(
                        strings, getActivity().getApplicationContext())
        );
        mViewPager.setOffscreenPageLimit(strings.length);
        mViewPager.setCurrentItem(1);
    }

    public void setItem(int item) {
        mViewPager.setCurrentItem(item);
    }

    private void init(View view) {
        mViewPager = (FontedTextViewPager) view.findViewById(R.id.viewpager);

        view.findViewById(R.id.left).setOnClickListener(this);
        view.findViewById(R.id.right).setOnClickListener(this);

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int pageCount = mViewPager.getAdapter().getCount();
                    if (mCurrentPage == 0)
                        mViewPager.setCurrentItem(pageCount - 2, false);
                    else if (mCurrentPage == pageCount - 1)
                        mViewPager.setCurrentItem(1, false);
                }
            }
        });

        mObservable = new Observable();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left) {
            String init = getSelection();
            int tab = mViewPager.getCurrentItem();
            if (tab > 0) {
                --tab;
                mViewPager.setCurrentItem(tab);
            } else if (tab == 0) {
                mViewPager.setCurrentItem(tab);
            }
            if (getSelection() != init) mObservable.notifyObservers();
        } else if (v.getId() == R.id.right) {
            String init = getSelection();
            int tab = mViewPager.getCurrentItem();
            ++tab;
            mViewPager.setCurrentItem(tab);
            if (getSelection() != init) mObservable.notifyObservers();
        }
    }
}
