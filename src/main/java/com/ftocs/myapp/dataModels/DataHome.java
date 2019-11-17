package com.ftocs.myapp.dataModels;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataHome{

	@SerializedName("CancelOrders")
	private int cancelOrders;

	@SerializedName("DeliverdOrders")
	private int deliverdOrders;

	@SerializedName("PendingOrders")
	private int pendingOrders;

	@SerializedName("$id")
	private String id;

	@SerializedName("ConfirmedOrders")
	private int confirmedOrders;

	public void setCancelOrders(int cancelOrders){
		this.cancelOrders = cancelOrders;
	}

	public int getCancelOrders(){
		return cancelOrders;
	}

	public void setDeliverdOrders(int deliverdOrders){
		this.deliverdOrders = deliverdOrders;
	}

	public int getDeliverdOrders(){
		return deliverdOrders;
	}

	public void setPendingOrders(int pendingOrders){
		this.pendingOrders = pendingOrders;
	}

	public int getPendingOrders(){
		return pendingOrders;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setConfirmedOrders(int confirmedOrders){
		this.confirmedOrders = confirmedOrders;
	}

	public int getConfirmedOrders(){
		return confirmedOrders;
	}

	@Override
 	public String toString(){
		return 
			"DataHome{" + 
			"cancelOrders = '" + cancelOrders + '\'' + 
			",deliverdOrders = '" + deliverdOrders + '\'' + 
			",pendingOrders = '" + pendingOrders + '\'' + 
			",$id = '" + id + '\'' + 
			",confirmedOrders = '" + confirmedOrders + '\'' + 
			"}";
		}
}