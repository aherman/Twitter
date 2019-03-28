package com.fit.ah.twitter.People;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fit.ah.twitter.Chat.ChatActivity;
import com.fit.ah.twitter.Messages.NewMessageDialog;
import com.fit.ah.twitter.api.MessagesApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.ResponseVM;
import com.fit.ah.twitter.Search.model.SearchUsersVM;
import com.fit.ah.twitter.api.UsersApi;
import com.fit.ah.twitter.helper.MyBitmapConverter;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;

public class PeopleAdapter extends ArrayAdapter<SearchUsersVM> {

    SearchUsersVM[] messageDataArrayList;
    Context myContext;
    boolean isSearchFlag;
    NewMessageDialog newMessageDialog;

    public PeopleAdapter(SearchUsersVM[] data, Context context, boolean isSearch, NewMessageDialog newMessageDialog) {
        super(context, R.layout.people_item, data);
        this.messageDataArrayList = data;
        this.myContext = context;
        isSearchFlag = isSearch;
        this.newMessageDialog = newMessageDialog;
    }

    private static class ViewHolder{
        TextView txtUsername;
        TextView txtNickname;
        ImageView imgProfile;
        TextView txtBio;
        Button btnFollow;
    }

    @Override
    public int getCount() {
        return messageDataArrayList.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public SearchUsersVM getItem(int position) {
        return messageDataArrayList[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final SearchUsersVM data = getItem(position);

        final ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.people_item, parent, false);
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtBio = convertView.findViewById(R.id.bio);
        viewHolder.txtNickname = convertView.findViewById(R.id.nickname);
        viewHolder.txtUsername = convertView.findViewById(R.id.username);
        viewHolder.imgProfile = convertView.findViewById(R.id.imgProfile);
        viewHolder.btnFollow = convertView.findViewById(R.id.btnFollow);

        viewHolder.txtUsername.setText("@"+data.username);
        viewHolder.txtNickname.setText(data.nickname);

        if (data.profileImg != null)
            viewHolder.imgProfile.setImageBitmap(MyBitmapConverter.StringToBitmap(data.profileImg));
        else
            viewHolder.imgProfile.setImageResource(R.mipmap.ic_launcher_round);

        if(data.following){
            viewHolder.btnFollow.setBackground(convertView.getResources().getDrawable(R.drawable.rounded_button));
            viewHolder.btnFollow.setText("Following");
            viewHolder.btnFollow.setTextColor(convertView.getResources().getColor(R.color.text_color_primary));
        }

        viewHolder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(data.following)
                    UsersApi.Unfollow(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                        @Override
                        public void run(ResponseVM result) {
                            Button button = (Button) v;
                            button.setBackground(v.getResources().getDrawable(R.drawable.transparent_button));
                            button.setText("Follow");
                            button.setTextColor(v.getResources().getColor(R.color.twitterblue_color));
                            data.following = false;
                        }
                    }, MySession.logiraniKorisnik.userID, data.userID);
                else
                    UsersApi.Follow(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                        @Override
                        public void run(ResponseVM result) {
                            Button button = (Button) v;
                            button.setBackground(v.getResources().getDrawable(R.drawable.rounded_button));
                            button.setText("Following");
                            button.setTextColor(v.getResources().getColor(R.color.text_color_primary));
                            data.following = true;
                        }
                    }, MySession.logiraniKorisnik.userID, data.userID);
            }
        });

        if(isSearchFlag){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessagesApi.CheckConversation(MyApp.getContext(), new MyRunnable<Integer>() {
                        @Override
                        public void run(Integer result) {
                            if(result == 0){
                                MessagesApi.NewConversation(MyApp.getContext(), new MyRunnable<Integer>() {
                                    @Override
                                    public void run(Integer result) {
                                        if(result != 0){
                                            Intent i = new Intent(MyApp.getContext(), ChatActivity.class);
                                            i.putExtra("conversationID",String.valueOf(result));
                                            i.putExtra("nickname",data.nickname);
                                            MyApp.getContext().startActivity(i);
                                            newMessageDialog.cancel();
                                        }
                                    }
                                }, data.userID, MySession.logiraniKorisnik.userID);
                            } else{
                                Intent i = new Intent(MyApp.getContext(), ChatActivity.class);
                                i.putExtra("conversationID",String.valueOf(result));
                                i.putExtra("nickname",data.nickname);
                                MyApp.getContext().startActivity(i);
                                newMessageDialog.cancel();
                            }
                        }
                    }, data.userID, MySession.logiraniKorisnik.userID);
                }
            });
        }

        return convertView;
    }
}
