package com.example.hotel.gson;

import com.google.gson.annotations.SerializedName;



public class MessageBean {
    /**
     * _id : 5a1d57af113f280940eea1d5
     * hotelname : adssa
     * number : 1012
     * type : 1
     * state : 1
     * price : 150
     * other :
     * address : 地址
     * imageurl : { pic1 : url1
     *              pic2 : url2
     *              pic3 : url3 }
     */

    @SerializedName("_id") private String id;
    @SerializedName("hotelname") private String hotelName;
    private String number;
    private int type;
    private int state;
    private String price;
    private String other;
    private String address;
    @SerializedName("imageurl") private ImageUrlBean imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ImageUrlBean getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ImageUrlBean imageUrl) {
        this.imageUrl = imageUrl;
    }

    public class ImageUrlBean {
        public String pic1;
        public String pic2;
        public String pic3;

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }

    }
}
