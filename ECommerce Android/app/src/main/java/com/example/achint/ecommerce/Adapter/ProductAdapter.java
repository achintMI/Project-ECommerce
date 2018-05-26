package com.example.achint.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.achint.ecommerce.Model.ProductData;
import com.example.achint.ecommerce.View.ProductActivity;
import com.example.achint.ecommerce.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {


    private List<ProductData> mProducts;
    private Context mContext;
    private IAdapterCommunicator iAdapterCommunicator;

    public ProductAdapter(List<ProductData> productList, IAdapterCommunicator homeActivity) {
        mProducts = productList;
        this.iAdapterCommunicator = homeActivity;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ProductData product  = mProducts.get(position);
        holder.productName.setText("Name: "+product.getProductName());
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //  Toast.makeText(mContext, "position clicked" + product.getProductName(), Toast.LENGTH_SHORT).show();
                Intent productDetails = new Intent(mContext, ProductActivity.class);
                productDetails.putExtra("productName", product.getProductName());
                productDetails.putExtra("productId", product.getProductId());
                productDetails.putExtra("productImage", product.getProductImageUrl());
                productDetails.putExtra("productPrice", product.getProductPrice());
                productDetails.putExtra("productDesc", product.getProductDescription());
                productDetails.putExtra("productRating", product.getMerchantRating());
                productDetails.putExtra("productQuantity", product.getUnitStock());
                productDetails.putExtra("merchantId", product.getMerchantId());
                mContext.startActivity(productDetails);
            }
        });
        Glide.with(holder.productImage.getContext()).load("https://images.pexels.com/photos/19090/pexels-photo.jpg?cs=srgb&dl=fashion-footwear-shoes-19090.jpg").into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        ImageView productImage;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
        }
    }

    public interface IAdapterCommunicator {
        void deleteItem(int position);
    }
}
