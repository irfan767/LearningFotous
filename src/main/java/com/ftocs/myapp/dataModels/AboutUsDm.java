package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class AboutUsDm{

	@SerializedName("Descriptions")
	private String descriptions;

	@SerializedName("ID")
	private int iD;

	@SerializedName("$id")
	private String id;

	@SerializedName("Name")
	private String name;

	public void setDescriptions(String descriptions){
		this.descriptions = descriptions;
	}

	public String getDescriptions(){
		return descriptions;
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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"AboutUsDm{" + 
			"descriptions = '" + descriptions + '\'' + 
			",iD = '" + iD + '\'' + 
			",$id = '" + id + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}