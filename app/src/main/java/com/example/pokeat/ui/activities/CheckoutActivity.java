package com.example.pokeat.ui.activities;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.pokeat.R;
import com.example.pokeat.datamodels.Order;
import com.example.pokeat.datamodels.Product;
import com.example.pokeat.ui.adapters.OrderProductsAdapter;
import com.example.pokeat.ui.adapters.ProductAdapter;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    Intent intent;
    TextView restaurantTitle;
    RecyclerView.LayoutManager layoutManager;
    OrderProductsAdapter adapter;
    RecyclerView ordersRV;

    ArrayList<Order> ordersArrayList;
    public ArrayList<Product> productsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        intent = getIntent();

        layoutManager = new LinearLayoutManager(this);
        adapter = new OrderProductsAdapter(this, getProducts());

        ordersRV = findViewById(R.id.orders_rv);
        ordersRV.setLayoutManager(layoutManager);
        ordersRV.setAdapter(adapter);

        restaurantTitle = findViewById(R.id.restaurant_name_final);
        restaurantTitle.append(intent.getStringExtra("restaurant_name"));

    }

    private ArrayList<Order> getOrders(){
            ordersArrayList = new ArrayList<>();
            Order order1 = new Order(getProducts(), intent.getStringExtra("restaurant_name"));
            ordersArrayList.add(order1);
            return ordersArrayList;
    }

    private ArrayList<Product> getProducts(){
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
}
