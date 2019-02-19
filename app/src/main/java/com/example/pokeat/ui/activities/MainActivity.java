package com.example.pokeat.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.pokeat.R;
import com.example.pokeat.datamodels.Restaurant;
import com.example.pokeat.services.RestController;
import com.example.pokeat.ui.SharedPreferencesUtils;
import com.example.pokeat.ui.adapters.RestaurantAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    RecyclerView restaurantRV;
    RecyclerView.LayoutManager layoutManager;
    RestaurantAdapter adapter;
    ArrayList<Restaurant> restaurantsArrayList = new ArrayList<>();
    private RestController restController;
    private static final String preferencesFile = "com.example.pokeat.mypreferences", preferenceKEY = "view_mode";
    private static final int LOGIN_REQUEST_CODE = 3001;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantRV = findViewById(R.id.places_rv);
        progressBar = findViewById(R.id.loading_progressbar);

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
        this.menu = menu;
        menu.findItem(R.id.view_mode).setIcon(RestaurantAdapter.getIsGrid() ? R.drawable.ic_grid_off_white_24dp : R.drawable.ic_grid_on_white_24dp);

        if (SharedPreferencesUtils.getStringValue(this, "jwt") != null) {
            menu.findItem(R.id.login_menu).setVisible(false);
            menu.findItem(R.id.logout_menu).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.login_menu) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
            return true;
        } else if (item.getItemId() == R.id.view_mode) {
            setLayoutManager();
            item.setIcon(RestaurantAdapter.getIsGrid() ? R.drawable.ic_grid_off_white_24dp : R.drawable.ic_grid_on_white_24dp);
            return true;
        } else if (item.getItemId() == R.id.logout_menu) {
            SharedPreferencesUtils.putValue(MainActivity.this, "jwt", null);
            item.setVisible(false);
            menu.findItem(R.id.login_menu).setVisible(true);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLayoutManager() {
        RestaurantAdapter.switchGrid();
        layoutManager = RestaurantAdapter.getIsGrid() ? new GridLayoutManager(this, 2) : new LinearLayoutManager(this);
        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);

        saveLayoutManager();
    }

    public void saveLayoutManager() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(preferenceKEY, RestaurantAdapter.getIsGrid());
        editor.apply();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                Restaurant restaurant = new Restaurant(jsonArray.getJSONObject(i));
                restaurantsArrayList.add(restaurant);
            }
            adapter.setData(restaurantsArrayList);
            progressBar.setVisibility(View.GONE);
            restaurantRV.setVisibility(View.VISIBLE);

        } catch (JSONException je) {
            Log.e("MAINACTIVITY", je.getMessage());
            Toast.makeText(this, je.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            SharedPreferencesUtils.putValue(this, "jwt", data.getStringExtra("jwt"));
            menu.findItem(R.id.login_menu).setVisible(false);
            menu.findItem(R.id.logout_menu).setVisible(true);
        }
    }
}
