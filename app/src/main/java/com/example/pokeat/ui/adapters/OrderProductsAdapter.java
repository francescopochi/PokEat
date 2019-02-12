package com.example.pokeat.ui.adapters;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.pokeat.R;
import com.example.pokeat.datamodels.Product;
import com.example.pokeat.ui.activities.CheckoutActivity;
import com.example.pokeat.ui.activities.MainActivity;

import java.util.ArrayList;

public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.OrderProductViewHolder>{

    private ArrayList<Product> dataSet;
    private Context context;
    private LayoutInflater inflater;
    public static float amountToRemove = 0f;
    private float total = 0f;

    public  OrderProductsAdapter(Context context, ArrayList<Product> dataSet){
        this.dataSet = dataSet;
        this.context = context;
        inflater = LayoutInflater.from(context);

        for(int i=0; i<dataSet.size(); i++){
            total += dataSet.get(i).getSubtotal();
        }
    }

    public interface OnTotalChangedListener {
        void onChange(int id);
    }

    public OrderProductsAdapter.OnTotalChangedListener getOnTotalChangedListener() {
        return onTotalChangedListener;
    }

    public void setOnTotalChangedListener(OrderProductsAdapter.OnTotalChangedListener OnTotalChangedListener) {
        this.onTotalChangedListener = OnTotalChangedListener;
    }

    private OrderProductsAdapter.OnTotalChangedListener onTotalChangedListener;


    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderProductViewHolder(inflater.inflate(R.layout.item_order,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder viewHolder, int i) {
        Product product = dataSet.get(i);
        OrderProductsAdapter.OrderProductViewHolder vh = viewHolder;

        vh.productNameTv.setText(product.getNome());
        vh.quantityTv.setText(String.valueOf(product.getQuantita()));
        vh.subtotalTv.setText(String.valueOf(product.getSubtotal()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class OrderProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView quantityTv,productNameTv,subtotalTv;
        public ImageButton removeBtn;

        public OrderProductViewHolder(@NonNull View itemView) {
            super(itemView);
            quantityTv = itemView.findViewById(R.id.item_qnt);
            productNameTv = itemView.findViewById(R.id.item_name);
            subtotalTv = itemView.findViewById(R.id.subtotal);

            removeBtn = itemView.findViewById(R.id.remove_item_button);
            removeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
            alertDialogBuilder.setTitle("Elimina elemento");
            alertDialogBuilder.setMessage("Sei sicuro di voler eliminare l'elemento dal carrello?")
                    .setCancelable(false)
                    .setPositiveButton("SI'", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dataSet.remove(getAdapterPosition());
                            onTotalChangedListener.onChange(getAdapterPosition());
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    }
}
