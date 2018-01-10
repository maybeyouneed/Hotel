package com.example.hotel.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.adapter.OrderAdapter;
import com.example.hotel.data.CommonResponse;
import com.example.hotel.data.Constant;
import com.example.hotel.data.Order;
import com.example.hotel.gson.MessageBean;
import com.example.hotel.gson.OrderInfo;
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


public class OrderFragment extends Fragment {

    private ListView orderList;
    private List<Order> ordersList = new ArrayList<>();
    private OrderAdapter adapter;
    private ProgressDialog dialog;
    private SwipeRefreshLayout order_refresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        orderList = (ListView) view.findViewById(R.id.order_list);
        order_refresh = (SwipeRefreshLayout) view.findViewById(R.id.order_refresh);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        final Boolean isLogin = pref.getBoolean("isLogin", false);
        final String key = pref.getString("key", null);
        Log.d("Order", "isLogin = " + isLogin);
        Log.d("Order", "key = " + key);
        if (isLogin && key != null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();
            orderList.setVisibility(View.VISIBLE);
            httpRequest(key);
        }
        if (!isLogin){
            orderList.setVisibility(View.GONE);
        }
        order_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLogin && key != null) {
                    dialog = new ProgressDialog(getActivity());
                    dialog.setCancelable(false);
                    dialog.setMessage("Loading...");
                    dialog.show();
                    orderList.setVisibility(View.VISIBLE);
                    httpRequest(key);
                    order_refresh.setRefreshing(false);
                }
                if (!isLogin){
                    Toast.makeText(getActivity(), "请先登录再查看订单", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Log.d("订单", "订单隐藏");
        } else {
            Log.d("订单", "订单显示");
            SharedPreferences pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
            Boolean isLogin = pref.getBoolean("isLogin", false);
            String key = pref.getString("key", null);
            Log.d("订单", "key = " + key);
            if (isLogin && key != null) {
                dialog = new ProgressDialog(getActivity());
                dialog.setCancelable(false);
                dialog.setMessage("Loading...");
                dialog.show();
                orderList.setVisibility(View.VISIBLE);
                httpRequest(key);
            }
            if (!isLogin){
                orderList.setVisibility(View.GONE);
            }
        }
    }

    private void httpRequest(String key){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.URL + "order")
                .header("key", key)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("订单", res);
                CommonResponse commonResponse = Utility.handleLoginResponse(res);
                if (commonResponse.getResult()) {
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("OrderInfo", 0).edit();
                    editor.putString("orderJSON", res);
                    editor.apply();
                    OrderInfo orderInfo = Utility.handleOrdersResponse(res);
                    ordersList.clear();
                    if (orderInfo != null && orderInfo.isResult() && !orderInfo.getMessage().isEmpty()) {
                        String roomCode;  //房间代码
                        SharedPreferences pref = getActivity().getSharedPreferences("RoomInfo", 0);
                        String roomInfoText = pref.getString("responseText", null);
                        RoomInfo roomInfo = Utility.handleRoomInfoResponse(roomInfoText);
                        List<MessageBean> rooms = roomInfo.getMessage();
                        List<OrderInfo.MessageBean> orders = orderInfo.getMessage();
                        for (OrderInfo.MessageBean order : orders) {
                            roomCode = order.getRoom();
                            String inTime = order.getIntime();
                            String outTime = order.getOuttime();
                            String cost = order.getCost();
                            for (MessageBean room : rooms) {
                                if (roomCode.equals(room.getId())) {
                                    String hotelName = room.getHotelName();
                                    String roomNumber = room.getNumber();
                                    String date = Utility.handleServerTime(inTime) + "-" + Utility.handleServerTime(outTime);
                                    String imageUrl = room.getImageUrl().getPic1();
                                    initList(hotelName, imageUrl, roomNumber, date, cost);
                                }
                            }
                        }
                        for (int i = 0; i < ordersList.size(); i++){
                            int index = ordersList.size() - i - 1;
                            Order temp = ordersList.get(index);
                            ordersList.set(index, ordersList.get(i));
                            ordersList.set(i, temp);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new OrderAdapter(getActivity(), R.layout.order_list_item, ordersList);
                                orderList.setAdapter(adapter);
                                dialog.dismiss();
                            }
                        });
                    } else if (orderInfo != null && !orderInfo.isResult()) {
                        Log.d("订单网络请求", "这里失败解析了");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "获取订单列表失败", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        });
                    }
                }else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "服务器问题，获取订单列表失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void initList(String hotelName,String imageUrl, String roomNumber, String date, String price){
        Order order = new Order(hotelName, Constant.URL_IMG + imageUrl, date, "房间号：" + roomNumber, "金额：￥" + price);
        ordersList.add(order);
    }

}
