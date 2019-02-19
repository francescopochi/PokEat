package com.example.pokeat.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.pokeat.R;
import com.example.pokeat.datamodels.Product;
import com.example.pokeat.datamodels.Restaurant;
import com.example.pokeat.services.RestController;
import com.example.pokeat.ui.SharedPreferencesUtils;
import com.example.pokeat.ui.adapters.ProductAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener, ProductAdapter.OnQuanityChangedListener, Response.Listener<String>, Response.ErrorListener {

    Intent intent;
    String restaurantId, restaurantName, restaurantAddress, restaurantPhone, restaurantImgSrc;
    float restaurantMinPrice;
    TextView restaurantTitleTv, restaurantAddressTv, restaurantPhoneTv, restaurantMinPriceTv, totalTv, emptyProducts;
    ImageView restaurantImg, locationImg;
    ProgressBar progressBar;
    Button checkoutBtn;
    private float total = 0f;
    RestController restController;
    RecyclerView productsRV;
    RecyclerView.LayoutManager layoutManager;
    ProductAdapter adapter;
    Restaurant restaurant;
    public ArrayList<Product> productsArrayList = new ArrayList<>();
    ProgressBar loadingProgressBar;
    RelativeLayout restaurantWrapper;
    private static final int LOGIN_FOR_CHECKOUT_REQUEST_CODE = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        intent = getIntent();
        restaurantId = intent.getStringExtra("restaurant_id");

        layoutManager = new LinearLayoutManager(this);
        adapter = new ProductAdapter(this);
        adapter.setOnQuanityChangedListener(this);

        productsRV = findViewById(R.id.products_rv);
        productsRV.setLayoutManager(layoutManager);
        productsRV.setAdapter(adapter);

        emptyProducts = findViewById(R.id.empty_products);
        restaurantTitleTv = findViewById(R.id.restaurant_name);
        restaurantAddressTv = findViewById(R.id.restaurant_address2);
        restaurantPhoneTv = findViewById(R.id.restaurant_phone2);
        restaurantMinPriceTv = findViewById(R.id.minimum_order);
        locationImg = findViewById(R.id.position_img);
        restaurantImg = findViewById(R.id.img);
        totalTv = findViewById(R.id.total_tv);
        progressBar = findViewById(R.id.my_progressBar);
        checkoutBtn = findViewById(R.id.checkout_btn);
        restaurantWrapper = findViewById(R.id.restaurant_wrapper);
        loadingProgressBar = findViewById(R.id.loading_progressbar);

        restController = new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT.concat(restaurantId), this, this);
        checkoutBtn.setOnClickListener(this);
        locationImg.setOnClickListener(this);
        restaurantPhoneTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.position_img) {
            giveDirections();
        } else if (v.getId() == R.id.checkout_btn) {

            String jwt = SharedPreferencesUtils.getStringValue(this, "jwt");
            if (jwt != null) {
                Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
                intent.putExtra("restaurant_name", restaurantName);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Devi essere loggato!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, LOGIN_FOR_CHECKOUT_REQUEST_CODE);
            }

        } else if (v.getId() == R.id.restaurant_phone2) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", restaurantPhone, null));
            startActivity(intent);
        }
    }

    // DOPO ESSERMI LOGGATO, DALLA SHOP ACTIVITY VADO DIRETTAMENTE AL CHECKOUT
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == LOGIN_FOR_CHECKOUT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onChange(float price) {
        updateTotal(price);
        updateProgress((int) total * 100);
        setButtonVisibility();
    }

    private void updateTotal(float item) {
        total = total + item;
        totalTv.setText("Totale: ".concat(String.valueOf(total)));
    }

    private void updateProgress(int progress) {
        progressBar.setProgress(progress);
    }

    private void setButtonVisibility() {
        if (progressBar.getProgress() == progressBar.getMax()) {
            checkoutBtn.setEnabled(true);
        } else {
            checkoutBtn.setEnabled(false);
        }
    }

    public void giveDirections() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + restaurantAddress));
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("MAINACTIVITY", error.getMessage());
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            restaurant = new Restaurant(jsonObject);
            restaurantName = restaurant.getNome();
            restaurantAddress = restaurant.getIndirizzo();
            restaurantPhone = restaurant.getNumTelefono();
            restaurantMinPrice = restaurant.getImportoMin();
            restaurantImgSrc = restaurant.getImageUrl();
            productsArrayList = restaurant.getProductsArrayList();

            setViews(restaurantName, restaurantAddress, restaurantPhone, restaurantMinPrice, restaurantImgSrc);
            adapter.setData(productsArrayList);

            if (productsArrayList.size() == 0)
                emptyProducts.setVisibility(View.VISIBLE);

            loadingProgressBar.setVisibility(View.GONE);
            restaurantWrapper.setVisibility(View.VISIBLE);

        } catch (JSONException je) {
            Log.e("MAINACTIVITY", je.getMessage());
            Toast.makeText(this, je.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void setViews(String restaurantName, String restaurantAddress, String restaurantPhone, float restaurantMinPrice, String restaurantImgSrc) {
        restaurantTitleTv.setText(restaurantName);
        restaurantAddressTv.setText(restaurantAddress);
        restaurantPhoneTv.setText(restaurantPhone);
        restaurantMinPriceTv.setText(String.valueOf(restaurantMinPrice));
        Glide.with(this).load(restaurantImgSrc).into(restaurantImg);

        progressBar.setMax((int) restaurantMinPrice * 100);
    }
}
