package com.fit.ah.twitter;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fit.ah.twitter.Dashboard.DashboardActivity;
import com.fit.ah.twitter.Messages.MessageData;
import com.fit.ah.twitter.Messages.MessagesActivity;
import com.fit.ah.twitter.Messages.MessagesAdapter;
import com.fit.ah.twitter.Search.SearchActivity;
import com.fit.ah.twitter.helper.MyApp;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavbar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        MenuItem searchMenuItem = bottomNavigationView.getMenu().findItem(R.id.search);
        MenuItem homeMenuItem = bottomNavigationView.getMenu().findItem(R.id.home);
        MenuItem notificationsMenuItem = bottomNavigationView.getMenu().findItem(R.id.notifications);
        MenuItem dmMenuItem = bottomNavigationView.getMenu().findItem(R.id.directmessages);

        searchMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MyApp.getContext(), ExploreActivity.class));
                finish();
                return true;
            }
        });

        homeMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MyApp.getContext(), DashboardActivity.class));
                finish();
                return true;
            }
        });

        dmMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MyApp.getContext(), MessagesActivity.class));
                finish();
                return true;
            }
        });

        notificationsMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MyApp.getContext(), NotificationsActivity.class));
                finish();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search tweets or people");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Intent i = new Intent(MyApp.getContext(), SearchActivity.class);
                i.putExtra("word", query);
                startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        getSupportActionBar().setTitle(R.string.explore_title);
        getSupportActionBar().setBackgroundDrawable(getTheme().getDrawable(R.color.actionbar_color));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_color));


        return super.onCreateOptionsMenu(menu);
    }
}
