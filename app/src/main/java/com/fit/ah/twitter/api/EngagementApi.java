package com.fit.ah.twitter.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fit.ah.twitter.Dashboard.model.UserTweetsVM;
import com.fit.ah.twitter.Engagements.EngagementData;
import com.fit.ah.twitter.Engagements.EngagementsFragment;
import com.fit.ah.twitter.Mentions.MentionsFragment;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.volley.MyVolley;

public class EngagementApi {

    public static void UserEngagementsLoad(final Context context, final MyRunnable<EngagementData[]> onSuccess,
                                           int userID) {

        final String url = MyApi.hostIP + "/api/Engagements/"+userID;

        MyVolley.get(url, EngagementData[].class, new Response.Listener<EngagementData[]>() {
            @Override
            public void onResponse(EngagementData[] response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void UserMentionsLoad(final Context context, final MyRunnable<UserTweetsVM[]> onSuccess,
                                        int userID) {

        final String url = MyApi.hostIP + "/api/Mentions/"+userID;

        MyVolley.get(url, UserTweetsVM[].class, new Response.Listener<UserTweetsVM[]>() {
            @Override
            public void onResponse(UserTweetsVM[] response) {
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
