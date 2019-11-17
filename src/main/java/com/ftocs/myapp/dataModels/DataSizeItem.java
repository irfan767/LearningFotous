package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataSizeItem{

	@SerializedName("SizePrice")
	private double sizePrice;

	@SerializedName("SizeID")
	private int sizeID;

	@SerializedName("$id")
	private String id;

	@SerializedName("SizeName")
	private String sizeName;

	public void setSizePrice(double sizePrice){
		this.sizePrice = sizePrice;
	}

	public double getSizePrice(){
		return sizePrice;
	}

	public void setSizeID(int sizeID){
		this.sizeID = sizeID;
	}

	public int getSizeID(){
		return sizeID;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setSizeName(String sizeName){
		this.sizeName = sizeName;
	}

	public String getSizeName(){
		return sizeName;
	}

	@Override
 	public String toString(){
		return 
			"DataSizeItem{" + 
			"sizePrice = '" + sizePrice + '\'' + 
			",sizeID = '" + sizeID + '\'' + 
			",$id = '" + id + '\'' + 
			",sizeName = '" + sizeName + '\'' + 
			"}";
		}
}