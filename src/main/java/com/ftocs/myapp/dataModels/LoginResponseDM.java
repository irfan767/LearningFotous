package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LoginResponseDM{

	@SerializedName("user_Type")
	private int userType;

	@SerializedName("Ext")
	private String ext;

	@SerializedName("ContactNo")
	private String contactNo;

	@SerializedName("Email")
	private String email;

	@SerializedName("Address")
	private String address;

	@SerializedName("CityID")
	private int cityID;

	@SerializedName("FullName")
	private String fullName;

	@SerializedName("ID")
	private int iD;

	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	@SerializedName("$id")
	private String id;

	@SerializedName("Password")
	private String password;

	@SerializedName("Message")
	private String message;


	@SerializedName("Photo")
	private String photo;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUserType(int userType){
		this.userType = userType;
	}

	public int getUserType(){
		return userType;
	}

	public void setExt(String ext){
		this.ext = ext;
	}

	public String getExt(){
		return ext;
	}

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

	public void setCityID(int cityID){
		this.cityID = cityID;
	}

	public int getCityID(){
		return cityID;
	}

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
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

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponseDM{" + 
			"user_Type = '" + userType + '\'' + 
			",ext = '" + ext + '\'' + 
			",contactNo = '" + contactNo + '\'' + 
			",email = '" + email + '\'' + 
			",address = '" + address + '\'' + 
			",cityID = '" + cityID + '\'' + 
			",fullName = '" + fullName + '\'' + 
			",iD = '" + iD + '\'' + 
			",$id = '" + id + '\'' + 
			",password = '" + password + '\'' + 
			"}";
		}
}