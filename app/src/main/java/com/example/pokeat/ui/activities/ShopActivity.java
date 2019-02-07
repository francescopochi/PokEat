package com.example.pokeat.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokeat.R;
import com.example.pokeat.datamodels.Product;
import com.example.pokeat.ui.adapters.ProductAdapter;
import com.example.pokeat.ui.adapters.RestaurantAdapter;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String restaurantName, restaurantAddress, restaurantPhone, restaurantMinPrice;
    TextView restaurantTitleTv,  restaurantAddressTv, restaurantPhoneTv, restaurantMinPriceTv;
    ImageView restaurantImg, locationImg;

    RecyclerView productsRV;
    RecyclerView.LayoutManager layoutManager;
    ProductAdapter adapter;
    int imgSrc;
    ArrayList<Product> productsArrayList;

    public ArrayList<Product> getProducts(){
        productsArrayList = new ArrayList<>();

        productsArrayList.add(new Product("Hamburger", 2.00f));
        productsArrayList.add(new Product("Pizza", 4.3f));
        productsArrayList.add(new Product("Calzone", 3.50f));
        productsArrayList.add(new Product("Spaghetti", 6.00f));
        productsArrayList.add(new Product("Bistecca alla Fiorentina", 12.50f));
        productsArrayList.add(new Product("Carbonara", 7.50f));
        productsArrayList.add(new Product("Insalata mista", 4.50f));
        productsArrayList.add(new Product("Petto di pollo", 8.00f));
        productsArrayList.add(new Product("Polenta", 5.00f));
        productsArrayList.add(new Product("Verdure miste", 4.00f));

        return productsArrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        intent = getIntent();

        layoutManager = new LinearLayoutManager(this);
        adapter = new ProductAdapter(this, getProducts());

        productsRV = findViewById(R.id.products_rv);
        productsRV.setLayoutManager(layoutManager);
        productsRV.setAdapter(adapter);

        restaurantTitleTv = findViewById(R.id.restaurant_name);
        restaurantAddressTv = findViewById(R.id.restaurant_address2);
        restaurantPhoneTv = findViewById(R.id.restaurant_phone2);
        restaurantMinPriceTv = findViewById(R.id.minimum_order);
        locationImg = findViewById(R.id.position_img);
        restaurantImg = findViewById(R.id.img);

        restaurantName = intent.getStringExtra("restaurant_name");
        restaurantAddress = intent.getStringExtra("restaurant_address");
        restaurantPhone = intent.getStringExtra("restaurant_phone");
        restaurantMinPrice = intent.getStringExtra("restaurant_min_price");
        imgSrc = intent.getIntExtra("restaurant_img_src", R.drawable.ic_image_black_24dp);

        restaurantImg.setImageResource(imgSrc);
        restaurantTitleTv.append(restaurantName);
        restaurantAddressTv.append(restaurantAddress);
        restaurantPhoneTv.append(restaurantPhone);
        restaurantMinPriceTv.append(restaurantMinPrice);

        locationImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.position_img){
            giveDirections();
        }
    }

    public void giveDirections(){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+restaurantAddress));
        startActivity(intent);
    }
}
