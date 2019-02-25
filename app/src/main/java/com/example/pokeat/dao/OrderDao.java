package com.example.pokeat.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.pokeat.datamodels.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM `order`")
    List<Order> getAll();

    @Insert
    void insert(Order order);

    @Delete
    void delete(Order order);

}
