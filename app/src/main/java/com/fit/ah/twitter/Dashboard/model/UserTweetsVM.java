package com.fit.ah.twitter.Dashboard.model;

import java.util.Date;

public class UserTweetsVM {
    private int userID;
    private int tweetID;
    private String username;
    private String nickname;
    private String content;
    private String imgTweet;

    public String getImgTweet() { return imgTweet; }

    public int getTweetID(){
        return tweetID;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() { return username; }

    public String getNickname(){ return nickname; }

    public String getContent() {
        return content;
    }

    public Date getDatetime() {
        return datetime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public String getParentTweetUsername() { return parentTweetUsername; }

    public String getReplyUsername() { return replyUsername; }

    private Date datetime;
    public int likeCount;
    public int retweetCount;
    private int replyCount;
    private String profileImg;
    private String parentTweetUsername;
    private String replyUsername;
    private int replyUserID;

    public int getReplyUserID() { return replyUserID; }

    public boolean IsMentionData() {
        if(replyUsername == null)
            return false;
        return true;
    }
}