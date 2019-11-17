package com.ftocs.myapp.dataModels;

public class OrderProductObj {
    public int ProductID;
    public boolean HasDandi;
    public String Size;
    public int Quantity;
    public float Price;
    public boolean IsDandi;

    public OrderProductObj(int productID, boolean hasDandi, String size, int quantity, float price, boolean isDandi) {
        ProductID = productID;
        HasDandi = hasDandi;
        Size = size;
        Quantity = quantity;
        Price = price;
        IsDandi = isDandi;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public boolean isHasDandi() {
        return HasDandi;
    }

    public void setHasDandi(boolean hasDandi) {
        HasDandi = hasDandi;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public boolean isDandi() {
        return IsDandi;
    }

    public void setDandi(boolean dandi) {
        IsDandi = dandi;
    }
}
