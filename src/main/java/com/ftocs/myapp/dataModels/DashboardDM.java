package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DashboardDM{

	@SerializedName("IsSuccess")
	private boolean isSuccess;

	@SerializedName("data")
	private DataHome dataHome;

	@SerializedName("$id")
	private String id;

	public void setIsSuccess(boolean isSuccess){
		this.isSuccess = isSuccess;
	}

	public boolean isIsSuccess(){
		return isSuccess;
	}

	public void setDataHome(DataHome dataHome){
		this.dataHome = dataHome;
	}

	public DataHome getDataHome(){
		return dataHome;
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
			"DashboardDM{" + 
			"isSuccess = '" + isSuccess + '\'' + 
			",data_home = '" + dataHome + '\'' + 
			",$id = '" + id + '\'' + 
			"}";
		}
}