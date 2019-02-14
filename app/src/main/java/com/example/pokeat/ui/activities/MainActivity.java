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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.Request;
import com.example.pokeat.R;
import com.example.pokeat.datamodels.Product;
import com.example.pokeat.datamodels.Restaurant;
import com.example.pokeat.services.RestController;
import com.example.pokeat.ui.adapters.RestaurantAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    RecyclerView restaurantRV;
    RecyclerView.LayoutManager layoutManager;
    RestaurantAdapter adapter;
    ArrayList<Restaurant> restaurantsArrayList = new ArrayList<>();
    private RestController restController;
    private static final String preferencesFile = "com.example.pokeat.mypreferences", preferenceKEY = "view_mode";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantRV = findViewById(R.id.places_rv);

        // Otteniamo eventuali dati di preferences
        sharedPreferences = getSharedPreferences(preferencesFile, MODE_PRIVATE);
        RestaurantAdapter.setGrid(sharedPreferences.getBoolean(preferenceKEY, false));

        // Creiamo LayourManager e Adapter
        if (RestaurantAdapter.getIsGrid()) {
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }
        adapter = new RestaurantAdapter(this);

        // Colleghiamo al RecycleView il suo layoutManager e Adapter
        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);

        restController = new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT, this, this);
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

    @Override
    public void onResponse(String response) {
        try{
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++){
                Restaurant restaurant = new Restaurant(jsonArray.getJSONObject(i));
                restaurantsArrayList.add(restaurant);
            }
            adapter.setData(restaurantsArrayList);

        } catch (JSONException je){
            Log.e("MAINACTIVITY", je.getMessage());
            Toast.makeText(this, je.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("MAINACTIVITY", error.getMessage());
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
