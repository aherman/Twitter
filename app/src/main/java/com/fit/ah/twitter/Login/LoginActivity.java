package com.fit.ah.twitter.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fit.ah.twitter.Dashboard.DashboardActivity;
import com.fit.ah.twitter.WelcomeActivity;
import com.fit.ah.twitter.api.MyApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.api.AuthApi;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;
import com.fit.ah.twitter.Login.model.AuthProvjeraVM;
import com.fit.ah.twitter.helper.volley.MyVolley;

public class LoginActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText loginUsername, loginPassword;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.nav_actionbar);
        toolbar.setTitle("Log in");
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApp.getContext(), WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin_click();
            }
        });

    }

    private void btnLogin_click() {

        AuthApi.AuthCheck(this, loginUsername.getText().toString(), loginPassword.getText().toString(), new MyRunnable<AuthProvjeraVM>(){
            @Override
            public void run(AuthProvjeraVM result) {
                if(result != null) {
                    MySession.logiraniKorisnik = result;
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(getString(R.string.session_username), loginUsername.getText().toString());
                    editor.commit();
                    MySession.authToken = MyApi.SetAuth();
                    startActivity(new Intent(MyApp.getContext(), DashboardActivity.class));
                    finish();
                }
                else
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_color));

        return true;
    }
}
