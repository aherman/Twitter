package com.fit.ah.twitter.api;

import android.util.Base64;

import java.util.HashMap;
import java.util.Map;

public class MyApi {

    public static String hostIP = "http://api.rks1638.app.fit.ba";

    public static Map<String, String> SetAuth() {
        Map<String, String> headers = new HashMap<>();
        String cerendials = "auth:pass";
        final String basicAuth = "Basic " + Base64.encodeToString(cerendials.getBytes(), Base64.NO_WRAP);
        headers.put("Authorization", basicAuth);
        return headers;
    }
}
