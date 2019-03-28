package com.fit.ah.twitter.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.Profile.model.CountriesVM;
import com.fit.ah.twitter.Trends.TrendData;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.volley.MyVolley;

public class MiscApi {

    public static void CountriesLoad(final Context context, final MyRunnable<CountriesVM[]> onSuccess) {

        final String url = MyApi.hostIP + "/api/Countries/GetAll";

        MyVolley.get(url, CountriesVM[].class, new Response.Listener<CountriesVM[]>() {
            @Override
            public void onResponse(CountriesVM[] response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void TrendsLoad(final Context context, final MyRunnable<TrendData[]> onSuccess) {

        final String url = MyApi.hostIP + "/api/Tweets/Trends";

        MyVolley.get(url, TrendData[].class, new Response.Listener<TrendData[]>() {
            @Override
            public void onResponse(TrendData[] response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void MediaLoad(final Context context, final MyRunnable<String[]> onSuccess, int userID) {

        final String url = MyApi.hostIP + "/api/Tweets/Media/"+userID;

        MyVolley.get(url, String[].class, new Response.Listener<String[]>() {
            @Override
            public void onResponse(String[] response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
