package com.ftocs.myapp.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.ftocs.myapp.dataModels.OrderEntity;


@Database(entities = {OrderEntity.class}, version = 2, exportSchema = false)
public abstract class OrderDb  extends RoomDatabase {
    public static OrderDb orderDb;
    public abstract AppDao daoAccess();

    public static OrderDb getAppDatabase(Context context) {
        if (orderDb == null) {
            orderDb = Room.databaseBuilder(context.getApplicationContext(), OrderDb.class, "ftos_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }
        return orderDb;
    }
}