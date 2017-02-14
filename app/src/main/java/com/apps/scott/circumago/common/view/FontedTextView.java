package com.apps.scott.circumago.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.apps.scott.circumago.R;
import com.apps.scott.circumago.common.caches.FontCache;

import java.lang.ref.WeakReference;

/**
 * Created by Scott on 9/22/2016.
 */
public class FontedTextView extends TextView {
    private String mFont;
    
    public FontedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FontedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setSingleLine(true);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FontedTextView,
                0, 0
        );

        try {
            mFont = a.getString(R.styleable.FontedTextView_font);
        } finally {
            a.recycle();
        }

        if (mFont != null) setTypeface(FontCache.getTypeface(mFont, context));
    }
}
