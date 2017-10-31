package com.example.hotel.data;

/**
 * 热门城市的数据类
 */

public class HotCity {
    private String name;
    private int imageId;

    public HotCity(String name, int imageId) {
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
