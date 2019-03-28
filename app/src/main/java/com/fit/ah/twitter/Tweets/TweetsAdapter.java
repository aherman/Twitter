package com.fit.ah.twitter.Tweets;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.fit.ah.twitter.Tweets.model.ViewHolder;

import com.fit.ah.twitter.Dashboard.model.TweetEngagementVM;
import com.fit.ah.twitter.Dashboard.model.UserTweetsVM;
import com.fit.ah.twitter.Favorites.model.FavoriteTweetsVM;
import com.fit.ah.twitter.ProcessActivity;
import com.fit.ah.twitter.Profile.ProfileActivity;
import com.fit.ah.twitter.ResponseVM;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.api.TweetApi;
import com.fit.ah.twitter.helper.MyBitmapConverter;
import com.fit.ah.twitter.helper.MyDateFormatter;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;

public class TweetsAdapter extends RecyclerView.Adapter<ViewHolder> {

    UserTweetsVM[] tweetDataArrayList;
    Context myContext;

    public TweetsAdapter(UserTweetsVM[] data, Context context) {
        super();
        this.tweetDataArrayList = data;
        this.myContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.tweet_item, parent, false);
        return new ViewHolder(view);
    }

    public UserTweetsVM getItem(int position){
        return tweetDataArrayList[position];
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final UserTweetsVM data = tweetDataArrayList[position];

        viewHolder.tweetImage.setImageBitmap(null);

        viewHolder.tweetParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyApp.getContext(), TweetPreviewActivity.class);
                i.putExtra("tweetID", data.getTweetID());
                MyApp.getContext().startActivity(i);
            }
        });

        if(data.IsMentionData()){
            viewHolder.mentionPart.setVisibility(View.VISIBLE);
            viewHolder.mentionUsername.setText(" @"+data.getReplyUsername());
        }

        ClickEvents(data, viewHolder);

        TweetApi.TweetLiked(MyApp.getContext(), new MyRunnable<TweetEngagementVM>() {
            @Override
            public void run(TweetEngagementVM result) {
                if(result.engaged) {
                    viewHolder.imgLike.setImageResource(R.drawable.like_liked);
                }
                else {
                    viewHolder.imgLike.setImageResource(R.drawable.like);
                }
            }
        }, MySession.logiraniKorisnik.userID, data.getTweetID());

        TweetApi.TweetRetweeted(MyApp.getContext(), new MyRunnable<TweetEngagementVM>() {
            @Override
            public void run(TweetEngagementVM result) {
                if(result.engaged){
                    viewHolder.imgRetweet.setImageResource(R.drawable.retweet_retweeted);
                }
                else {
                    viewHolder.imgRetweet.setImageResource(R.drawable.retweet_icon);
                }
            }
        }, MySession.logiraniKorisnik.userID, data.getTweetID());

        viewHolder.txtUsername.setText(" @"+data.getUsername());
        viewHolder.txtNickname.setText(data.getNickname());
        viewHolder.tweetContent.setText(data.getContent());
        viewHolder.txtLikeCount.setText(Integer.toString(data.getLikeCount()));
        viewHolder.txtRetweetCount.setText(Integer.toString(data.getRetweetCount()));
        viewHolder.tweetTime.setText(MyDateFormatter.FormatDate(data.getDatetime()));
        viewHolder.txtReplyCount.setText(Integer.toString(data.getReplyCount()));
        if(data.getProfileImg() != null)
            viewHolder.imgProfile.setImageBitmap(MyBitmapConverter.StringToBitmap(data.getProfileImg()));
        else
            viewHolder.imgProfile.setImageResource(R.mipmap.ic_launcher_round);

        if(MyDateFormatter.IsLongDate(data.getDatetime())){
            viewHolder.txtUsername.setMaxWidth(180);
            viewHolder.txtNickname.setMaxWidth(300);
            viewHolder.tweetTime.setText(MyDateFormatter.FormatShortDate(data.getDatetime()));
        }
        else{
            viewHolder.tweetTime.setText(MyDateFormatter.FormatDate(data.getDatetime()));
            viewHolder.txtUsername.setMaxWidth(500);
            viewHolder.txtNickname.setMaxWidth(500);
        }

        if(data.getParentTweetUsername() != null){
            viewHolder.dashboardReplyPart.setVisibility(View.VISIBLE);
            viewHolder.miscText.setVisibility(View.VISIBLE);
            viewHolder.miscText.setText("Replying to");
            viewHolder.miscImage.setImageResource(R.drawable.reply_icon);
            viewHolder.txtParentTweetUsername.setVisibility(View.VISIBLE);

            if(data.getUserID() != MySession.logiraniKorisnik.userID && data.getParentTweetUsername().equals("1")){
                viewHolder.miscText.setText("Retweeted");
                viewHolder.miscImage.setImageResource(R.drawable.retweet_retweeted);
                viewHolder.parentTweetUsername.setText("");
            }
            else
                viewHolder.parentTweetUsername.setText("@"+data.getParentTweetUsername());
        }

        if(data.getUserID() == MySession.logiraniKorisnik.userID) {
            viewHolder.imgRetweet.setImageResource(R.drawable.retweet_disabled);
            viewHolder.imgRetweet.setEnabled(false);
        }

        if(data.getImgTweet() != null){
            viewHolder.tweetImage.setVisibility(View.VISIBLE);
            viewHolder.tweetImage.setImageBitmap(MyBitmapConverter.StringToBitmap(data.getImgTweet()));
        }

