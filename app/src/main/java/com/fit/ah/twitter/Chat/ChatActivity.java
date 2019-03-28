package com.fit.ah.twitter.Chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.fit.ah.twitter.Favorites.FavoritesActivity;
import com.fit.ah.twitter.Login.LoginActivity;
import com.fit.ah.twitter.Messages.MessagesActivity;
import com.fit.ah.twitter.Profile.ProfileActivity;
import com.fit.ah.twitter.Settings.SettingsActivity;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.ResponseVM;
import com.fit.ah.twitter.api.MessagesApi;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;

public class ChatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ChatData[] dataModel;
    Toolbar toolbar;
    ListView lista;
    static ChatAdapter adapter;
    String conversationID;
    String nickname;
    ImageView sendButton;
    EditText msgText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            conversationID = extras.getString("conversationID");
            nickname = extras.getString("nickname");
        }

        toolbar = findViewById(R.id.nav_actionbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setTitle(nickname);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lista = findViewById(R.id.lista);
        sendButton = findViewById(R.id.sendButton);
        msgText = findViewById(R.id.msgText);

        msgText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    SendData();
                    return true;
                }
                return false;
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendData();
            }
        });

        LoadData();
    }

    private void SendData() {
        String text = msgText.getText().toString();
        if(!"".equals(text)){
            ChatData obj = new ChatData();
            obj.senderID = MySession.logiraniKorisnik.userID;
            obj.conversationID = Integer.parseInt(conversationID);
            obj.content = text;
            MessagesApi.PostReply(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                @Override
                public void run(ResponseVM result) {
                    if (result.responseCode == 400){
                        Snackbar.make(findViewById(R.id.parentLayout), result.responseMessage, Snackbar.LENGTH_LONG).show();
                    }
                    else
                        LoadData();
                }
            }, obj);
            msgText.setText("");
        }
    }

    private void LoadData() {
        MessagesApi.ConversationMessagesLoad(this, conversationID, new MyRunnable<ChatData[]>() {
            @Override
            public void run(ChatData[] result) {
                if(result != null) {
                    dataModel = result;
                    adapter = new ChatAdapter(dataModel, getApplicationContext());
                    lista.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_profile: {
                Intent i = new Intent(MyApp.getContext(), ProfileActivity.class);
                i.putExtra("userID", MySession.logiraniKorisnik.userID);
                startActivity(i);
                break;
            }
            case R.id.nav_logout: {
                MySession.logiraniKorisnik = null;
                startActivity(new Intent(MyApp.getContext(), LoginActivity.class));
                finish();
                break;
            }
            case R.id.nav_settings: {
                startActivity(new Intent(MyApp.getContext(), SettingsActivity.class));
                break;
            }
            case R.id.nav_favorites: {
                startActivity(new Intent(MyApp.getContext(), FavoritesActivity.class));
                break;
            }
        }
        return true;
    }
}
