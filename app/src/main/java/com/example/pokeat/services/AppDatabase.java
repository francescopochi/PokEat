package com.example.pokeat.services;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.pokeat.dao.OrderDao;
import com.example.pokeat.datamodels.Order;

@Database(entities = {Order.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final String nomeDatabase = "orders_db";

    public abstract OrderDao orderDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context,AppDatabase.class,nomeDatabase).build();
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

}