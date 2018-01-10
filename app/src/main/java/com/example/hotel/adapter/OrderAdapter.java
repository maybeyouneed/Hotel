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

import com.bumptech.glide.Glide;
import com.example.hotel.R;
import com.example.hotel.data.Order;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order>{

    private int resourceId;
    private Context mContext;

    public OrderAdapter(Context context,  int textViewResourceId, List<Order> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        Order order = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.orderImage = (ImageView) view.findViewById(R.id.order_image);
            viewHolder.orderName = (TextView) view.findViewById(R.id.order_name);
            viewHolder.time = (TextView) view.findViewById(R.id.order_time);
            viewHolder.hotelInfo = (TextView) view.findViewById(R.id.order_info);
            viewHolder.price = (TextView) view.findViewById(R.id.order_price);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(mContext).load(order.getImageUrl()).into(viewHolder.orderImage);
        viewHolder.orderName.setText(order.getHotelName());
        viewHolder.time.setText(order.getTime());
        viewHolder.hotelInfo.setText(order.getInfo());
        viewHolder.price.setText(order.getPrice());
        return view;
    }

    private class ViewHolder{
        ImageView orderImage;
        TextView orderName;
        TextView time;
        TextView hotelInfo;
        TextView price;
    }
}
