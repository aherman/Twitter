package com.fit.ah.twitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.fit.ah.twitter.Dashboard.DashboardActivity;
import com.fit.ah.twitter.Login.model.AuthProvjeraVM;
import com.fit.ah.twitter.People.PeopleAdapter;
import com.fit.ah.twitter.Search.model.SearchUsersVM;
import com.fit.ah.twitter.api.AuthApi;
import com.fit.ah.twitter.api.MyApi;
import com.fit.ah.twitter.api.UsersApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;
import com.fit.ah.twitter.helper.volley.MyVolley;

public class InterestsActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnNext;
    ListView lista;
    SearchView searchView;
    static PeopleAdapter adapter;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        btnNext = findViewById(R.id.btn_next);
        lista = findViewById(R.id.lista);
        searchView = findViewById(R.id.searchView);
        toolbar = findViewById(R.id.nav_actionbar);
        toolbar.setTitle("Interests");
        setSupportActionBar(toolbar);

        username = getIntent().getStringExtra("username");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MyApp.getContext(), DashboardActivity.class));
            }
        });

        AuthApi.AuthRegistration(MyApp.getContext(), username, new MyRunnable<AuthProvjeraVM>() {
            @Override
            public void run(AuthProvjeraVM result) {
                MySession.logiraniKorisnik = result;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.session_username), result.username);
                editor.commit();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                UsersApi.SearchUsers(MyApp.getContext(), new MyRunnable<SearchUsersVM[]>() {
                    @Override
                    public void run(SearchUsersVM[] result) {
                        adapter = new PeopleAdapter(result, MyApp.getContext(), false, null);
                        lista.setAdapter(adapter);
                    }
                }, searchView.getQuery().toString(), MySession.logiraniKorisnik.userID);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_color));

        return true;
    }
}
