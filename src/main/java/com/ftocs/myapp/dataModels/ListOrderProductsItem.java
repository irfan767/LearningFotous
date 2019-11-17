package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class ListOrderProductsItem implements Serializable {

	@SerializedName("ProductName")
	private String productName;

	@SerializedName("Price")
	private double price;

	@SerializedName("HasDandi")
	private boolean hasDandi;

	@SerializedName("Size")
	private String size;

	@SerializedName("Quantity")
	private int quantity;

	@SerializedName("IsDandi")
	private boolean isDandi;

	@SerializedName("$id")
	private String id;

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

	public void setHasDandi(boolean hasDandi){
		this.hasDandi = hasDandi;
	}

	public boolean isHasDandi(){
		return hasDandi;
	}

	public void setSize(String size){
		this.size = size;
	}

	public String getSize(){
		return size;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setIsDandi(boolean isDandi){
		this.isDandi = isDandi;
	}

	public boolean isIsDandi(){
		return isDandi;
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
			"ListOrderProductsItem{" + 
			"productName = '" + productName + '\'' + 
			",price = '" + price + '\'' + 
			",hasDandi = '" + hasDandi + '\'' + 
			",size = '" + size + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",isDandi = '" + isDandi + '\'' + 
			",$id = '" + id + '\'' + 
			"}";
		}
}