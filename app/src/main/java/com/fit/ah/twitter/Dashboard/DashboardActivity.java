package com.fit.ah.twitter.Dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fit.ah.twitter.BottomNavigationViewHelper;
import com.fit.ah.twitter.ExploreActivity;
import com.fit.ah.twitter.Favorites.FavoritesActivity;
import com.fit.ah.twitter.Login.LoginActivity;
import com.fit.ah.twitter.Messages.MessagesActivity;
import com.fit.ah.twitter.Settings.SettingsActivity;
import com.fit.ah.twitter.Tweets.TweetsAdapter;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.NotificationsActivity;
import com.fit.ah.twitter.Profile.ProfileActivity;
import com.fit.ah.twitter.Profile.model.UsersInfoVM;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.Tweets.TweetActivity;
import com.fit.ah.twitter.api.UsersApi;
import com.fit.ah.twitter.api.TweetApi;
import com.fit.ah.twitter.helper.MyBitmapConverter;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.Dashboard.model.UserTweetsVM;
import com.fit.ah.twitter.helper.MySession;

import de.hdodenhof.circleimageview.CircleImageView;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static TweetsAdapter adapter;
    static long back_pressed;
    UserTweetsVM[] dataModel;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    RecyclerView lista;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton floatingActionButton;
    NavigationView navigationView;
    TextView username, nickname, followers, following;
    CircleImageView headerImgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        navigationView = findViewById(R.id.nav_navview);
        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        floatingActionButton = findViewById(R.id.newtweet_button);
        toolbar = findViewById(R.id.nav_actionbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher_round);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        View headerView = navigationView.getHeaderView(0);

        username = headerView.findViewById(R.id.headerUsername);
        nickname = headerView.findViewById(R.id.headerNickname);
        followers = headerView.findViewById(R.id.headerFollowersCount);
        following = headerView.findViewById(R.id.headerFollowingCount);
        headerImgProfile = headerView.findViewById(R.id.headerImgProfile);

        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        lista = findViewById(R.id.tweet_list);

        GetTweetList();

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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTweetList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), TweetActivity.class));
            }
        });

        LoadNavDrawerData();
    }

    private void LoadNavDrawerData(){
        UsersApi.ProfileLoad(MyApp.getContext(), new MyRunnable<UsersInfoVM>() {
            @Override
            public void run(UsersInfoVM result) {
                followers.setText(String.valueOf(result.followersCount));
                following.setText(String.valueOf(result.followingCount));
                username.setText("@"+result.username);
                nickname.setText(result.nickname);
                if(result.profileImg != null)
                    headerImgProfile.setImageBitmap(MyBitmapConverter.StringToBitmap(result.profileImg));
                else
                    headerImgProfile.setImageResource(R.mipmap.ic_launcher_round);
            }
        }, MySession.logiraniKorisnik.userID);
    }

    private void GetTweetList() {
        TweetApi.DashboardTweetsLoad(this, new MyRunnable<UserTweetsVM[]>() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_color));
        return true;
    }

    @Override
    protected void onResume() {
        GetTweetList();
        LoadNavDrawerData();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
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
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.session_username), "");
                editor.commit();
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
