package com.fit.ah.twitter.Favorites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.fit.ah.twitter.Tweets.TweetsAdapter;
import com.fit.ah.twitter.Dashboard.model.UserTweetsVM;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.api.TweetApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;

public class FavoritesActivity extends AppCompatActivity {

    private static TweetsAdapter adapter;
    UserTweetsVM[] dataModel;
    Toolbar toolbar;
    RecyclerView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        toolbar = findViewById(R.id.nav_actionbar);
        toolbar.setTitle("Favorites");
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lista = findViewById(R.id.favorites_list);

        TweetApi.FavoriteTweetsLoad(this, new MyRunnable<UserTweetsVM[]>() {
            @Override
            public void run(UserTweetsVM[] result) {
                if(result != null) {
                    dataModel = result;
                    adapter = new TweetsAdapter(dataModel, getApplicationContext());
                    lista.setAdapter(adapter);
                    lista.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
                }
            }
        }, MySession.logiraniKorisnik.userID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_color));

        return true;
    }
}
