package com.fit.ah.twitter.Tweets.model;

import android.media.Image;

public class TweetData {

    String username;
    String nickname;
    int likeCount;
    int retweetCount;
    String tweetContent;
    Image imgProfile;

    public TweetData(String username, String nickname, int likeCount, int retweetCount, String tweetContent) {
        this.username = username;
        this.nickname = nickname;
        this.likeCount = likeCount;
        this.retweetCount = retweetCount;
        this.tweetContent = tweetContent;
    }

    public String getUsername() {
        return username;
    }

    public String getTweetContent() {
        return tweetContent;
    }

    public Image getImgProfile() {
        return imgProfile;
    }

    public String getNickname() {
        return nickname;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }
}
