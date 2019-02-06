package com.example.pokeat.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pokeat.R;

public class ShopActivity extends AppCompatActivity {

    Intent intent;
    String restaurantName, restaurantAddress, restaurantPhone, restaurantMinPrice;
    TextView restaurantTitleTv,  restaurantAddressTv, restaurantPhoneTv, restaurantMinPriceTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        restaurantTitleTv = findViewById(R.id.restaurant_name);
        restaurantAddressTv = findViewById(R.id.restaurant_address2);
        restaurantPhoneTv = findViewById(R.id.restaurant_phone2);
        restaurantMinPriceTv = findViewById(R.id.minimum_order);

        intent = getIntent();
        restaurantName = intent.getStringExtra("restaurant_name");
        restaurantAddress = intent.getStringExtra("restaurant_address");
        restaurantPhone = intent.getStringExtra("restaurant_phone");
        restaurantMinPrice = intent.getStringExtra("restaurant_min_price");

        restaurantTitleTv.append(restaurantName);
        restaurantAddressTv.append(restaurantAddress);
        restaurantPhoneTv.append(restaurantPhone);
        restaurantMinPriceTv.append(restaurantMinPrice);
    }
}
