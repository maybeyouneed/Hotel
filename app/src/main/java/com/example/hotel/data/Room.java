package com.example.hotel.data;



public class Room {

    private String name;
    private String info;
    private int imageId;

    public Room(String name, String info, int imageId) {
        this.name = name;
        this.info = info;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
