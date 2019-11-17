package com.ftocs.myapp.dataModels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "oderdetail")
public class OrderEntity {

    public OrderEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "hasDandi")
    private boolean hasDandi;
    @ColumnInfo(name = "Size")
    private String size;

    @ColumnInfo(name = "Quantity")
    private int Quantity;

    @ColumnInfo(name = "IsDandi")
    private boolean IsDandi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public boolean isHasDandi() {
        return hasDandi;
    }

    public void setHasDandi(boolean hasDandi) {
        this.hasDandi = hasDandi;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public boolean getIsDandi() {
        return IsDandi;
    }

    public void setIsDandi(boolean isDandi) {
        IsDandi = isDandi;
    }
}
