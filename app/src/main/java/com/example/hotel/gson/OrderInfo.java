package com.example.hotel.gson;


import java.util.List;

public class OrderInfo {

    /**
     * name : Get order succeed
     * result : true
     * message : [{"_id":"59eaf91c113f280b94e7efd5","user":"test","intime":"2017-11-04T00:00:00.000Z","outtime":"2017-11-06T00:00:00.000Z","people":[{"name":"daw ea wd"},{"name":"qwedefdsf"}],"cost":"5555","room":"5a1d57af113f280940eea1d5","state":0,"ordertime":"2016-11-01T00:00:00.000Z"}]
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

    public static class MessageBean {
        /**
         * _id : 59eaf91c113f280b94e7efd5
         * user : test
         * intime : 2017-11-04T00:00:00.000Z
         * outtime : 2017-11-06T00:00:00.000Z
         * people : [{"name":"daw ea wd"},{"name":"qwedefdsf"}]
         * cost : 5555
         * room : 5a1d57af113f280940eea1d5
         * state : 0
         * ordertime : 2016-11-01T00:00:00.000Z
         */

        private String _id;
        private String user;
        private String intime;
        private String outtime;
        private String cost;
        private String room;
        private int state;
        private String ordertime;
        private String people;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getIntime() {
            return intime;
        }

        public void setIntime(String intime) {
            this.intime = intime;
        }

        public String getOuttime() {
            return outtime;
        }

        public void setOuttime(String outtime) {
            this.outtime = outtime;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getOrdertime() {
            return ordertime;
        }

        public void setOrdertime(String ordertime) {
            this.ordertime = ordertime;
        }

        public String getPeople() {
            return people;
        }

        public void setPeople(String people) {
            this.people = people;
        }
    }
}
