package com.fit.ah.twitter.Trends;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.api.MiscApi;
import com.fit.ah.twitter.helper.MyRunnable;

import java.util.ArrayList;


public class TrendsFragment extends Fragment {

    private static TrendsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trends_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final ListView lista = view.findViewById(R.id.trends_list);

        MiscApi.TrendsLoad(MyApp.getContext(), new MyRunnable<TrendData[]>() {
            @Override
            public void run(TrendData[] result) {
                if(result != null){
                    adapter = new TrendsAdapter(result, getActivity().getApplicationContext());
                    lista.setAdapter(adapter);
                }
            }
        });





    }
}
