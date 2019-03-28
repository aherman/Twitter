package com.fit.ah.twitter.Engagements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fit.ah.twitter.R;
import com.fit.ah.twitter.Tweets.TweetPreviewActivity;
import com.fit.ah.twitter.api.EngagementApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by AH on 06-Sep-18.
 */

public class EngagementsFragment extends Fragment {

    private static EngagementsAdapter adapter;
    ArrayList<EngagementData> dataModel;
    ListView lista;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.engagements_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        lista = view.findViewById(R.id.engagements_list);

        LoadData(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EngagementData item = adapter.getItem(position);
                Intent i = new Intent(MyApp.getContext(), TweetPreviewActivity.class);
                i.putExtra("tweetID", item.tweetID);
                MyApp.getContext().startActivity(i);
            }
        });
    }

    private void LoadData(final ListView lista) {
        EngagementApi.UserEngagementsLoad(MyApp.getContext(), new MyRunnable<EngagementData[]>() {
            @Override
            public void run(EngagementData[] result) {
                if(result != null) {
                    dataModel = new ArrayList<>(Arrays.asList(result));
                    adapter = new EngagementsAdapter(dataModel, getActivity().getApplicationContext());
                    lista.setAdapter(adapter);
                }
            }
        }, MySession.logiraniKorisnik.userID);
    }

    @Override
    public void onResume() {
        LoadData(lista);
        super.onResume();
    }
}
