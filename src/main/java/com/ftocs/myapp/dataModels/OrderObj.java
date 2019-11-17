package com.ftocs.myapp.dataModels;

import java.util.List;

public class OrderObj {
    public int UserID;
    public String Lat;
    public List<OrderProductObj> ListOrderProducts;
    public String Long;
    public float Price;
    public String Address;


    public OrderObj(int userID, String lat, String aLong, float price, List<OrderProductObj> listOrderProducts,String aAddress) {
        UserID = userID;
        Lat = lat;
        Long = aLong;
        Price = price;
        ListOrderProducts = listOrderProducts;
        Address= aAddress;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public List<OrderProductObj> getListOrderProducts() {
        return ListOrderProducts;
    }

    public void setListOrderProducts(List<OrderProductObj> listOrderProducts) {
        ListOrderProducts = listOrderProducts;
    }


}
