package com.ftocs.myapp.dataModels;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CategoryDM{

	@SerializedName("IsSuccesss")
	private boolean isSuccess;

	@SerializedName("Listt")
	private List<ListItem> list;

	@SerializedName("$id")
	private String id;

	public void setIsSuccess(boolean isSuccess){
		this.isSuccess = isSuccess;
	}

	public boolean isIsSuccess(){
		return isSuccess;
	}

	public void setList(List<ListItem> list){
		this.list = list;
	}

	public List<ListItem> getList(){
		return list;
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
			"CategoryDM{" + 
			"isSuccess = '" + isSuccess + '\'' + 
			",list = '" + list + '\'' + 
			",$id = '" + id + '\'' + 
			"}";
		}
}