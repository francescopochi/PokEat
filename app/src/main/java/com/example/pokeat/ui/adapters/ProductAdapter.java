package com.example.pokeat.ui.adapters;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokeat.R;
import com.example.pokeat.datamodels.Product;
import com.example.pokeat.ui.activities.ShopActivity;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter{
    LayoutInflater inflater;
    private ArrayList<Product> data;

    public ArrayList<Product> getData() {
        return data;
    }

    public ProductAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
    }

    public interface OnQuanityChangedListener {
        void onChange(float price);
    }

    public OnQuanityChangedListener getOnQuanityChangedListener() {
        return onQuanityChangedListener;
    }

    public void setOnQuanityChangedListener(OnQuanityChangedListener onQuanityChangedListener) {
        this.onQuanityChangedListener = onQuanityChangedListener;
    }

    private OnQuanityChangedListener onQuanityChangedListener;

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

    public void setData(ArrayList<Product> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        public TextView foodName, foodPrice, foodQuantity;
        public Button plusBtn, minusBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName_tv);
            foodPrice = itemView.findViewById(R.id.foodPrice_tv);
            foodQuantity = itemView.findViewById(R.id.foodQuantity_tv);

            plusBtn = itemView.findViewById(R.id.quantityPlus_btn);
            minusBtn = itemView.findViewById(R.id.quantityMinus_btn);
            plusBtn.setOnClickListener(this);
            minusBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Product product = data.get(getAdapterPosition());

            if (view.getId() == R.id.quantityPlus_btn) {
                product.increaseQuantity();
                notifyItemChanged(getAdapterPosition());
                onQuanityChangedListener.onChange(product.getPrezzo());
            } else if (view.getId() == R.id.quantityMinus_btn) {

                if(product.getQuantita()>0){
                    product.decreaseQuantity();
                    notifyItemChanged(getAdapterPosition());
                    onQuanityChangedListener.onChange(product.getPrezzo() * -1);
                } else {
                    Toast.makeText(view.getContext(), "aggiungi almeno un elemento ", Toast.LENGTH_LONG);
                }


            }
        }

    }
}
