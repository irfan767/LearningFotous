package com.ftocs.myapp.dataModels;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ProductListDM{

	@SerializedName("List")
	private List<ListProductItem> listProduct;

	@SerializedName("IsSuccess")
	private boolean isSuccess;

	@SerializedName("$id")
	private String id;

	public void setListProduct(List<ListProductItem> listProduct){
		this.listProduct = listProduct;
	}

	public List<ListProductItem> getListProduct(){
		return listProduct;
	}

	public void setIsSuccess(boolean isSuccess){
		this.isSuccess = isSuccess;
	}

	public boolean isIsSuccess(){
		return isSuccess;
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
			"ProductListDM{" + 
			"listProduct = '" + listProduct + '\'' + 
			",isSuccess = '" + isSuccess + '\'' + 
			",$id = '" + id + '\'' + 
			"}";
		}
}