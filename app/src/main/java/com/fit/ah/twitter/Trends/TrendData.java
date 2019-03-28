package com.fit.ah.twitter.Trends;

import android.media.Image;

/**
 * Created by AH on 05-Sep-18.
 */

public class TrendData {

    String word;
    int count;

    public TrendData(String tweetWord, int tweetCount) {
        this.word = tweetWord;
        this.count = tweetCount;
    }

    public String getTweetWord() {
        return word;
    }

    public String getTweetCount() {
        return String.valueOf(count);
    }
}
