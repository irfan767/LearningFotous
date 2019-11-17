package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ProductInfoDM{

	@SerializedName("IsSuccess")
	private boolean isSuccess;

	@SerializedName("data")
	private Data data;

	@SerializedName("$id")
	private String id;

	public void setIsSuccess(boolean isSuccess){
		this.isSuccess = isSuccess;
	}

	public boolean isIsSuccess(){
		return isSuccess;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
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
			"ProductInfoDM{" + 
			"isSuccess = '" + isSuccess + '\'' + 
			",data = '" + data + '\'' + 
			",$id = '" + id + '\'' + 
			"}";
		}
}