package com.example.pokeat.ui.activities;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.pokeat.R;
import com.example.pokeat.datamodels.Restaurant;
import com.example.pokeat.ui.adapters.RestaurantAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView restaurantRV;
    RecyclerView.LayoutManager layoutManager;
    RestaurantAdapter adapter;
    ArrayList<Restaurant> arrayList;

    private static final String preferencesFile = "com.example.pokeat.mypreferences";
    private static final String preferenceKEY = "view_mode";
    SharedPreferences sharedPreferences;

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

        sharedPreferences = getSharedPreferences(preferencesFile, MODE_PRIVATE);
        RestaurantAdapter.setGrid(sharedPreferences.getBoolean(preferenceKEY, false));

        // Creiamo LayourManager e Adapter
        if(RestaurantAdapter.getIsGrid()){
            layoutManager = new GridLayoutManager(this,2);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }
        adapter = new RestaurantAdapter(this, getData());

        // Colleghiamo al RecycleView il suo layoutManager e Adapter
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
        menu.findItem(R.id.view_mode).setIcon(RestaurantAdapter.getIsGrid() ? R.drawable.ic_grid_off_white_24dp : R.drawable.ic_grid_on_white_24dp);
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
        } else if(item.getItemId() == R.id.view_mode){
            setLayoutManager();
            item.setIcon(RestaurantAdapter.getIsGrid() ? R.drawable.ic_grid_off_white_24dp : R.drawable.ic_grid_on_white_24dp);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLayoutManager(){
        RestaurantAdapter.switchGrid();
        layoutManager = RestaurantAdapter.getIsGrid() ? new GridLayoutManager(this,2): new LinearLayoutManager(this);
        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);

        saveLayoutManager();
    }

    public void saveLayoutManager(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(preferenceKEY, RestaurantAdapter.getIsGrid());
        editor.apply();
    }
}
