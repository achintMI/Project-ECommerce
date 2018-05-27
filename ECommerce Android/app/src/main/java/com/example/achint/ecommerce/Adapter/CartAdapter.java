package com.example.achint.ecommerce.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.achint.ecommerce.Model.Product;
import com.example.achint.ecommerce.R;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Product> productList;
    private IAdapterCommunicator iAdapterCommunicator;

    public CartAdapter(List<Product> mEmployees, IAdapterCommunicator iAdapterCommunicator) {
        this.productList = mEmployees;
        this.iAdapterCommunicator = iAdapterCommunicator;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart,
                        parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //Binding UI
        final Product productDetails = productList.get(position);
        holder.tvProductName.setText(productDetails.getProductName());

        holder.tvProductPrice.setText(productDetails.getProductPrice());

        Glide.with(holder.imgvProductImage.getContext()).load(R.drawable.test).into(holder.imgvProductImage);

        holder.btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iAdapterCommunicator.deleteItem(position);
            }
        });

        holder.btBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public int getItemCount() {
        // Set the list count
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //Declare the views

        TextView tvProductName;
        ImageView imgvProductImage;
        TextView tvProductPrice;
        Button btBuyNow;
        Button btRemove;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.tv_product_name);
            imgvProductImage = itemView.findViewById(R.id.imgv_product_image);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            btBuyNow = itemView.findViewById(R.id.bt_buy_now);
            btRemove = itemView.findViewById(R.id.bt_remove);
        }
    }

    public static interface IAdapterCommunicator {
        void deleteItem(int position);

    }
}