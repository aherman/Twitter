package com.fit.ah.twitter.People;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fit.ah.twitter.Profile.ProfileActivity;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.Search.model.SearchUsersVM;
import com.fit.ah.twitter.api.UsersApi;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;


public class PeopleFragment extends Fragment {

    private static PeopleAdapter adapter;
    String word;
    ListView lista;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.people_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        lista = view.findViewById(R.id.people_list);

        LoadData(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchUsersVM obj = adapter.getItem(position);
                Intent i = new Intent(MyApp.getContext(), ProfileActivity.class);
                i.putExtra("userID", obj.userID);
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume() {
        LoadData(lista);
        super.onResume();
    }

    private void LoadData(final ListView lista) {
        word = getArguments().getString("word");

        UsersApi.SearchUsers(MyApp.getContext(), new MyRunnable<SearchUsersVM[]>() {
            @Override
            public void run(SearchUsersVM[] result) {
                adapter = new PeopleAdapter(result, getActivity().getApplicationContext(), false, null);
                lista.setAdapter(adapter);
            }
        }, word, MySession.logiraniKorisnik.userID);
    }
}
