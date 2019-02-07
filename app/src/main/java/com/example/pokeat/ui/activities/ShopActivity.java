package com.example.pokeat.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokeat.R;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String restaurantName, restaurantAddress, restaurantPhone, restaurantMinPrice;
    TextView restaurantTitleTv,  restaurantAddressTv, restaurantPhoneTv, restaurantMinPriceTv;
    ImageView restaurantImg, locationImg;

    int imgSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        intent = getIntent();

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
