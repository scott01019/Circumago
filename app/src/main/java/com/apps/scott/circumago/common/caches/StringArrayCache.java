package com.apps.scott.circumago.common.caches;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Scott on 10/12/2016.
 */
public class StringArrayCache {
    private static HashMap<Integer, String[]> stringCache = new HashMap<>();

    public static String[] getStrings(Integer id, Context context) {
        String[] str = stringCache.get(id);
        if (str == null) {
            try {
                str = context.getResources().getStringArray(id);
                stringCache.put(id, str);
            } catch (Exception e) {
                return null;
            }
        }
        return str;
    }
}
