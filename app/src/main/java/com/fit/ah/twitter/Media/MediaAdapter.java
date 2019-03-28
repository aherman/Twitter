package com.fit.ah.twitter.Media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.fit.ah.twitter.R;
import com.fit.ah.twitter.helper.MyBitmapConverter;

public class MediaAdapter extends ArrayAdapter<String> {
    Context myContext;
    String[] dataList;
    ImageView imageItem;

    public MediaAdapter(String[] data, Context context) {
        super(context, R.layout.media_item, data);
        dataList = data;
        myContext = context;
    }

    @Override
    public int getCount() {
        return dataList.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return dataList[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final String data = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.media_item, parent, false);

        imageItem = convertView.findViewById(R.id.image);
        imageItem.setImageBitmap(MyBitmapConverter.StringToBitmap(data));

        return convertView;
    }
}
