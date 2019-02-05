package com.example.pokeat.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pokeat.R;

public class ShopActivity extends AppCompatActivity {

    Intent intent;
    String restaurantName;
    TextView restaurantTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        restaurantTitle = findViewById(R.id.restaurant_name);

        intent = getIntent();
        restaurantName = intent.getStringExtra("restaurant_name");

        restaurantTitle.append(restaurantName);
    }
}
