package com.fit.ah.twitter.Trends;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fit.ah.twitter.R;

public class TrendsAdapter extends ArrayAdapter<TrendData> {

    TrendData[] trendDataArrayList;
    Context myContext;
    TextView txtTweetWord, txtTweetCount, txtCounter;

    public TrendsAdapter(TrendData[] data, Context context) {
        super(context, R.layout.trend_item, data);
        this.trendDataArrayList = data;
        this.myContext = context;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return trendDataArrayList.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public TrendData getItem(int position) {
        return trendDataArrayList[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TrendData data = getItem(position);

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.trend_item, parent, false);
        }

        txtCounter = convertView.findViewById(R.id.counter);
        txtTweetCount = convertView.findViewById(R.id.tweetCount);
        txtTweetWord = convertView.findViewById(R.id.tweetWord);

        txtCounter.setText(String.valueOf(position+1));
        if(data.getTweetCount().equals("1"))
            txtTweetCount.setText(data.getTweetCount() + " Tweet");
        else
            txtTweetCount.setText(data.getTweetCount() + " Tweets");
        txtTweetWord.setText(data.getTweetWord());

        return convertView;
    }


}
