package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ProductPhotosItem{

	@SerializedName("path")
	private String path;

	@SerializedName("ID")
	private int iD;

	@SerializedName("$id")
	private String id;

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}

	public void setID(int iD){
		this.iD = iD;
	}

	public int getID(){
		return iD;
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
			"ProductPhotosItem{" + 
			"path = '" + path + '\'' + 
			",iD = '" + iD + '\'' + 
			",$id = '" + id + '\'' + 
			"}";
		}
}