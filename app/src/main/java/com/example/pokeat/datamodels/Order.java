package com.example.pokeat.datamodels;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "order")
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @Embedded
    private Restaurant restaurant;

    @ColumnInfo(name = "products")
    private List<Product> products;

    @ColumnInfo(name = "total")
    private float total;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}