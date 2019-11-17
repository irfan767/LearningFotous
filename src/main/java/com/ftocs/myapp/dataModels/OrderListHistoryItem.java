package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class OrderListHistoryItem implements Serializable {

	@SerializedName("Status")
	private String status;

	@SerializedName("ProductName")
	private String productName;

	@SerializedName("Price")
	private double price;

	@SerializedName("OrderNo")
	private int orderNo;

	@SerializedName("Time")
	private String time;

	@SerializedName("Date")
	private String date;

	@SerializedName("$id")
	private String id;

	@SerializedName("Size")
	private String size;


    @SerializedName("HasDandi")
	private boolean hasDandi;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @SerializedName("IsDandi")
    private boolean IsDandi;

    @SerializedName("Quantity")
    private String quantity;

    public boolean isHasDandi() {
        return hasDandi;
    }

    public void setHasDandi(boolean hasDandi) {
        this.hasDandi = hasDandi;
    }

    public boolean isDandi() {
        return IsDandi;
    }

    public void setDandi(boolean dandi) {
        IsDandi = dandi;
    }

    public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}

	public void setOrderNo(int orderNo){
		this.orderNo = orderNo;
	}

	public int getOrderNo(){
		return orderNo;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"OrderListHistoryItem{" + 
			"status = '" + status + '\'' + 
			",productName = '" + productName + '\'' + 
			",price = '" + price + '\'' + 
			",orderNo = '" + orderNo + '\'' + 
			",time = '" + time + '\'' + 
			",date = '" + date + '\'' + 
			",$id = '" + id + '\'' + 
			"}";
		}
}