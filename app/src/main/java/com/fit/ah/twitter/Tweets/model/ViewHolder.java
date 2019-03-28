package com.fit.ah.twitter.Tweets.model;

import android.app.MediaRouteButton;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fit.ah.twitter.R;

import org.w3c.dom.Text;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView txtUsername;
    public TextView txtNickname;
    public TextView txtLikeCount;
    public TextView txtRetweetCount;
    public TextView txtReplyCount;
    public ImageView imgProfile;
    public TextView tweetContent;
    public ImageView tweetImage;
    public ImageView imgLike;
    public ImageView imgRetweet;
    public ImageView iconArrow;
    public ImageView imgReply;
    public ImageView miscImage;
    public TextView tweetTime;
    public TextView parentTweetUsername;
    public TextView miscText;
    public LinearLayout mentionPart;
    public TextView mentionUsername;
    public LinearLayout dashboardReplyPart;
    public TextView txtParentTweetUsername;
    public LinearLayout tweetParent;

    public ViewHolder(@NonNull View convertView) {
        super(convertView);
        this.tweetParent = convertView.findViewById(R.id.tweetParent);
        this.txtUsername = convertView.findViewById(R.id.username);
        this.txtNickname = convertView.findViewById(R.id.nickname);
        this.txtLikeCount = convertView.findViewById(R.id.likeCount);
        this.txtRetweetCount = convertView.findViewById(R.id.retweetCount);
        this.tweetContent = convertView.findViewById(R.id.tweetContent);
        this.imgLike = convertView.findViewById(R.id.imgLike);
        this.imgRetweet = convertView.findViewById(R.id.imgRetweet);
        this.iconArrow = convertView.findViewById(R.id.iconArrow);
        this.imgProfile = convertView.findViewById(R.id.imgProfile);
        this.imgReply = convertView.findViewById(R.id.imgReply);
        this.tweetTime = convertView.findViewById(R.id.tweetTime);
        this.txtReplyCount = convertView.findViewById(R.id.replyCount);
        this.parentTweetUsername = convertView.findViewById(R.id.txtParentTweetUsername);
        this.miscText = convertView.findViewById(R.id.dRPtxt1);
        this.mentionPart = convertView.findViewById(R.id.mentionPart);
        this.mentionUsername = convertView.findViewById(R.id.mentionUsername);
        this.tweetImage = convertView.findViewById(R.id.tweetImage);
        this.miscImage = convertView.findViewById(R.id.dRPimage1);
        this.dashboardReplyPart = convertView.findViewById(R.id.dashboardReplyPart);
        this.txtParentTweetUsername = convertView.findViewById(R.id.txtParentTweetUsername);
    }


}
