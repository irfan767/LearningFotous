package com.ftocs.myapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ftocs.myapp.dataModels.DataSizeItem;
import com.ftocs.myapp.dataModels.OrderEntity;

import java.util.List;
@Dao
public interface AppDao {
    @Insert
    Long insertData(OrderEntity orderEntity);

    @Query("SELECT * FROM oderdetail")
    LiveData<List<OrderEntity>> fetchAllTsks();
}
