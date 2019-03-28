package com.fit.ah.twitter.Messages;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.fit.ah.twitter.People.PeopleAdapter;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.Search.model.SearchUsersVM;
import com.fit.ah.twitter.api.UsersApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;

public class NewMessageDialog extends Dialog {

    Activity c;
    ListView lista;
    SearchView searchView;
    PeopleAdapter adapter;

    public NewMessageDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_newmessage);
        lista = findViewById(R.id.usersList);
        searchView = findViewById(R.id.receiverName);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                UsersApi.SearchUsers(MyApp.getContext(), new MyRunnable<SearchUsersVM[]>() {
                    @Override
                    public void run(SearchUsersVM[] result) {
                        adapter = new PeopleAdapter(result, MyApp.getContext(), true, NewMessageDialog.this);
                        lista.setAdapter(adapter);
                    }
                }, searchView.getQuery().toString(), MySession.logiraniKorisnik.userID);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


}