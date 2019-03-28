package com.fit.ah.twitter.Engagements;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.fit.ah.twitter.R;

import java.util.Date;

/**
 * Created by AH on 05-Sep-18.
 */

public class EngagementData {
    int tweetID;
    Date time;
    String nickname;
    String tweetContent;
    String imgProfile;
    String type;

    public EngagementData(int tweetID, Date time, String nickname, String tweetContent, String imgProfile, String type) {
        this.tweetID = tweetID;
        this.time = time;
        this.nickname = nickname;
        this.tweetContent = tweetContent;
        this.imgProfile = imgProfile;
        this.type = this.type;
    }

    public Date getTime() {
        return time;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTweetContent() {
        return tweetContent;
    }

    public String getImgProfile() {
        return imgProfile;
    }

    public int getEngagementType() {
        if(type.contains("Favorite"))
            return R.drawable.like_liked;
        return R.drawable.retweet_icon;
    }


}
