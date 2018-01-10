package com.example.hotel.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.adapter.CityAdapter;
import com.example.hotel.adapter.HotelAdapter;
import com.example.hotel.data.Constant;
import com.example.hotel.data.HotCity;
import com.example.hotel.data.Hotel;
import com.example.hotel.gson.MessageBean;
import com.example.hotel.gson.RoomInfo;
import com.example.hotel.utils.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 推荐页面
 */

public class Fragment1 extends Fragment {

    private List<HotCity> cityList = new ArrayList<>();
    private List<Hotel> hotelList = new ArrayList<>();
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home1, container, false);
        return view;
    }

    //先查询SP中是否有缓存，如果有就加载缓存中数据，没有就请求
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences pref = getActivity().getSharedPreferences("RoomInfo", 0);
        String roomInfoCache = pref.getString("responseText", null);
        if (roomInfoCache != null){
            RoomInfo roomInfo = Utility.handleRoomInfoResponse(roomInfoCache);
            showRoomInfo(roomInfo);
        } else {
            requestRoomInfo();
        }
    }

    //从服务器请求房间信息，并将返回的数据缓存到SP
    private void requestRoomInfo(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.URL + "room")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Log.d("首页", res);
                final RoomInfo roomInfo = Utility.handleRoomInfoResponse(res);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (roomInfo != null && roomInfo.isResult()){
                            SharedPreferences pref = getActivity().getSharedPreferences("RoomInfo", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("responseText", res);
                            editor.apply();
                            showRoomInfo(roomInfo);
                        }else {
                            Toast.makeText(getActivity(), "获取房间信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络连接异常！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //显示首页的房间列表
    private void showRoomInfo(RoomInfo roomInfo){
        List<MessageBean> rooms = roomInfo.getMessage();
        for (MessageBean room : rooms) {
            String hotelName = room.getHotelName();
            String imageUrl = room.getImageUrl().getPic1();
            String roomNumber = room.getNumber();
            Hotel hotel = new Hotel(hotelName, Constant.URL_IMG + imageUrl, roomNumber);
            hotelList.add(hotel);
        }
        initViews();
    }

    private void initViews(){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view2);
        initList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        CityAdapter cityAdapter = new CityAdapter(cityList);
        HotelAdapter hotelAdapter = new HotelAdapter(hotelList);
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
        HotCity ningPo = new HotCity("宁波", R.drawable.ningbo);
        cityList.add(ningPo);

    }

}
