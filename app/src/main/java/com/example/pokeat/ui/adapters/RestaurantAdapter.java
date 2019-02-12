package com.example.pokeat.ui.adapters;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokeat.R;
import com.example.pokeat.datamodels.Product;
import com.example.pokeat.datamodels.Restaurant;
import com.example.pokeat.ui.activities.LoginActivity;
import com.example.pokeat.ui.activities.RegisterActivity;
import com.example.pokeat.ui.activities.ShopActivity;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RestaurantAdapter extends RecyclerView.Adapter{
    LayoutInflater inflater;

    private ArrayList<Restaurant> data;
    private static boolean isGrid;

    public static boolean getIsGrid() {
        return isGrid;
    }

    public static void setGrid(boolean isGrid){
        RestaurantAdapter.isGrid = isGrid;
    }

    public static void switchGrid() {
        isGrid = !isGrid;
    }

    public RestaurantAdapter(Context context, ArrayList<Restaurant> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int layout = isGrid ? R.layout.item_restaurant_grid : R.layout.item_restaurant;
        View view = inflater.inflate(layout, viewGroup, false);

        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        RestaurantViewHolder vh = (RestaurantViewHolder) viewHolder;
        vh.restaurantName.setText(data.get(pos).getNome());
        vh.restaurantAddress.setText(data.get(pos).getIndirizzo());
        vh.restaurantMinPrice.setText(data.get(pos).getImportoMin());
        vh.restaurantPhone.setText(data.get(pos).getNumTelefono());
        vh.restaurantImage.setImageResource(data.get(pos).getImage());

        vh.imgSrc = data.get(pos).getImage(); 
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Restaurant> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        public TextView restaurantName, restaurantAddress, restaurantMinPrice, restaurantPhone;
        public ImageView restaurantImage;
        public Button vediMenuBtn;
        public int imgSrc;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.name_tv);
            restaurantAddress = itemView.findViewById(R.id.address_tv);
            restaurantMinPrice = itemView.findViewById(R.id.minimumPrice_tv);
            restaurantPhone = itemView.findViewById(R.id.phone_tv);
            restaurantImage = itemView.findViewById(R.id.image_iv);
            vediMenuBtn = itemView.findViewById(R.id.vediMenu_btn);

            vediMenuBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.vediMenu_btn){
                Intent intent = new Intent(v.getContext(), ShopActivity.class);
                intent.putExtra("restaurant_name", restaurantName.getText().toString());
                intent.putExtra("restaurant_address", restaurantAddress.getText().toString());
                intent.putExtra("restaurant_min_price", restaurantMinPrice.getText().toString());
                intent.putExtra("restaurant_phone", restaurantPhone.getText().toString());
                intent.putExtra("restaurant_img_src", imgSrc);

                v.getContext().startActivity(intent);
            }
        }
    }

}
