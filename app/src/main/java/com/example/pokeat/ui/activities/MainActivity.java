package com.example.pokeat.ui.activities;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.pokeat.R;
import com.example.pokeat.datamodels.Restaurant;
import com.example.pokeat.ui.adapters.RestaurantAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView restaurantRV;
    RecyclerView.LayoutManager layoutManager;
    RestaurantAdapter adapter;
    ArrayList<Restaurant> arrayList;

    Restaurant restaurant1 = new Restaurant("Bussola", "Via Roma, 13 ROMA", "10.00€", "0966932317", R.drawable.pizza_icon);
    Restaurant restaurant2 = new Restaurant("Mc Donald's", "Via Tiburtina, 79 ROMA", "12.00€", "09667687654", R.drawable.mcdonald_icon);
    Restaurant restaurant3 = new Restaurant("Burger King", "Via Garibaldi, 122 MILANO", "8.00€", "0765435678", R.drawable.burgerking_icon);
    Restaurant restaurant4 = new Restaurant("Starbucks", "Via Sandro Sandri, 79 MILANO", "9.00€", "09889897876", R.drawable.starbucks_icon);
    Restaurant restaurant5 = new Restaurant("La Gardenia", "Via Pietro Nenni, 12 REGGIO CALABRIA", "5.00€", "0966932395", R.drawable.pizza_icon);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantRV = findViewById(R.id.places_rv);

        // Creiamo LayourManager e Adapter
        layoutManager = new LinearLayoutManager(this);
        adapter = new RestaurantAdapter(this, getData());   // ci passo un arraylist gia pieno

        // Colleghiamo al RecycleView il suo layouyManager e Adapter
        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);
    }

    // Metodo per riempire l'arraylist
    private ArrayList<Restaurant> getData(){
        arrayList = new ArrayList<>();

        arrayList.add(restaurant1);
        arrayList.add(restaurant2);
        arrayList.add(restaurant3);
        arrayList.add(restaurant4);
        arrayList.add(restaurant5);


        return arrayList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.login_menu) {
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        } else if (item.getItemId() == R.id.checkout_menu) {
            startActivity(new Intent(this, CheckoutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
