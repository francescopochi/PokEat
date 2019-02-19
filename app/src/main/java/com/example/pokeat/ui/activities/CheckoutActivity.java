package com.example.pokeat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.pokeat.R;
import com.example.pokeat.datamodels.Product;
import com.example.pokeat.ui.adapters.OrderProductsAdapter;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity implements OrderProductsAdapter.OnTotalChangedListener {

    @Override
    public void onChange(int id) {
        //order.productsArrayList.remove(id);
        //totalTv.setText("" + order.calcolaTotale());
    }

    Intent intent;
    TextView restaurantTitle, totalTv;
    RecyclerView.LayoutManager layoutManager;
    OrderProductsAdapter adapter;
    RecyclerView ordersRV;
    private float total = 0f;
    ArrayList<Product> productsArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        intent = getIntent();

        layoutManager = new LinearLayoutManager(this);
        adapter.setOnTotalChangedListener(this);

        ordersRV = findViewById(R.id.orders_rv);
        totalTv = findViewById(R.id.checkout_total);
        ordersRV.setLayoutManager(layoutManager);
        ordersRV.setAdapter(adapter);

        restaurantTitle = findViewById(R.id.restaurant_name_final);
        restaurantTitle.append(intent.getStringExtra("restaurant_name"));
    }

}
