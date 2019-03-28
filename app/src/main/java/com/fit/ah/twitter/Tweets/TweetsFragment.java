package com.fit.ah.twitter.Tweets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit.ah.twitter.Dashboard.model.UserTweetsVM;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.api.TweetApi;
import com.fit.ah.twitter.helper.MyRunnable;

public class TweetsFragment extends Fragment {

    private static TweetsAdapter adapter;
    UserTweetsVM[] dataModel;
    int userID;
    RecyclerView lista;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tweets_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        lista = view.findViewById(R.id.tweet_list);
        LoadData(lista);
    }

    @Override
    public void onResume() {
        LoadData(lista);
        super.onResume();
    }

    private void LoadData(final RecyclerView lista) {
        Boolean search = getArguments().getBoolean("search");

        if(search){
            String word = getArguments().getString("word");
            TweetApi.SearchTweets(getContext(), new MyRunnable<UserTweetsVM[]>() {
                @Override
                public void run(UserTweetsVM[] result) {
                    dataModel = result;
                    adapter = new TweetsAdapter(dataModel, getActivity().getApplicationContext());
                    lista.setAdapter(adapter);
                    lista.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
                }
            }, word);
        }
        else{
            String tip = getArguments().getString("type");
            userID = getArguments().getInt("userID");
            if(tip.contains("tweets")) {
                TweetApi.UserTweetsLoad(MyApp.getContext(), new MyRunnable<UserTweetsVM[]>() {
                    @Override
                    public void run(UserTweetsVM[] result) {
                        if (result != null) {
                            dataModel = result;
                            adapter = new TweetsAdapter(dataModel, getActivity().getApplicationContext());
                            lista.setAdapter(adapter);
                            lista.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
                        }
                    }
                }, userID);
            }
            else
                TweetApi.UserLikedTweetsLoad(MyApp.getContext(), new MyRunnable<UserTweetsVM[]>() {
                    @Override
                    public void run(UserTweetsVM[] result) {
                        if(result != null){
                            dataModel = result;
                            adapter = new TweetsAdapter(dataModel, getActivity().getApplicationContext());
                            lista.setAdapter(adapter);
                        }
                    }
                }, userID);
        }
    }
}
