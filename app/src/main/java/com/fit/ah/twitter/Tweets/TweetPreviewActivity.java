package com.fit.ah.twitter.Tweets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fit.ah.twitter.Dashboard.model.TweetEngagementVM;
import com.fit.ah.twitter.Dashboard.model.UserTweetsVM;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.api.TweetApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyBitmapConverter;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;

public class TweetPreviewActivity extends AppCompatActivity {
    ImageView imgLike, imgRetweet, imgReply, imgProfile, imgTweet;
    TextView username, nickname, time, likeCount, retweetCount, replyCount, tweetContent, parentTweetUsername, miscText;
    UserTweetsVM data;
    int tweetID;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_preview);

        toolbar = findViewById(R.id.nav_actionbar);
        toolbar.setTitle("Tweet preview");
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tweetID = getIntent().getIntExtra("tweetID", 0);

        username = findViewById(R.id.username);
        nickname = findViewById(R.id.nickname);
        likeCount = findViewById(R.id.likeCount);
        retweetCount = findViewById(R.id.retweetCount);
        tweetContent = findViewById(R.id.tweetContent);
        imgLike = findViewById(R.id.imgLike);
        imgRetweet = findViewById(R.id.imgRetweet);
        imgProfile = findViewById(R.id.imgProfile);
        imgReply = findViewById(R.id.imgReply);
        time = findViewById(R.id.tweetTime);
        replyCount = findViewById(R.id.replyCount);
        parentTweetUsername = findViewById(R.id.txtParentTweetUsername);
        miscText = findViewById(R.id.dRPtxt1);
        imgTweet = findViewById(R.id.imgTweet);

        TweetApi.GetTweet(this, new MyRunnable<UserTweetsVM>() {
            @Override
            public void run(UserTweetsVM result) {
                data = result;
                username.setText("@"+data.getUsername());
                nickname.setText(data.getNickname());
                tweetContent.setText(data.getContent());
                likeCount.setText(Integer.toString(data.getLikeCount()));
                retweetCount.setText(Integer.toString(data.getRetweetCount()));
                time.setText(data.getDatetime().toString());
                replyCount.setText(Integer.toString(data.getReplyCount()));

                if (data.getProfileImg() != null)
                    imgProfile.setImageBitmap(MyBitmapConverter.StringToBitmap(data.getProfileImg()));
                else
                    imgProfile.setImageResource(R.mipmap.ic_launcher_round);

                if (data.getImgTweet() != null){
                    imgTweet.setImageBitmap(MyBitmapConverter.StringToBitmap(result.getImgTweet()));
                    imgTweet.setVisibility(View.VISIBLE);
                }

                if(data.getParentTweetUsername() != null){
                    findViewById(R.id.dashboardReplyPart).setVisibility(View.VISIBLE);
                    miscText.setVisibility(View.VISIBLE);
                    findViewById(R.id.txtParentTweetUsername).setVisibility(View.VISIBLE);

                    if(data.getUserID() != MySession.logiraniKorisnik.userID && data.getParentTweetUsername().equals("1")){
                        miscText.setText("Retweeted");
                        parentTweetUsername.setText("");
                    }
                    else
                        parentTweetUsername.setText("@"+data.getParentTweetUsername());
                }

                if(data.getUserID() == MySession.logiraniKorisnik.userID) {
                    imgRetweet.setImageResource(R.drawable.retweet_disabled);
                    imgRetweet.setEnabled(false);
                }

                TweetApi.TweetLiked(MyApp.getContext(), new MyRunnable<TweetEngagementVM>() {
                    @Override
                    public void run(TweetEngagementVM result) {
                        if(result.engaged) {
                            imgLike.setImageResource(R.drawable.like_liked);
                        }
                        else {
                            imgLike.setImageResource(R.drawable.like);
                        }
                    }
                }, MySession.logiraniKorisnik.userID, data.getTweetID());

                TweetApi.TweetRetweeted(MyApp.getContext(), new MyRunnable<TweetEngagementVM>() {
                    @Override
                    public void run(TweetEngagementVM result) {
                        if(result.engaged){
                            imgRetweet.setImageResource(R.drawable.retweet_retweeted);
                        }
                        else {
                            imgRetweet.setImageResource(R.drawable.retweet_icon);
                        }
                    }
                }, MySession.logiraniKorisnik.userID, data.getTweetID());

                imgLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TweetApi.UserTweetLike(MyApp.getContext(), new MyRunnable<TweetEngagementVM>() {
                            @Override
                            public void run(TweetEngagementVM result) {
                                if(result.engaged){
                                    data.likeCount++;
                                    imgLike.setImageResource(R.drawable.like_liked);
                                }
                                else{
                                    data.likeCount--;
                                    imgLike.setImageResource(R.drawable.like);
                                }

                                likeCount.setText(String.valueOf(data.getLikeCount()));
                            }
                        }, MySession.logiraniKorisnik.userID, data.getTweetID());
                    }
                });

                imgRetweet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TweetApi.UserTweetRetweet(MyApp.getContext(), new MyRunnable<TweetEngagementVM>() {
                            @Override
                            public void run(TweetEngagementVM result) {
                                if(result.engaged){
                                    data.retweetCount++;
                                    imgRetweet.setImageResource(R.drawable.retweet_retweeted);
                                }
                                else {
                                    data.retweetCount--;
                                    imgRetweet.setImageResource(R.drawable.retweet_icon);
                                }

                                retweetCount.setText(String.valueOf(data.getRetweetCount()));
                            }
                        }, MySession.logiraniKorisnik.userID, data.getTweetID());
                    }
                });

                imgReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MyApp.getContext(), ReplyActivity.class);
                        i.putExtra("username", data.getUsername());
                        i.putExtra("tweetID", Integer.toString(data.getTweetID()));
                        MyApp.getContext().startActivity(i);
                        finish();
                    }
                });
            }
        }, tweetID);

    }

}
