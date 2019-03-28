package com.fit.ah.twitter.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.url_connection.HttpManager;
import com.fit.ah.twitter.helper.volley.MyVolley;
import com.fit.ah.twitter.Login.model.AuthProvjeraVM;

import org.apache.http.message.BasicNameValuePair;

public class AuthApi {

    public static void AuthCheck(final Context context, final String username, final String password, final MyRunnable<AuthProvjeraVM> onSuccess) {

        final String url = MyApi.hostIP + "/api/Users/Authentication";

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Twitter","Authenticating...");
        progressDialog.show();

        MyVolley.get(url, AuthProvjeraVM.class, new Response.Listener<AuthProvjeraVM>() {
            @Override
            public void onResponse(AuthProvjeraVM response) {
                progressDialog.dismiss();
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
            }
        }, new BasicNameValuePair("username",username),
           new BasicNameValuePair("password",password));
    }

    public static void LoginSession(final Context context, final String username, final MyRunnable<AuthProvjeraVM> onSuccess) {

        final String url = MyApi.hostIP + "/api/Users/AuthSession/"+username;

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Twitter","Processing...");
        progressDialog.show();

        MyVolley.get(url, AuthProvjeraVM.class, new Response.Listener<AuthProvjeraVM>() {
                    @Override
                    public void onResponse(AuthProvjeraVM response) {
                        onSuccess.run(response);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void AuthRegistration(final Context context, final String username, final MyRunnable<AuthProvjeraVM> onSuccess) {

        final String url = MyApi.hostIP + "/api/Users/AuthRegister";

        MyVolley.get(url, AuthProvjeraVM.class, new Response.Listener<AuthProvjeraVM>() {
                    @Override
                    public void onResponse(AuthProvjeraVM response) {
                        onSuccess.run(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new BasicNameValuePair("username",username));
    }
}
