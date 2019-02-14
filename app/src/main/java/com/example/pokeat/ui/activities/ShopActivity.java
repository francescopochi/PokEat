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
import com.example.pokeat.ui.adapters.ProductAdapter;
import com.example.pokeat.ui.adapters.RestaurantAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener, ProductAdapter.OnQuanityChangedListener {

    Intent intent;
    String restaurantName, restaurantAddress, restaurantPhone, restaurantImgSrc;
    float restaurantMinPrice;
    TextView restaurantTitleTv,  restaurantAddressTv, restaurantPhoneTv, restaurantMinPriceTv, totalTv;
    ImageView restaurantImg, locationImg;
    ProgressBar progressBar;
    Button checkoutBtn;
    private float total = 0f;

    RecyclerView productsRV;
    RecyclerView.LayoutManager layoutManager;
    ProductAdapter adapter;
    Restaurant restaurant;
    public ArrayList<Product> productsArrayList;
    int restaurant_id;

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

        restaurant_id = intent.getIntExtra("restaurant_id", -1);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://5c642463c969210014a32e05.mockapi.io/api/v1/restaurant/" + (restaurant_id+1) + "";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, // HTTP request method
                url,   // Destination
                new Response.Listener<String>() {   // Listener for successful response
                    @Override
                    public void onResponse(String response) {
                        Log.d("MAINACTIVIY", response);
                        //Parsing
                        try{
                            JSONObject restaurantJSON = new JSONObject(response);
                            restaurant= new Restaurant(restaurantJSON);

                            restaurantPhone = restaurant.getNumTelefono();
                            restaurantName = restaurant.getNome();
                            restaurantMinPrice = restaurant.getImportoMin();
                            restaurantAddress = restaurant.getIndirizzo();
                            restaurantImgSrc = restaurant.getImageUrl();

                            restaurantTitleTv.setText(restaurantName);
                            restaurantAddressTv.setText(restaurantAddress);
                            restaurantPhoneTv.setText(restaurantPhone);
                            restaurantMinPriceTv.setText(String.valueOf(restaurantMinPrice));
                            Glide.with(ShopActivity.this).load(restaurantImgSrc).into(restaurantImg);
                            progressBar.setMax((int)(restaurantMinPrice)*100);
                        } catch (JSONException e){
                            Log.i("SHOPACTIVYTY", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {  // Listener for error response
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("SHOPACTIVYTY", error.getMessage() + " " + error.networkResponse.statusCode);
                    }
                }
        );
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

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
}
