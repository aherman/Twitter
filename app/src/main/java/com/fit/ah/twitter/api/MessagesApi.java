package com.fit.ah.twitter.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fit.ah.twitter.Chat.ChatData;
import com.fit.ah.twitter.Messages.MessageData;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.ResponseVM;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;
import com.fit.ah.twitter.helper.volley.MyVolley;

import org.apache.http.message.BasicNameValuePair;

public class MessagesApi {

    public static void MessagesPreviewLoad(final Context context, final MyRunnable<MessageData[]> onSuccess, int userID) {

        final String url = MyApi.hostIP + "/api/DirectMessages/Preview/"+userID;

        MyVolley.get(url, MessageData[].class, new Response.Listener<MessageData[]>() {
            @Override
            public void onResponse(MessageData[] response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void CheckConversation(final Context context, final MyRunnable<Integer> onSuccess, int userID1, int userID2) {

        final String url = MyApi.hostIP + "/api/DirectMessages/CheckConversation/"+userID1+"/"+userID2;

        MyVolley.get(url, Integer.class, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void NewConversation(final Context context, final MyRunnable<Integer> onSuccess, int userID1, int userID2) {

        final String url = MyApi.hostIP + "/api/DirectMessages/Conversation/"+userID1+"/"+userID2;

        MyVolley.post(url, Integer.class, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, null);
    }

    public static void ConversationMessagesLoad(final Context context, final String conversationID, final MyRunnable<ChatData[]> onSuccess) {

        final String url = MyApi.hostIP + "/api/DirectMessages";

        MyVolley.get(url, ChatData[].class, new Response.Listener<ChatData[]>() {
                    @Override
                    public void onResponse(ChatData[] response) {
                        onSuccess.run(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
                    }
                }, new BasicNameValuePair("conversationID",conversationID),
                new BasicNameValuePair("currentUser", String.valueOf(MySession.logiraniKorisnik.userID)));
    }

    public static void PostReply(final Context context, final MyRunnable<ResponseVM> onSuccess, ChatData obj) {

        final String url = MyApi.hostIP + "/api/DirectMessages";

        MyVolley.post(url, ResponseVM.class, new Response.Listener<ResponseVM>() {
            @Override
            public void onResponse(ResponseVM response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, obj);
    }

}
