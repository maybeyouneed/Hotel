package com.example.hotel.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.hotel.data.CommonResponse;
import com.example.hotel.gson.OrderInfo;
import com.example.hotel.gson.RoomInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * .
 */

public class Utility {

    public static CommonResponse handleLoginResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject object = new JSONObject(response);
                CommonResponse commonResponse = new CommonResponse();
                commonResponse.setName(object.getString("name"));
                commonResponse.setResult(object.getBoolean("result"));
                commonResponse.setMessage(object.getString("message"));
                return commonResponse;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static RoomInfo handleRoomInfoResponse(String response) {
        return new Gson().fromJson(response, RoomInfo.class);
    }

    public static OrderInfo handleOrdersResponse(String response){
        return new Gson().fromJson(response, OrderInfo.class);
    }

    public static String handleServerTime(String utcDate){
        String time = utcDate.substring(5, 10);   //11-04
        StringBuilder sb = new StringBuilder(time);
        sb.replace(2, 3, "月");
        sb.append("日");
        return sb.toString();
    }

    public static String handleLocalTime(String time){         //date: 2018-1-7
        DateFormat format = new SimpleDateFormat("yyyy-m-d");
        try {
            Date date = format.parse(time);                    //2018-01-07
            Log.d("时间格式转换结果：", String.format("%tFT00:00:00.000Z", date));
            return String.format("%tFT00:00:00.000Z", date);   //2018-01-06T00:00:00.000Z
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date curDate = new Date(System.currentTimeMillis());
        String curTime = format.format(curDate);
        Log.d("本地时间格式转换：", curTime.replace("+0800", "Z"));
        return curTime.replace("+0800", "Z");
    }

}
