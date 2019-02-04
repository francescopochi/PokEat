package com.example.pokeat.ui.adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.pokeat.R;
import com.example.pokeat.datamodels.Restaurant;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RestaurantAdapter extends RecyclerView.Adapter {
    LayoutInflater inflater;
    private ArrayList<Restaurant> data;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_restaurant, viewGroup, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        RestaurantViewHolder vh = (RestaurantViewHolder) viewHolder;
        vh.restaurantName.setText(data.get(pos).nome);
        vh.restaurantAddress.setText(data.get(pos).indirizzo);
        vh.restaurantMinPrice.setText(data.get(pos).importoMin);
        vh.restaurantPhone.setText(data.get(pos).numTelefono);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{

        public TextView restaurantName, restaurantAddress, restaurantMinPrice, restaurantPhone;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.name_tv);
            restaurantAddress = itemView.findViewById(R.id.address_tv);
            restaurantMinPrice = itemView.findViewById(R.id.minimumPrice_tv);
            restaurantPhone = itemView.findViewById(R.id.phone_tv);
        }
    }
}
