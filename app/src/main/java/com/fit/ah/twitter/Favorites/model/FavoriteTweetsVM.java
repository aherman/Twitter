package com.fit.ah.twitter.Favorites.model;

public class FavoriteTweetsVM {
    int UserID;
    int TweetID;

    public int getUserID() {
        return UserID;
    }

    public int getTweetID() {
        return TweetID;
    }

    public FavoriteTweetsVM(int userID, int tweetID) {
        UserID = userID;
        TweetID = tweetID;
    }
}
