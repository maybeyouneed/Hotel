package com.example.hotel.data;


public class Order {

    private String hotelName;
    private String imageUrl;
    private String time;
    private String info;
    private String price;

    public Order(String hotelName, String imageUrl, String time, String info, String price) {
        this.hotelName = hotelName;
        this.imageUrl = imageUrl;
        this.time = time;
        this.info = info;
        this.price = price;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
