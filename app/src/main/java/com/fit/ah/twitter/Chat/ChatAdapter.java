package com.fit.ah.twitter.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fit.ah.twitter.R;
import com.fit.ah.twitter.helper.MyBitmapConverter;
import com.fit.ah.twitter.helper.MyDateFormatter;
import com.fit.ah.twitter.helper.MySession;

public class ChatAdapter extends ArrayAdapter<ChatData> {

    ChatData[] chatDataArray;
    Context myContext;

    public ChatAdapter(ChatData[] data, Context context) {
        super(context, R.layout.msg_receive, data);
        this.chatDataArray = data;
        this.myContext = context;
    }

    private static class ViewHolder{
        TextView txtTime;
        ImageView imgProfile;
        TextView txtContent;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if(chatDataArray[position].senderID == MySession.logiraniKorisnik.userID)
            return 1;
        else
            return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatData data = getItem(position);

        final ViewHolder viewHolder;

        int type = getItemViewType(position);

        if(convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());

            if(type == 1)
                convertView = inflater.inflate(R.layout.msg_sent, parent, false);
            else {
                convertView = inflater.inflate(R.layout.msg_receive, parent, false);
                viewHolder.imgProfile = convertView.findViewById(R.id.imgProfile);
                if(data.dmImg != null)
                    viewHolder.imgProfile.setImageBitmap(MyBitmapConverter.StringToBitmap(data.dmImg));
                else
                    viewHolder.imgProfile.setImageResource(R.mipmap.ic_launcher_round);
            }

            viewHolder.txtContent = convertView.findViewById(R.id.messageContent);
            viewHolder.txtTime = convertView.findViewById(R.id.messageTime);

            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtContent.setText(data.content);
        viewHolder.txtTime.setText(MyDateFormatter.FormatDate(data.dateTime));

        return convertView;
    }



}
