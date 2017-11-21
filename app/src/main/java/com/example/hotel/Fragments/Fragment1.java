package com.example.hotel.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotel.Adapter.CityAdapter;
import com.example.hotel.Adapter.HotelAdapter;
import com.example.hotel.R;
import com.example.hotel.data.HotCity;
import com.example.hotel.data.Hotel;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐页面
 */

public class Fragment1 extends Fragment {

    private List<HotCity> cityList = new ArrayList<>();
    private List<Hotel> selectionList = new ArrayList<>();
    private View view;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home1, container, false);
        initList();
        initViews();
        return view;
    }

    private void initViews(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        CityAdapter cityAdapter = new CityAdapter(cityList);
        HotelAdapter hotelAdapter = new HotelAdapter(selectionList);
        recyclerView.setAdapter(cityAdapter);
        recyclerView2.setAdapter(hotelAdapter);

    }


    private void initList() {
        HotCity hangzhou = new HotCity("杭州", R.drawable.hangzhou);
        cityList.add(hangzhou);
        HotCity chongqing = new HotCity("重庆", R.drawable.chongqing);
        cityList.add(chongqing);
        HotCity shanghai = new HotCity("上海", R.drawable.shanghai);
        cityList.add(shanghai);
        HotCity ningbo = new HotCity("宁波", R.drawable.ningbo);
        cityList.add(ningbo);

        Hotel hotel1 = new Hotel("1号房间", R.drawable.hotel1);
        selectionList.add(hotel1);
        Hotel hotel2 = new Hotel("2号房间", R.drawable.hotel2);
        selectionList.add(hotel2);
        Hotel hotel3 = new Hotel("3号房间", R.drawable.hotel3);
        selectionList.add(hotel3);
        Hotel hotel4 = new Hotel("4号房间", R.drawable.hotel4);
        selectionList.add(hotel4);
    }

}
