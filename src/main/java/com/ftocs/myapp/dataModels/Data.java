package com.ftocs.myapp.dataModels;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("HasDandi")
	private boolean hasDandi;

	@SerializedName("ProductPhotos")
	private List<ProductPhotosItem> productPhotos;

	@SerializedName("ID")
	private int iD;

	@SerializedName("IsDandi")
	private boolean isDandi;

	@SerializedName("Detail")
	private String detail;

	@SerializedName("$id")
	private String id;

	@SerializedName("Name")
	private Object name;

	public void setHasDandi(boolean hasDandi){
		this.hasDandi = hasDandi;
	}

	public boolean isHasDandi(){
		return hasDandi;
	}

	public void setProductPhotos(List<ProductPhotosItem> productPhotos){
		this.productPhotos = productPhotos;
	}

	public List<ProductPhotosItem> getProductPhotos(){
		return productPhotos;
	}

	public void setID(int iD){
		this.iD = iD;
	}

	public int getID(){
		return iD;
	}

	public void setIsDandi(boolean isDandi){
		this.isDandi = isDandi;
	}

	public boolean isIsDandi(){
		return isDandi;
	}

	public void setDetail(String detail){
		this.detail = detail;
	}

	public String getDetail(){
		return detail;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setName(Object name){
		this.name = name;
	}

	public Object getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"hasDandi = '" + hasDandi + '\'' + 
			",productPhotos = '" + productPhotos + '\'' + 
			",iD = '" + iD + '\'' + 
			",isDandi = '" + isDandi + '\'' + 
			",detail = '" + detail + '\'' + 
			",$id = '" + id + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}