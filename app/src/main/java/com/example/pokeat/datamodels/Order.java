package com.example.pokeat.datamodels;

import java.util.ArrayList;

public class Order {

    private String restaurantName;
    public ArrayList<Product> productsArrayList;
    private float total;

    public Order(ArrayList<Product> products, String restaurantName) {
        this.productsArrayList = products;
        this.restaurantName = restaurantName;
    }

    public String getRestaurant() {
        return restaurantName;
    }

    public void setRestaurant(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float calcolaTotale() {
        total = 0;
        for (Product elem : productsArrayList) {
            total += elem.getSubtotal();
        }

        return total;
    }
}
