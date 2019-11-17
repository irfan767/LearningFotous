package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class ListProductItem implements Serializable {

	@SerializedName("Description")
	private String description;

	@SerializedName("ProductID")
	private int productID;

	@SerializedName("CateogoryID")
	private int cateogoryID;

	@SerializedName("Image")
	private String image;

	@SerializedName("IsDandi")
	private boolean isDandi;

	@SerializedName("HasDandi")
	private boolean hasDandi;

	@SerializedName("$id")
	private String id;

	@SerializedName("Name")
	private String name;

	public boolean isHasDandi() {
		return hasDandi;
	}

	public void setHasDandi(boolean hasDandi) {
		this.hasDandi = hasDandi;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setProductID(int productID){
		this.productID = productID;
	}

	public int getProductID(){
		return productID;
	}

	public void setCateogoryID(int cateogoryID){
		this.cateogoryID = cateogoryID;
	}

	public int getCateogoryID(){
		return cateogoryID;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"ListProductItem{" + 
			"description = '" + description + '\'' + 
			",productID = '" + productID + '\'' + 
			",cateogoryID = '" + cateogoryID + '\'' + 
			",image = '" + image + '\'' + 
			",isDandi = '" + isDandi + '\'' + 
			",$id = '" + id + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}