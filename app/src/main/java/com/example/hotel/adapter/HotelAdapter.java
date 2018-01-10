package com.example.hotel.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hotel.R;
import com.example.hotel.activities.RoomInfoActivity;
import com.example.hotel.data.Hotel;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店列表的Adapter
 */

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    private Context mContext;
    private List<Hotel> mHotelList = new ArrayList<>();

    public HotelAdapter(List<Hotel> hotelList) {
        mHotelList = hotelList;
    }

    static class  ViewHolder extends RecyclerView.ViewHolder{

        ImageView hotelImage;
        TextView hotelName;

        public ViewHolder(View itemView) {
            super(itemView);
            hotelImage = (ImageView) itemView.findViewById(R.id.chosen_image);
            hotelName = (TextView) itemView.findViewById(R.id.chosen_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.care_chosen_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Hotel hotel = mHotelList.get(position);
        holder.hotelName.setText(String.format(mContext.getResources().getString(R.string.hotel_name), hotel.getHotelName(), hotel.getNumber()) );
        Glide.with(mContext).load(hotel.getImageUrl()).into(holder.hotelImage);

        //点击事件
        holder.hotelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RoomInfoActivity.class);
                intent.putExtra("hotelName",hotel.getHotelName());
                intent.putExtra("hotelNumber",hotel.getNumber());
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mHotelList.size();
    }
}
