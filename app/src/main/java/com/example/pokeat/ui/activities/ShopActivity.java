package com.example.pokeat.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.pokeat.R;
import com.example.pokeat.datamodels.Product;
import com.example.pokeat.datamodels.Restaurant;
import com.example.pokeat.services.RestController;
import com.example.pokeat.ui.adapters.ProductAdapter;
import com.example.pokeat.ui.adapters.RestaurantAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener, ProductAdapter.OnQuanityChangedListener, Response.Listener<String>, Response.ErrorListener  {

    Intent intent;
    String restaurantId, restaurantName, restaurantAddress, restaurantPhone, restaurantImgSrc;
    float restaurantMinPrice;
    TextView restaurantTitleTv,  restaurantAddressTv, restaurantPhoneTv, restaurantMinPriceTv, totalTv;
    ImageView restaurantImg, locationImg;
    ProgressBar progressBar;
    Button checkoutBtn;
    private float total = 0f;
    RestController restController;

    RecyclerView productsRV;
    RecyclerView.LayoutManager layoutManager;
    ProductAdapter adapter;
    Restaurant restaurant;
    public ArrayList<Product> productsArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        intent = getIntent();
        restaurantId = intent.getStringExtra("restaurant_id");

        layoutManager = new LinearLayoutManager(this);
        adapter = new ProductAdapter(this, getProducts());
        adapter.setOnQuanityChangedListener(this);

        productsRV = findViewById(R.id.products_rv);
        productsRV.setLayoutManager(layoutManager);
        productsRV.setAdapter(adapter);

        restaurantTitleTv = findViewById(R.id.restaurant_name);
        restaurantAddressTv = findViewById(R.id.restaurant_address2);
        restaurantPhoneTv = findViewById(R.id.restaurant_phone2);
        restaurantMinPriceTv = findViewById(R.id.minimum_order);
        locationImg = findViewById(R.id.position_img);
        restaurantImg = findViewById(R.id.img);
        totalTv = findViewById(R.id.total_tv);
        progressBar = findViewById(R.id.my_progressBar);
        checkoutBtn = findViewById(R.id.checkout_btn);

        checkoutBtn.setOnClickListener(this);

        restController = new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT.concat(restaurantId), this, this);

        locationImg.setOnClickListener(this);
        restaurantPhoneTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.position_img){
            giveDirections();
        } else if(v.getId() == R.id.checkout_btn){
            Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
            intent.putExtra("restaurant_name", restaurantName);
            startActivity(intent);
        } else if(v.getId() == R.id.restaurant_phone2){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", restaurantPhone, null));
            startActivity(intent);
        }
    }

    @Override
    public void onChange(float price) {
        updateTotal(price);
        updateProgress((int)total*100);
        setButtonVisibility();
    }

    private void updateTotal(float item){
        total = total + item;
        totalTv.setText("Totale: ".concat(String.valueOf(total)));
    }

    private void updateProgress(int progress){
        progressBar.setProgress(progress);
    }

    private void setButtonVisibility(){
        if(progressBar.getProgress() == progressBar.getMax()){
            checkoutBtn.setEnabled(true);
        } else {
            checkoutBtn.setEnabled(false);
        }
    }

    public void giveDirections(){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+restaurantAddress));
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("MAINACTIVITY", error.getMessage());
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        try{
            JSONObject jsonObject = new JSONObject(response);
            restaurant = new Restaurant(jsonObject);
            restaurantName = restaurant.getNome();
            restaurantAddress = restaurant.getIndirizzo();
            restaurantPhone = restaurant.getNumTelefono();
            restaurantMinPrice = restaurant.getImportoMin();
            restaurantImgSrc = restaurant.getImageUrl();

            setTextViews(restaurantName,restaurantAddress, restaurantPhone, restaurantMinPrice,restaurantImgSrc);

            Log.e("SHOPACTIVITY", response);

        } catch (JSONException je){
            Log.e("MAINACTIVITY", je.getMessage());
            Toast.makeText(this, je.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void setTextViews(String restaurantName, String restaurantAddress, String restaurantPhone, float restaurantMinPrice, String restaurantImgSrc){
        restaurantTitleTv.setText(restaurantName);
        restaurantAddressTv.setText(restaurantAddress);
        restaurantPhoneTv.setText(restaurantPhone);
        restaurantMinPriceTv.setText(String.valueOf(restaurantMinPrice));
        Glide.with(this).load(restaurantImgSrc).into(restaurantImg);
        progressBar.setMax((int)restaurantMinPrice*100);
    }
}
