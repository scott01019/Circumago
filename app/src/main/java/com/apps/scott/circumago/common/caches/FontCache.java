package com.apps.scott.circumago.common.caches;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Scott on 9/22/2016.
 */
public class FontCache {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();
    private static final String FONT_FILE = "fonts/";

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(
                        context.getAssets(),
                        FONT_FILE + fontname
                );
                fontCache.put(fontname, typeface);
            } catch (Exception e) {
                return null;
            }
        }
        return typeface;
    }
}
