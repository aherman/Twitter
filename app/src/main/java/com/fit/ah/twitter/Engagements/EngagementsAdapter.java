package com.fit.ah.twitter.Engagements;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fit.ah.twitter.R;
import com.fit.ah.twitter.helper.MyBitmapConverter;
import com.fit.ah.twitter.helper.MyDateFormatter;

import java.util.ArrayList;

/**
 * Created by AH on 05-Sep-18.
 */

public class EngagementsAdapter extends ArrayAdapter<EngagementData> {

    ArrayList<EngagementData> tweetDataArrayList;
    Context myContext;

    public EngagementsAdapter(ArrayList<EngagementData> data, Context context) {
        super(context, R.layout.engagement_item, data);
        this.tweetDataArrayList = data;
        this.myContext = context;
    }

    private static class ViewHolder{
        TextView txtNickname;
        ImageView imgEngagementType;
        ImageView imgProfile;
        TextView tweetContent;
        TextView tweetTime;
        TextView txtEngagementType;
    }

    @Override
    public int getCount() {
        return tweetDataArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public EngagementData getItem(int position) {
        return tweetDataArrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        EngagementData data = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.engagement_item, parent, false);

            viewHolder.txtNickname = convertView.findViewById(R.id.nickname);
            viewHolder.imgEngagementType = convertView.findViewById(R.id.engagementType);
            viewHolder.imgProfile = convertView.findViewById(R.id.imgProfile);
            viewHolder.tweetContent = convertView.findViewById(R.id.tweetContent);
            viewHolder.tweetTime = convertView.findViewById(R.id.engagementTime);
            viewHolder.txtEngagementType = convertView.findViewById(R.id.engagementTypeText);

            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtNickname.setText(data.getNickname());
        viewHolder.imgEngagementType.setImageResource(data.getEngagementType());
        if (data.getImgProfile() != null)
            viewHolder.imgProfile.setImageBitmap(MyBitmapConverter.StringToBitmap(data.getImgProfile()));
        else
            viewHolder.imgProfile.setImageResource(R.mipmap.ic_launcher_round);
        viewHolder.tweetContent.setText(data.getTweetContent());
        viewHolder.tweetTime.setText(MyDateFormatter.FormatDate(data.getTime()));
        if(data.getEngagementType() == R.drawable.like_liked)
            viewHolder.txtEngagementType.setText("liked your Tweet.");
        else
            viewHolder.txtEngagementType.setText("retweeted your Tweet.");

        return convertView;
    }
}
