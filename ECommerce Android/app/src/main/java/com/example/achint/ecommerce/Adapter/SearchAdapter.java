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
import com.example.achint.ecommerce.Model.Search;
import com.example.achint.ecommerce.R;
import com.example.achint.ecommerce.View.ProductActivity;
import com.example.achint.ecommerce.View.SearchActivity;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Search> searchResults;
    private Context mContext;
    private IAdapterCommunicator iAdapterCommunicator;

    public SearchAdapter(List<Search> searchList, IAdapterCommunicator searchActivity) {
        searchResults = searchList;
        this.iAdapterCommunicator = searchActivity;
    }


    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search, parent, false);
        mContext = parent.getContext();
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        final Search product  = searchResults.get(position);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText("Rs." + product.getProductPrice());
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(mContext, "position clicked" + product.getProductName(), Toast.LENGTH_SHORT).show();
                Intent productDetails = new Intent(mContext, ProductActivity.class);
                productDetails.putExtra("productName", product.getProductName());
//                productDetails.putExtra("productId", product.);
                productDetails.putExtra("productImage", product.getImgSrc());
                productDetails.putExtra("productPrice", product.getProductPrice());
//                productDetails.putExtra("productDesc", product);
                productDetails.putExtra("productRating", product.getMerchantRating());
                productDetails.putExtra("productQuantity", product.getStock());
//                productDetails.putExtra("merchantId", product.());
                mContext.startActivity(productDetails);
            }
        });
        Glide.with(holder.productImage.getContext()).load(product.getImgSrc()).into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        ImageView productImage;
        TextView productPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
        }
    }

    public interface IAdapterCommunicator {
        void deleteItem(int position);
    }
}
