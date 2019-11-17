package com.ftocs.myapp.dataModels;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ListItem implements Serializable {

	@SerializedName("Status")
	private String status;

	@SerializedName("Price")
	private double price;

	@SerializedName("Size")
	private Object size;

	@SerializedName("Long")
	private String longg;

	@SerializedName("Quantity")
	private int quantity;

	@SerializedName("OrderNo")
	private int orderNo;

	@SerializedName("Time")
	private String time;

	@SerializedName("Date")
	private String date;

	@SerializedName("Lat")
	private String lat;

	@SerializedName("$id")
	private String id;

	@SerializedName("CategoryName")
	private String categoryName;

	@SerializedName("CategoryIcon")
	private String categoryIcon;

	@SerializedName("ID")
	private int iD;



	//datastart
	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setCategoryIcon(String categoryIcon){
		this.categoryIcon = categoryIcon;
	}

	public String getCategoryIcon(){
		return categoryIcon;
	}

	public void setID(int iD){
		this.iD = iD;
	}

	public int getID(){
		return iD;
	}





	// dataend

	@SerializedName("ListOrderProducts")
	private List<ListOrderProductsItem> listOrderProducts;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}

	public void setSize(Object size){
		this.size = size;
	}

	public Object getSize(){
		return size;
	}

	public void setLong(String longg){
		this.longg = longg;
	}

	public String getLong(){
		return longg;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
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

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setListOrderProducts(List<ListOrderProductsItem> listOrderProducts){
		this.listOrderProducts = listOrderProducts;
	}

	public List<ListOrderProductsItem> getListOrderProducts(){
		return listOrderProducts;
	}

	@Override
 	public String toString(){
		return 
			"ListItem{" + 
			"status = '" + status + '\'' + 
			",price = '" + price + '\'' + 
			",size = '" + size + '\'' + 
			",long = '" + longg + '\'' +
			",quantity = '" + quantity + '\'' + 
			",orderNo = '" + orderNo + '\'' + 
			",time = '" + time + '\'' + 
			",date = '" + date + '\'' + 
			",lat = '" + lat + '\'' + 
			",$id = '" + id + '\'' + 
			",listOrderProducts = '" + listOrderProducts + '\'' + 
			"}";
		}
}