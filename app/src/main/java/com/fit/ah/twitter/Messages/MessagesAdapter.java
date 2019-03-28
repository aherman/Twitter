package com.fit.ah.twitter.Messages;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fit.ah.twitter.Chat.ChatActivity;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.helper.MyBitmapConverter;
import com.fit.ah.twitter.helper.MyDateFormatter;

public class MessagesAdapter extends ArrayAdapter<MessageData> {

    MessageData[] messageDataArray;
    Context myContext;

    public MessagesAdapter(MessageData[] data, Context context) {
        super(context, R.layout.message_item, data);
        this.messageDataArray = data;
        this.myContext = context;
    }

    private static class ViewHolder {
        TextView txtUsername;
        TextView txtNickname;
        TextView txtTime;
        ImageView imgProfile;
        TextView txtContent;
        TextView conversationID;
    }

    @Override
    public int getCount() {
        return messageDataArray.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public MessageData getItem(int position) {
        return messageDataArray[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MessageData data = getItem(position);

        final ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.message_item, parent, false);

            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imgProfile = convertView.findViewById(R.id.imgProfile);
        viewHolder.txtContent = convertView.findViewById(R.id.messageContent);
        viewHolder.txtNickname = convertView.findViewById(R.id.nickname);
        viewHolder.txtUsername = convertView.findViewById(R.id.username);
        viewHolder.txtTime = convertView.findViewById(R.id.messageTime);
        viewHolder.conversationID = convertView.findViewById(R.id.conversationID);

        viewHolder.txtNickname.setText(data.nickname);
        viewHolder.txtContent.setText(data.content);
        viewHolder.txtTime.setText(MyDateFormatter.FormatDate(data.time));
        viewHolder.conversationID.setText(data.conversationID.toString());
        if (data.imgProfile != null)
            viewHolder.imgProfile.setImageBitmap(MyBitmapConverter.StringToBitmap(data.imgProfile));
        else
            viewHolder.imgProfile.setImageResource(R.mipmap.ic_launcher_round);

        if(MyDateFormatter.IsLongDate(data.time)){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.txtUsername.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, viewHolder.txtNickname.getId());
            viewHolder.txtUsername.setLayoutParams(params);
            viewHolder.txtUsername.setText("@"+data.username);
        }
        else{
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.txtUsername.getLayoutParams();
            params.addRule(RelativeLayout.RIGHT_OF, viewHolder.txtNickname.getId());
            viewHolder.txtUsername.setLayoutParams(params);
            viewHolder.txtUsername.setText(" @"+data.username);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyApp.getContext(), ChatActivity.class);
                i.putExtra("conversationID",data.conversationID.toString());
                i.putExtra("nickname",data.nickname);
                MyApp.getContext().startActivity(i);
            }
        });

        return convertView;
    }



}
