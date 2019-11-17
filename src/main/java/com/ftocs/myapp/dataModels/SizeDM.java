package com.ftocs.myapp.dataModels;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class SizeDM{

	@SerializedName("data")
	private List<DataSizeItem> dataSize;

	@SerializedName("IsSuccess")
	private boolean isSuccess;

	@SerializedName("$id")
	private String id;

	public void setDataSize(List<DataSizeItem> dataSize){
		this.dataSize = dataSize;
	}

	public List<DataSizeItem> getDataSize(){
		return dataSize;
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
			"SizeDM{" + 
			"data_size = '" + dataSize + '\'' + 
			",isSuccess = '" + isSuccess + '\'' + 
			",$id = '" + id + '\'' + 
			"}";
		}
}