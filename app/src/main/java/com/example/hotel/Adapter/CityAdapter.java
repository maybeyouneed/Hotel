package com.example.hotel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hotel.R;
import com.example.hotel.data.HotCity;

import java.util.List;

/**
 * 热门城市列表的Adapter
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private Context mContext;
    private List<HotCity> mCityList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView cityImage;
        TextView cityName;

        public ViewHolder(View itemView) {
            super(itemView);
            cityImage = (ImageView) itemView.findViewById(R.id.city_image);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
        }
    }


    public CityAdapter(List<HotCity> cityList) {
        mCityList = cityList;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.hot_city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder holder, int position) {
        HotCity city = mCityList.get(position);
        holder.cityName.setText(city.getName());
        Glide.with(mContext).load(city.getImageId()).into(holder.cityImage);
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }
}
