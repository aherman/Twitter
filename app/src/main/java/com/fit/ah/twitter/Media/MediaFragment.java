package com.fit.ah.twitter.Media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.fit.ah.twitter.R;
import com.fit.ah.twitter.api.MiscApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;


public class MediaFragment extends Fragment {
    ListView mediaList;
    MediaAdapter adapter;
    String[] dataModel;
    int userID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.media_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        userID = getArguments().getInt("userID");
        mediaList = getActivity().findViewById(R.id.mediaList);

        MiscApi.MediaLoad(MyApp.getContext(), new MyRunnable<String[]>() {
            @Override
            public void run(String[] result) {
                dataModel = result;
                adapter = new MediaAdapter(dataModel, getActivity().getApplicationContext());
                mediaList.setAdapter(adapter);
            }
        }, userID);

        super.onViewCreated(view, savedInstanceState);
    }
}