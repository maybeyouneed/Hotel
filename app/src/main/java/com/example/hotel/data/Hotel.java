package com.example.hotel.data;

/**
 * 酒店信息的数据类
 */

public class Hotel {
    private String name;
    private int imageId;

    public Hotel(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

}
