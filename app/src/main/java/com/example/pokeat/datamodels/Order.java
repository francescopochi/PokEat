package com.example.pokeat.datamodels;
import java.util.ArrayList;

public class Order {

    private String restaurantName;
    private ArrayList<Product> productsArrayList;
    private float total;

    public Order(ArrayList<Product> products, String restaurantName){
        this.productsArrayList = products;
        this.restaurantName = restaurantName;
    }

    public ArrayList<Product> getProducts(){
        productsArrayList = new ArrayList<>();

        productsArrayList.add(new Product("Hamburger", 2f));
        productsArrayList.add(new Product("Pizza", 4.3f));
        productsArrayList.add(new Product("Calzone", 3.5f));
        productsArrayList.add(new Product("Spaghetti", 6f));
        productsArrayList.add(new Product("Bistecca alla Fiorentina", 12.5f));
        productsArrayList.add(new Product("Carbonara", 7.5f));
        productsArrayList.add(new Product("Insalata mista", 4.5f));
        productsArrayList.add(new Product("Petto di pollo", 8f));
        productsArrayList.add(new Product("Polenta", 5f));
        productsArrayList.add(new Product("Verdure miste", 4f));

        return productsArrayList;
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
}
