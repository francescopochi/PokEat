package com.example.pokeat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pokeat.R;
import com.example.pokeat.datamodels.Order;
import com.example.pokeat.datamodels.Product;
import com.example.pokeat.ui.adapters.OrderProductsAdapter;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, OrderProductsAdapter.onItemRemovedListener {

    private TextView restaturantTv, totalTv;
    private RecyclerView productRv;
    private Button payBtn;
    private LinearLayoutManager layoutManager;
    private OrderProductsAdapter adapter;
    private Order order;
    private float total = 0;
    private Intent intent;
    private String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        intent = getIntent();
        restaurantName = intent.getStringExtra("restaurant_name");

        restaturantTv = findViewById(R.id.restaurant_name);
        totalTv = findViewById(R.id.checkout_total);
        productRv = findViewById(R.id.product_rv);
        payBtn = findViewById(R.id.pay_btn);
        order = getOrder();
        total = order.getTotal();

        layoutManager = new LinearLayoutManager(this);
        productRv.setLayoutManager(layoutManager);
        adapter = new OrderProductsAdapter(this, getProducts());
        adapter.setOnItemRemovedListener(this);
        productRv.setAdapter(adapter);

        payBtn.setOnClickListener(this);
        bindData();
    }

    private void bindData() {
        restaturantTv.setText(restaurantName);
        totalTv.setText(String.valueOf(order.getTotal()));
    }

    private Order getOrder() {
        Order order = new Order();
        order.setProducts(getProducts());

        for (int i = 0; i < getProducts().size(); i++) {
            total += getProducts().get(i).getSubtotal();
        }

        order.setTotal(total);

        return order;
    }

    private ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("McMenu", 7, 1));
        products.add(new Product("Hamburger", 4, 2));
        products.add(new Product("Insalata", 2, 1));
        return products;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pay_btn) {
            /* effettuare post :
            restaurant: <id>
            user : <id>
            amount : <number>
            products : JSONArray of JSONProducts
            */
        }
    }

    @Override
    public void onItemRemoved(float subtotal) {
        updateTotal(subtotal);
    }

    private void updateTotal(float subtotal) {
        if (total == 0)
            return;

        total -= subtotal;
        totalTv.setText(String.valueOf(total));
    }
}

