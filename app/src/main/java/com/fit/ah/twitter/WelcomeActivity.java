package com.fit.ah.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fit.ah.twitter.Dashboard.DashboardActivity;
import com.fit.ah.twitter.Login.LoginActivity;
import com.fit.ah.twitter.Login.model.AuthProvjeraVM;
import com.fit.ah.twitter.Profile.model.UsersInfoVM;
import com.fit.ah.twitter.Registration.RegistrationActivity;
import com.fit.ah.twitter.api.AuthApi;
import com.fit.ah.twitter.api.MyApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;
import com.fit.ah.twitter.helper.volley.MyVolley;

public class WelcomeActivity extends AppCompatActivity {

    Button btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
        String sessionUsername = preferences.getString(getString(R.string.session_username), "");

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), LoginActivity.class));
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), RegistrationActivity.class));
            }
        });

        if(!sessionUsername.equals("")){
            MySession.authToken = MyApi.SetAuth();
            AuthApi.LoginSession(this, sessionUsername, new MyRunnable<AuthProvjeraVM>() {
                @Override
                public void run(AuthProvjeraVM result) {
                    if(result != null) {
                        MySession.logiraniKorisnik = result;
                        startActivity(new Intent(MyApp.getContext(), DashboardActivity.class));
                        finish();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}
