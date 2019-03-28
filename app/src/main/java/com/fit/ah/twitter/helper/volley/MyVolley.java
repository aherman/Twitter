package com.fit.ah.twitter.helper.volley;

import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.Response;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyGson;
import com.fit.ah.twitter.helper.MySession;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyVolley {


    public static <T> void get(String urlStr, Class<T> responseType, Response.Listener<T> listener, Response.ErrorListener errorListener, BasicNameValuePair... inputParams){
        String urlParam = URLEncodedUtils.format(Arrays.asList(inputParams), "utf-8");
        String url = urlStr + "?" + urlParam;
        GsonRequest<T> gsonRequest = new GsonRequest<>(url, responseType, MySession.authToken, null, listener, errorListener, Request.Method.GET);
        MySingleton.getInstance(MyApp.getContext()).addToRequestQueue(gsonRequest);
    }

    public static <T> void post(String urlStr, Class<T> responseType, Response.Listener<T> listener, Response.ErrorListener errorListener, Object postObject) {
        String jsonStr = MyGson.build().toJson(postObject);
        GsonRequest<T> gsonRequest = new GsonRequest<>(urlStr, responseType, MySession.authToken, jsonStr, listener, errorListener, Request.Method.POST);
        MySingleton.getInstance(MyApp.getContext()).addToRequestQueue(gsonRequest);
    }

    public static <T> void put(String urlStr, Class<T> responseType, Response.Listener<T> listener, Response.ErrorListener errorListener, Object putObject) {
        String jsonStr = MyGson.build().toJson(putObject);
        GsonRequest<T> gsonRequest = new GsonRequest<>(urlStr, responseType, MySession.authToken, jsonStr, listener, errorListener, Request.Method.PUT);
        MySingleton.getInstance(MyApp.getContext()).addToRequestQueue(gsonRequest);
    }

    public static <T> void delete(String urlStr, Class<T> responseType, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        GsonRequest<T> gsonRequest = new GsonRequest<>(urlStr, responseType, MySession.authToken, null, listener, errorListener, Request.Method.DELETE);
        MySingleton.getInstance(MyApp.getContext()).addToRequestQueue(gsonRequest);
    }

    public static <T> void patch(String urlStr, Class<T> responseType, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        GsonRequest<T> gsonRequest = new GsonRequest<>(urlStr, responseType, MySession.authToken, null, listener, errorListener, Request.Method.PATCH);
        MySingleton.getInstance(MyApp.getContext()).addToRequestQueue(gsonRequest);
    }
}
