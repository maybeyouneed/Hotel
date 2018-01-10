package com.example.hotel.gson;

import java.util.List;

/**
 * 房间信息数据的GSon实体类
 */

public class RoomInfo {
    /**
     * name : Get rooms succeed
     * result : true
     * message : [{"_id":"5a1d57af113f280940eea1d5","hotelname":"adssa","number":"1012","type":1,"state":1,"price":"150","other":"","address":"地址","imageurl":{}}]
     */

    private String name;
    private boolean result;
    private List<MessageBean> message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

}
