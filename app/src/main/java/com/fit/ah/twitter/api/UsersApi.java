package com.fit.ah.twitter.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fit.ah.twitter.Dashboard.model.UserTweetsVM;
import com.fit.ah.twitter.Settings.model.SettingsVM;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.Profile.model.UsersEditVM;
import com.fit.ah.twitter.Profile.model.UsersInfoVM;
import com.fit.ah.twitter.Registration.model.RegistrationVM;
import com.fit.ah.twitter.ResponseVM;
import com.fit.ah.twitter.Search.model.SearchUsersVM;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.volley.MyVolley;

public class UsersApi {

    public static void ProfileLoad(final Context context, final MyRunnable<UsersInfoVM> onSuccess, int userID) {

        final String url = MyApi.hostIP + "/api/Users/"+userID;

        MyVolley.get(url, UsersInfoVM.class, new Response.Listener<UsersInfoVM>() {
            @Override
            public void onResponse(UsersInfoVM response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(), "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void GetProfileImg(final Context context, final MyRunnable<String> onSuccess, int userID) {

        final String url = MyApi.hostIP + "/api/Users/ProfileImg/"+userID;

        MyVolley.get(url, String.class, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(), "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void ProfileLoadEditData(final Context context, final MyRunnable<UsersEditVM> onSuccess, int userID) {

        final String url = MyApi.hostIP + "/api/Users/GetEditData/"+userID;

        MyVolley.get(url, UsersEditVM.class, new Response.Listener<UsersEditVM>() {
            @Override
            public void onResponse(UsersEditVM response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(), "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void ProfileEdit(final Context context, final MyRunnable<UsersEditVM> onSuccess, UsersEditVM obj, int userID) {

        final String url = MyApi.hostIP + "/api/Users/"+userID;

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Twitter","Saving changes...");
        progressDialog.show();

        MyVolley.put(url, UsersEditVM.class, new Response.Listener<UsersEditVM>() {
            @Override
            public void onResponse(UsersEditVM response) {
                onSuccess.run(response);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, "Connection error "+error.getMessage() + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
            }
        }, obj);
    }

    public static void CheckUsername(final Context context, final MyRunnable<Boolean> onSuccess, String username) {

        final String url = MyApi.hostIP + "/api/Users/CheckUsername/"+username;

        MyVolley.get(url, Boolean.class, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage() + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void SearchUsers(final Context context, final MyRunnable<SearchUsersVM[]> onSuccess, String word, int userID) {

        final String url = MyApi.hostIP + "/api/Users/Search/"+userID+"/"+word;

        MyVolley.get(url, SearchUsersVM[].class, new Response.Listener<SearchUsersVM[]>() {
            @Override
            public void onResponse(SearchUsersVM[] response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage() + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void Register(final  Context context, final MyRunnable<RegistrationVM> onSuccess, RegistrationVM obj){

        final String url = MyApi.hostIP + "/api/Users";

        MyVolley.post(url, RegistrationVM.class, new Response.Listener<RegistrationVM>() {
            @Override
            public void onResponse(RegistrationVM response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage() + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
            }
        }, obj);
    }

    public static void Follow(final  Context context, final MyRunnable<ResponseVM> onSuccess, int followerID, int followingID){

        final String url = MyApi.hostIP + "/api/UsersFollowing/Follow/"+followerID+"/"+followingID;

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
        }, null);
    }

    public static void Unfollow(final  Context context, final MyRunnable<ResponseVM> onSuccess, int followerID, int followingID){

        final String url = MyApi.hostIP + "/api/UsersFollowing/Unfollow/"+followerID+"/"+followingID;

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

    public static void IsFollowing(final  Context context, final MyRunnable<Boolean> onSuccess, int followerID, int followingID) {

        final String url = MyApi.hostIP + "/api/UsersFollowing/IsFollowing/" + followerID + "/" + followingID;

        MyVolley.get(url, Boolean.class, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void SettingsLoad(final  Context context, final MyRunnable<SettingsVM> onSuccess, int userID){

        final String url = MyApi.hostIP + "/api/Users/Settings/"+userID;

        MyVolley.get(url, SettingsVM.class, new Response.Listener<SettingsVM>() {
            @Override
            public void onResponse(SettingsVM response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void SettingsPut(final  Context context, final MyRunnable<ResponseVM> onSuccess, int userID, SettingsVM obj){

        final String url = MyApi.hostIP + "/api/Users/ProfileUpdate/"+userID;

        MyVolley.put(url, ResponseVM.class, new Response.Listener<ResponseVM>() {
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

    public static void Deactivate(final  Context context, final MyRunnable<ResponseVM> onSuccess, int userID){

        final String url = MyApi.hostIP + "/api/Users/Deactivate/"+userID;

        MyVolley.patch(url, ResponseVM.class, new Response.Listener<ResponseVM>() {
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
}
