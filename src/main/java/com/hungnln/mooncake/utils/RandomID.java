package com.hungnln.mooncake.utils;

import java.util.Random;


public class RandomID {

    private static final int CAKE_ID_LENGTH = 99999;
    private static final int ORDER_ID_LENGTH = 99999;

    public static String getRanDomCakeID() {
        Random random = new Random();
        String randomIDStr = String.valueOf(random.nextInt(CAKE_ID_LENGTH));
        return randomIDStr;
    }

    public static String getRanDomOrderID() {
        Random random = new Random();
        String randomIDStr = String.valueOf(random.nextInt(ORDER_ID_LENGTH));
        return randomIDStr;
    }
}
