package com.example.hotel.interfaces;


import com.example.hotel.data.CommonResponse;

public interface HttpCallBackListener {
    void onSuccess(CommonResponse response);
    void onFailed();
    void onError();

}
