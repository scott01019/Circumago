package com.apps.scott.circumago.common.view.pager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.view.FontedTextView;

import java.util.ArrayList;

/**
 * Created by Scott on 10/13/2016.
 */
public class FontedTextViewPagerAdapter extends PagerAdapter {
    private ArrayList<LinearLayout> mTextViews;


    public FontedTextViewPagerAdapter(String[] strings, Context context) {
        init(strings, context);
    }

    private void init(String[] strings, Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);

        mTextViews = new ArrayList<>();

        LinearLayout first = (LinearLayout) inflater.inflate(R.layout.fonted_text_view, null, false);
        ((FontedTextView) first.getChildAt(0)).setText(strings[strings.length - 1]);
        mTextViews.add(first);

        for (String str : strings) {
            LinearLayout ftv = (LinearLayout) inflater.inflate(R.layout.fonted_text_view, null, false);
            ((FontedTextView) ftv.getChildAt(0)).setText(str);
            mTextViews.add(ftv);
        }

        LinearLayout last = (LinearLayout) inflater.inflate(R.layout.fonted_text_view, null, false);
        ((FontedTextView) last.getChildAt(0)).setText(strings[0]);
        mTextViews.add(last);
    }

    public String getStringOfItem(int i) {
        return (String) ((FontedTextView) mTextViews.get(i).getChildAt(0)).getText();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        collection.addView(mTextViews.get(position));
        return mTextViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mTextViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
