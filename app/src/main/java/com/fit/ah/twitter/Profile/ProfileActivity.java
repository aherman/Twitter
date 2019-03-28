package com.fit.ah.twitter.Profile;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fit.ah.twitter.Media.MediaFragment;
import com.fit.ah.twitter.ResponseVM;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.Profile.model.UsersInfoVM;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.SectionsPageAdapter;
import com.fit.ah.twitter.Tweets.TweetsFragment;
import com.fit.ah.twitter.api.UsersApi;
import com.fit.ah.twitter.helper.MyBitmapConverter;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    SectionsPageAdapter sectionsPageAdapter;
    ViewPager viewPager;
    TextView txtUsername, txtNickname, txtLocation, txtBirthdate, txtFollowing, txtFollowers, txtFollowIndicator;
    CircleImageView imgProfile;
    ImageView imgHeader;
    Button btnProfile;
    int userID;
    boolean following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userID = getIntent().getIntExtra("userID", 0);

        txtNickname = findViewById(R.id.nickname);
        txtUsername = findViewById(R.id.username);
        txtBirthdate = findViewById(R.id.birthdate);
        txtLocation = findViewById(R.id.location);
        txtFollowers = findViewById(R.id.followersCount);
        txtFollowing = findViewById(R.id.followingCount);
        txtFollowIndicator = findViewById(R.id.followIndicatior);
        btnProfile = findViewById(R.id.btnEdit);
        imgProfile = findViewById(R.id.imgProfile);
        imgHeader = findViewById(R.id.imgHeader);

        if(userID == MySession.logiraniKorisnik.userID){
            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MyApp.getContext(), EditProfileActivity.class));
                }
            });
            btnProfile.setBackground(getResources().getDrawable(R.drawable.transparent_button_gray));
            btnProfile.setText("Edit profile");
            btnProfile.setTextColor(getResources().getColor(R.color.my_gray));
        }
        else{
            UsersApi.IsFollowing(MyApp.getContext(), new MyRunnable<Boolean>() {
                @Override
                public void run(Boolean result) {
                    if(result)
                        txtFollowIndicator.setVisibility(View.VISIBLE);
                }
            }, userID, MySession.logiraniKorisnik.userID);

            UsersApi.IsFollowing(MyApp.getContext(), new MyRunnable<Boolean>() {
                @Override
                public void run(Boolean result) {
                    if(result){
                        following = true;
                        btnProfile.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                        btnProfile.setText("Following");
                        btnProfile.setTextColor(getResources().getColor(R.color.text_color_primary));
                    }
                    else{
                        following = false;
                        btnProfile.setBackground(getResources().getDrawable(R.drawable.transparent_button));
                        btnProfile.setText("Follow");
                        btnProfile.setTextColor(getResources().getColor(R.color.twitterblue_color));
                    }
                    btnProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(following){
                                UsersApi.Unfollow(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                                    @Override
                                    public void run(ResponseVM result) {
                                        btnProfile.setBackground(getResources().getDrawable(R.drawable.transparent_button));
                                        btnProfile.setText("Follow");
                                        btnProfile.setTextColor(getResources().getColor(R.color.twitterblue_color));
                                        following = false;
                                    }
                                }, MySession.logiraniKorisnik.userID, userID);
                            }
                            else{
                                UsersApi.Follow(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                                    @Override
                                    public void run(ResponseVM result) {
                                        btnProfile.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                                        btnProfile.setText("Following");
                                        btnProfile.setTextColor(getResources().getColor(R.color.text_color_primary));
                                        following = true;
                                    }
                                }, MySession.logiraniKorisnik.userID, userID);
                            }
                        }
                    });
                }
            }, MySession.logiraniKorisnik.userID, userID);
        }

        LoadData();

         sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
         viewPager = findViewById(R.id.container);
         setupViewPager(viewPager);

         TabLayout tabLayout = findViewById(R.id.tabs);
         tabLayout.setupWithViewPager(viewPager);

         tabLayout.setTabTextColors(getResources()
                 .getColor(R.color.text_color_secondary), getResources().getColor(R.color.text_color_primary));

         viewPager.setFocusable(false);
    }

    private void LoadData() {

        UsersApi.ProfileLoad(MyApp.getContext(), new MyRunnable<UsersInfoVM>() {
            @Override
            public void run(UsersInfoVM result) {
                txtNickname.setText(result.nickname);
                txtUsername.setText("@"+result.username);
                txtBirthdate.setText("Born on "+result.birthDate);
                txtFollowers.setText(Integer.toString(result.followersCount));
                txtFollowing.setText(Integer.toString(result.followingCount));
                txtLocation.setText("Lives in "+result.location);
                if(result.profileImg != null)
                    imgProfile.setImageBitmap(MyBitmapConverter.StringToBitmap(result.profileImg));
                else
                    imgProfile.setImageResource(R.mipmap.ic_launcher_round);
                if(result.headerImg != null)
                    imgHeader.setImageBitmap(MyBitmapConverter.StringToBitmap(result.headerImg));
                else
                    imgHeader.setImageResource(R.drawable.sample_header);
            }
        }, userID);

    }


    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        Bundle tweetBundle = new Bundle();
        tweetBundle.putString("type", "tweets");
        tweetBundle.putInt("userID", userID);
        TweetsFragment tweetsFragment1 = new TweetsFragment();
        tweetsFragment1.setArguments(tweetBundle);
        adapter.addFragment(tweetsFragment1, "Tweets");

        Bundle mediaBundle = new Bundle();
        mediaBundle.putInt("userID", userID);
        MediaFragment mediaFragment = new MediaFragment();
        mediaFragment.setArguments(mediaBundle);
        adapter.addFragment((mediaFragment), "Media");

        Bundle tweetBundle2 = new Bundle();
        TweetsFragment tweetsFragment2 = new TweetsFragment();
        tweetBundle2.putString("type","likes");
        tweetBundle2.putInt("userID", userID);
        tweetsFragment2.setArguments(tweetBundle2);
        adapter.addFragment(tweetsFragment2 , "Likes");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        getSupportActionBar().setTitle(R.string.profile_title);
        getSupportActionBar().setBackgroundDrawable(getTheme().getDrawable(R.color.actionbar_color));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_color));
        getSupportActionBar().hide();
        return true;
    }

    @Override
    protected void onResume() {
        LoadData();

        super.onResume();
    }
}
