package com.apps.scott.circumago.common.utility;

import java.security.SecureRandom;

/**
 * Created by Scott on 10/14/2016.
 */
public class Random {
    public static final SecureRandom RANDOM = new SecureRandom();

    public static int getInt(int max) {
        return RANDOM.nextInt(max + 1);
    }
}
