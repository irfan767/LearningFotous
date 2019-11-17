package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CityDM{

	@SerializedName("cityName")
	private String cityName;

	@SerializedName("ID")
	private int iD;

	@SerializedName("$id")
	private String id;

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
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
			"CityDM{" + 
			"cityName = '" + cityName + '\'' + 
			",iD = '" + iD + '\'' + 
			",$id = '" + id + '\'' + 
			"}";
		}
}