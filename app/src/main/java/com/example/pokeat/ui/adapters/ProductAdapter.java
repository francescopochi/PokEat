package com.example.pokeat.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pokeat.R;
import com.example.pokeat.datamodels.Product;
import com.example.pokeat.ui.activities.ShopActivity;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter{
    LayoutInflater inflater;

    private ArrayList<Product> data;

    public ProductAdapter(Context context, ArrayList<Product> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.item_product, viewGroup, false);
        return new ProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        ProductAdapter.ProductViewHolder vh = (ProductAdapter.ProductViewHolder) viewHolder;

        vh.foodName.setText(data.get(pos).getNome());
        vh.foodPrice.setText("" + data.get(pos).getPrezzo());
        vh.foodQuantity.setText("" + data.get(pos).getQuantita());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public TextView foodName, foodPrice, foodQuantity;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName_tv);
            foodPrice = itemView.findViewById(R.id.foodPrice_tv);
            foodQuantity = itemView.findViewById(R.id.foodQuantity_tv);
        }
    }
}
