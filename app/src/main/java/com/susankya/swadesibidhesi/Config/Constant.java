package com.susankya.swadesibidhesi.Config;

import android.util.Base64;

public class Constant {
    public static final String CONSUMER_KEY     = "ck_ab25e0a679e9c13700132bf4cba4b5055513ff6c";
    public static final String CONSUMER_SECRET  = "cs_75f253b398912c3e6f10cff1fb3ee570dc7bbd27";
    public static final String HOST = "https://wowdokan.com";


    public static final String API_VER          = "/wc-api/v3/";
    public static final String BASIC_AUTH = "Basic " + Base64.encodeToString((CONSUMER_KEY + ":" + CONSUMER_SECRET).getBytes(), Base64.NO_WRAP);
}
