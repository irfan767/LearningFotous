package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ContactUsDm{

	@SerializedName("ContactNo")
	private String contactNo;

	@SerializedName("Email")
	private String email;

	@SerializedName("Address")
	private String address;

	@SerializedName("ID")
	private int iD;

	@SerializedName("MapScript")
	private String mapScript;

	@SerializedName("Key")
	private Object key;

	@SerializedName("$id")
	private String id;

	@SerializedName("LocationLat")
	private String locationLat;

	@SerializedName("LocationLong")
	private String locationLong;

	public void setContactNo(String contactNo){
		this.contactNo = contactNo;
	}

	public String getContactNo(){
		return contactNo;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setID(int iD){
		this.iD = iD;
	}

	public int getID(){
		return iD;
	}

	public void setMapScript(String mapScript){
		this.mapScript = mapScript;
	}

	public String getMapScript(){
		return mapScript;
	}

	public void setKey(Object key){
		this.key = key;
	}

	public Object getKey(){
		return key;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setLocationLat(String locationLat){
		this.locationLat = locationLat;
	}

	public String getLocationLat(){
		return locationLat;
	}

	public void setLocationLong(String locationLong){
		this.locationLong = locationLong;
	}

	public String getLocationLong(){
		return locationLong;
	}

	@Override
 	public String toString(){
		return 
			"ContactUsDm{" + 
			"contactNo = '" + contactNo + '\'' + 
			",email = '" + email + '\'' + 
			",address = '" + address + '\'' + 
			",iD = '" + iD + '\'' + 
			",mapScript = '" + mapScript + '\'' + 
			",key = '" + key + '\'' + 
			",$id = '" + id + '\'' + 
			",locationLat = '" + locationLat + '\'' + 
			",locationLong = '" + locationLong + '\'' + 
			"}";
		}
}