package com.example.hotel.data;

/**
 * Hotel类的副本，测试用
 */

public class Hotel {
    private String hotelName;
    private String imageUrl;
    private String number;

    public Hotel(String hotelName, String imageUrl, String number) {
        this.hotelName = hotelName;
        this.imageUrl = imageUrl;
        this.number = number;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
