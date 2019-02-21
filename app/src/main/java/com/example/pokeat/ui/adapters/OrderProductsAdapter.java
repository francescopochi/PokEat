package com.example.pokeat.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.pokeat.R;
import com.example.pokeat.datamodels.Product;

import java.util.ArrayList;

public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.OrderProductViewHolder>{

    private ArrayList<Product> dataSet;
    private Context context;
    private LayoutInflater inflater;

    public  OrderProductsAdapter(Context context, ArrayList<Product> dataSet){
        this.dataSet = dataSet;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public interface onItemRemovedListener{
        void onItemRemoved(float subtotal);
    }

    private onItemRemovedListener onItemRemovedListener;

    public OrderProductsAdapter.onItemRemovedListener getOnItemRemovedListener() {
        return onItemRemovedListener;
    }

    public void setOnItemRemovedListener(OrderProductsAdapter.onItemRemovedListener onItemRemovedListener) {
        this.onItemRemovedListener = onItemRemovedListener;
    }

    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderProductViewHolder(inflater.inflate(R.layout.item_order,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder orderProductViewHolder, int i) {
        Product product = dataSet.get(i);

        orderProductViewHolder.productNameTv.setText(dataSet.get(i).getNome());
        orderProductViewHolder.quantityTv.setText(String.valueOf(dataSet.get(i).getQuantita()));
        orderProductViewHolder.subtotalTv.setText(String.valueOf(dataSet.get(i).getSubtotal()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private void removeItem(int index){
        dataSet.remove(index);
        notifyItemRemoved(index);
    }

    public class OrderProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView quantityTv,productNameTv,subtotalTv;
        public ImageButton removeBtn;


        public OrderProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTv = itemView.findViewById(R.id.item_name);
            subtotalTv = itemView.findViewById(R.id.subtotal);
            quantityTv = itemView.findViewById(R.id.item_qnt);
            removeBtn = itemView.findViewById(R.id.remove_item_button);
            removeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            AlertDialog.Builder removeAlert = new AlertDialog.Builder(context);
            removeAlert
                    .setTitle("Conferma eliminazione")
                    .setMessage("Sei sicuro di voler eliminare l'elemento selezionato?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onItemRemovedListener.onItemRemoved(dataSet.get(getAdapterPosition()).getSubtotal());
                            removeItem(getAdapterPosition());
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .create()
                    .show();
        }
    }
}