//        myOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(myContext, data.getContent(), Toast.LENGTH_SHORT).show();
//            }
//        };
    }

    @Override
    public int getItemCount() {
        return tweetDataArrayList.length;
    }

    private void ClickEvents(final UserTweetsVM data, final ViewHolder viewHolder) {

        viewHolder.mentionUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyApp.getContext(), ProfileActivity.class);
                i.putExtra("userID", data.getReplyUserID());
                myContext.startActivity(i);
            }
        });

        viewHolder.txtNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyApp.getContext(), ProfileActivity.class);
                i.putExtra("userID", data.getUserID());
                myContext.startActivity(i);
            }
        });
        viewHolder.txtUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyApp.getContext(), ProfileActivity.class);
                i.putExtra("userID", data.getUserID());
                myContext.startActivity(i);
            }
        });
        viewHolder.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(MyApp.getContext(), ProfileActivity.class);
               i.putExtra("userID", data.getUserID());
               myContext.startActivity(i);
            }
        });
        viewHolder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TweetApi.UserTweetLike(MyApp.getContext(), new MyRunnable<TweetEngagementVM>() {
                    @Override
                    public void run(TweetEngagementVM result) {
                        if(result.engaged){
                            data.likeCount++;
                            viewHolder.imgLike.setImageResource(R.drawable.like_liked);
                        }
                        else{
                            data.likeCount--;
                            viewHolder.imgLike.setImageResource(R.drawable.like);
                        }

                        viewHolder.txtLikeCount.setText(String.valueOf(data.getLikeCount()));
                    }
                }, MySession.logiraniKorisnik.userID, data.getTweetID());
            }
        });
        viewHolder.imgRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TweetApi.UserTweetRetweet(MyApp.getContext(), new MyRunnable<TweetEngagementVM>() {
                    @Override
                    public void run(TweetEngagementVM result) {
                        if(result.engaged){
                            data.retweetCount++;
                            viewHolder.imgRetweet.setImageResource(R.drawable.retweet_retweeted);
                        }
                        else {
                            data.retweetCount--;
                            viewHolder.imgRetweet.setImageResource(R.drawable.retweet_icon);
                        }

                        viewHolder.txtRetweetCount.setText(String.valueOf(data.getRetweetCount()));
                    }
                }, MySession.logiraniKorisnik.userID, data.getTweetID());
            }
        });
        viewHolder.iconArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MyApp.getContext(), v);

                popup.getMenu().add(1,1,1,"Add to favorites");
                if(data.getUserID() == MySession.logiraniKorisnik.userID)
                    popup.getMenu().add(1,2,2,"Delete tweet");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == 1){
                            TweetApi.FavoriteTweetsCheck(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                                @Override
                                public void run(ResponseVM result) {
                                    if(result.responseCode == 200)
                                        TweetApi.FavoriteTweetsPost(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                                            @Override
                                            public void run(ResponseVM result) {
                                                if(result.responseCode == 200)
                                                    Toast.makeText(MyApp.getContext(), "Tweet added to favorites.", Toast.LENGTH_SHORT).show();
                                                else if(result.responseCode == 400)
                                                    Toast.makeText(MyApp.getContext(), result.responseMessage, Toast.LENGTH_SHORT).show();
                                            }}, new FavoriteTweetsVM(MySession.logiraniKorisnik.userID, data.getTweetID()));
                                    else if(result.responseCode == 400)
                                        Toast.makeText(MyApp.getContext(), result.responseMessage, Toast.LENGTH_SHORT).show();
                                }
                            }, data.getTweetID(), MySession.logiraniKorisnik.userID);

                        }
                        if(item.getItemId() == 2){
                            TweetApi.DeleteTweet(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                                @Override
                                public void run(ResponseVM result) {
                                    if (result.responseCode == 200) {
                                        myContext.startActivity(new Intent(myContext, ProcessActivity.class));
                                    }
                                    Toast.makeText(MyApp.getContext(), result.responseMessage, Toast.LENGTH_SHORT).show();
                                }
                            }, data.getTweetID());
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        viewHolder.imgReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyApp.getContext(), ReplyActivity.class);
                i.putExtra("username", data.getUsername());
                i.putExtra("tweetID", Integer.toString(data.getTweetID()));
                myContext.startActivity(i);
            }
        });
    }
}
