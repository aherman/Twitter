package com.fit.ah.twitter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fit.ah.twitter.Dashboard.DashboardActivity;
import com.fit.ah.twitter.Engagements.EngagementsFragment;
import com.fit.ah.twitter.Favorites.FavoritesActivity;
import com.fit.ah.twitter.Login.LoginActivity;
import com.fit.ah.twitter.Mentions.MentionsFragment;
import com.fit.ah.twitter.Messages.MessagesActivity;
import com.fit.ah.twitter.Profile.ProfileActivity;
import com.fit.ah.twitter.Profile.model.UsersInfoVM;
import com.fit.ah.twitter.Settings.SettingsActivity;
import com.fit.ah.twitter.api.UsersApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;

public class NotificationsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    TextView username, nickname, followers, following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        navigationView = findViewById(R.id.nav_navview);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar = findViewById(R.id.nav_actionbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher_round);
        toolbar.setTitle("Notifications");
        setSupportActionBar(toolbar);

        View headerView = navigationView.getHeaderView(0);

        username = headerView.findViewById(R.id.headerUsername);
        nickname = headerView.findViewById(R.id.headerNickname);
        followers = headerView.findViewById(R.id.headerFollowersCount);
        following = headerView.findViewById(R.id.headerFollowingCount);

        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabTextColors(getResources()
                .getColor(R.color.text_color_secondary), getResources().getColor(R.color.text_color_primary));

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

        LoadNavDrawerData();
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new EngagementsFragment(), "Engagements");
        adapter.addFragment(new MentionsFragment(), "Mentions");

        viewPager.setAdapter(adapter);
    }

    private void LoadNavDrawerData(){
        UsersApi.ProfileLoad(MyApp.getContext(), new MyRunnable<UsersInfoVM>() {
            @Override
            public void run(UsersInfoVM result) {
                followers.setText(String.valueOf(result.followersCount));
                following.setText(String.valueOf(result.followingCount));
                username.setText("@"+result.username);
                nickname.setText(result.nickname);
            }
        }, MySession.logiraniKorisnik.userID);
    }

    @Override
    protected void onPostResume() {
        LoadNavDrawerData();
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_color));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile: {
                Intent i = new Intent(MyApp.getContext(), ProfileActivity.class);
                i.putExtra("userID", MySession.logiraniKorisnik.userID);
                startActivity(i);
                break;
            }
            case R.id.nav_logout: {
                MySession.logiraniKorisnik = null;
                MySession.authToken = null;
                startActivity(new Intent(MyApp.getContext(), LoginActivity.class));
                finish();
                break;
            }
            case R.id.nav_settings: {
                startActivity(new Intent(MyApp.getContext(), SettingsActivity.class));
                break;
            }
            case R.id.nav_favorites: {
                startActivity(new Intent(MyApp.getContext(), FavoritesActivity.class));
                break;
            }
        }
        return true;
    }
}
