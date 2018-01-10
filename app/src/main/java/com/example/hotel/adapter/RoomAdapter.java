package com.example.hotel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotel.R;
import com.example.hotel.data.Room;

import java.util.List;


public class RoomAdapter extends ArrayAdapter<Room> {

    private int resourceId;

    public RoomAdapter(Context context,  int textViewResourceId, List<Room> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Room room = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.roomImage = (ImageView) view.findViewById(R.id.room_img);
            viewHolder.roomName = (TextView) view.findViewById(R.id.room_name);
            viewHolder.roomInfo = (TextView) view.findViewById(R.id.room_info);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.roomImage.setImageResource(room.getImageId());
        viewHolder.roomName.setText(room.getName());
        viewHolder.roomInfo.setText(room.getInfo());
        return view;
    }

    private class ViewHolder {
        ImageView roomImage;
        TextView roomName;
        TextView roomInfo;
    }
}
