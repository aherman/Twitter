package com.fit.ah.twitter.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fit.ah.twitter.Dashboard.model.TweetEngagementVM;
import com.fit.ah.twitter.Favorites.model.FavoriteTweetsVM;
import com.fit.ah.twitter.Tweets.model.PostTweetVM;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.PostReply;
import com.fit.ah.twitter.ResponseVM;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.Dashboard.model.UserTweetsVM;
import com.fit.ah.twitter.helper.volley.MyVolley;

import org.apache.http.message.BasicNameValuePair;

public class TweetApi {


    public static void DashboardTweetsLoad(final Context context, final MyRunnable<UserTweetsVM[]> onSuccess, int userID) {

        final String url = MyApi.hostIP + "/api/Tweets/"+userID;

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Twitter","Loading Tweets...");
        progressDialog.show();

        MyVolley.get(url, UserTweetsVM[].class, new Response.Listener<UserTweetsVM[]>() {
                    @Override
                    public void onResponse(UserTweetsVM[] response) {
                        progressDialog.dismiss();
                        onSuccess.run(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void SearchTweets(final Context context, final MyRunnable<UserTweetsVM[]> onSuccess, String word) {

        final String url = MyApi.hostIP + "/api/Tweets/Search/"+word;

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Twitter","Searching...");
        progressDialog.show();

        MyVolley.get(url, UserTweetsVM[].class, new Response.Listener<UserTweetsVM[]>() {
            @Override
            public void onResponse(UserTweetsVM[] response) {
                progressDialog.dismiss();
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void UserTweetsLoad(final Context context, final MyRunnable<UserTweetsVM[]> onSuccess, int userID) {

        final String url = MyApi.hostIP + "/api/Tweets/User/"+userID;

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

    public static void UserLikedTweetsLoad(final Context context, final MyRunnable<UserTweetsVM[]> onSuccess, int userID) {

        final String url = MyApi.hostIP + "/api/Tweets/UserLiked/"+userID;

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

    public static void UserTweetLike(final Context context, final MyRunnable<TweetEngagementVM> onSuccess, int userID, int tweetID) {

        final String url = MyApi.hostIP + "/api/Favorites?tweetID="+tweetID+"&userID="+userID;

        MyVolley.post(url, TweetEngagementVM.class, new Response.Listener<TweetEngagementVM>() {
            @Override
            public void onResponse(TweetEngagementVM response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, null);
    }

    public static void PostTweet(final Context context, final MyRunnable<ResponseVM> onSuccess, PostTweetVM obj) {

        final String url = MyApi.hostIP + "/api/Tweets";

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Twitter","Posting Tweet...");
        progressDialog.show();

        MyVolley.post(url, ResponseVM.class, new Response.Listener<ResponseVM>() {
            @Override
            public void onResponse(ResponseVM response) {
                onSuccess.run(response);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, "Connection error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, obj);
    }

    public static void PostReply(final Context context, final MyRunnable<ResponseVM> onSuccess, PostReply obj) {

        final String url = MyApi.hostIP + "/api/Tweets/Reply";

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Twitter","Posting Reply...");
        progressDialog.show();

        MyVolley.post(url, ResponseVM.class, new Response.Listener<ResponseVM>() {
            @Override
            public void onResponse(ResponseVM response) {
                onSuccess.run(response);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, "Connection error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, obj);
    }

    public static void UserTweetRetweet(final Context context, final MyRunnable<TweetEngagementVM> onSuccess, int userID, int tweetID) {

        final String url = MyApi.hostIP + "/api/Retweets?tweetID="+tweetID+"&userID="+userID;

        MyVolley.post(url, TweetEngagementVM.class, new Response.Listener<TweetEngagementVM>() {
            @Override
            public void onResponse(TweetEngagementVM response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, null);
    }


    public static void TweetLiked(final Context context, final MyRunnable<TweetEngagementVM> onSuccess, final int userID, final int tweetID) {

        final String url = MyApi.hostIP + "/api/Favorites/GetUserFavorite";

        MyVolley.get(url, TweetEngagementVM.class, new Response.Listener<TweetEngagementVM>() {
            @Override
            public void onResponse(TweetEngagementVM response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new BasicNameValuePair("tweetID", Integer.toString(tweetID)),
                new BasicNameValuePair("userID", Integer.toString(userID)));
    }

    public static void TweetRetweeted(final Context context, final MyRunnable<TweetEngagementVM> onSuccess, final int userID, final int tweetID) {

        final String url = MyApi.hostIP + "/api/Retweets/GetUserRetweet";

        MyVolley.get(url, TweetEngagementVM.class, new Response.Listener<TweetEngagementVM>() {
                    @Override
                    public void onResponse(TweetEngagementVM response) {
                        onSuccess.run(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new BasicNameValuePair("tweetID", Integer.toString(tweetID)),
                new BasicNameValuePair("userID", Integer.toString(userID)));
    }

    public static void FavoriteTweetsLoad(final Context context, final MyRunnable<UserTweetsVM[]> onSuccess, int userID) {

        final String url = MyApi.hostIP + "/api/FavoriteTweets/"+userID;

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

    public static void FavoriteTweetsPost(final Context context, final MyRunnable<ResponseVM> onSuccess, FavoriteTweetsVM obj) {

        final String url = MyApi.hostIP + "/api/FavoriteTweets";

        MyVolley.post(url, ResponseVM.class, new Response.Listener<ResponseVM>() {
            @Override
            public void onResponse(ResponseVM response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, obj);
    }

    public static void FavoriteTweetsCheck(final Context context, final MyRunnable<ResponseVM> onSuccess, int tweetID, int userID) {

        final String url = MyApi.hostIP + "/api/FavoriteTweets/Check/"+userID+"/"+tweetID;

        MyVolley.get(url, ResponseVM.class, new Response.Listener<ResponseVM>() {
            @Override
            public void onResponse(ResponseVM response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void DeleteTweet(final Context context, final MyRunnable<ResponseVM> onSuccess, int tweetID) {

        final String url = MyApi.hostIP + "/api/Tweets/"+tweetID;

        MyVolley.delete(url, ResponseVM.class, new Response.Listener<ResponseVM>() {
            @Override
            public void onResponse(ResponseVM response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void GetTweet(final  Context context, final MyRunnable<UserTweetsVM> onSuccess, int tweetID){

        final String url = MyApi.hostIP + "/api/Tweets/Tweet/"+tweetID;

        MyVolley.get(url, UserTweetsVM.class, new Response.Listener<UserTweetsVM>() {
            @Override
            public void onResponse(UserTweetsVM response) {
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



