package com.example.achint.ecommerce.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.achint.ecommerce.Controller.MainController;
import com.example.achint.ecommerce.Interface.OrderInterface;
import com.example.achint.ecommerce.Model.OrderModel;
import com.example.achint.ecommerce.Model.Product;
import com.example.achint.ecommerce.R;
import com.example.achint.ecommerce.Sessions.SessionManagement;
import com.example.achint.ecommerce.View.LoginActivity;
import com.example.achint.ecommerce.View.ProductActivity;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Product> productList;
    private IAdapterCommunicator iAdapterCommunicator;
    private Context mContext;
    SessionManagement session;
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
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Product productDetails = productList.get(position);
        holder.tvProductName.setText(productDetails.getProductName());
        holder.tvProductPrice.setText(String.valueOf(productDetails.getProductCost()*productDetails.getUnitStock()) );
        holder.tvQuantity.setText("Quantity : "+productDetails.getUnitStock());
        holder.etAddQuantity.setText(""+0);
//        holder.etRemoveQuantity.setText(""+productDetails.getProductQuantity());

        Glide.with(holder.imgvProductImage.getContext()).
                load(productDetails.getImageUrl()).into(holder.imgvProductImage);

        holder.btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityToRemove = productDetails.getUnitStock();//Integer.parseInt(holder.etRemoveQuantity.getText().toString());

                iAdapterCommunicator.deleteItem(position,quantityToRemove);
            }
        });

        holder.btAddQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityToAdd = Integer.parseInt(holder.etAddQuantity.getText().toString());
                Product productToAdd = productList.get(holder.getAdapterPosition());
                iAdapterCommunicator.updateProductQuantity(holder.getAdapterPosition(),
                        productToAdd.getProductId(), quantityToAdd,productToAdd.getUserId(),
                        productToAdd.getImageUrl(), productToAdd.getProductCost(),
                        productToAdd.getProductName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName;
        ImageView imgvProductImage;
        TextView tvProductPrice;
        EditText etAddQuantity;
        Button btAddQuantity;
        Button btRemove;
        TextView tvQuantity;
        public ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            imgvProductImage = itemView.findViewById(R.id.imgv_product_image);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            btAddQuantity = itemView.findViewById(R.id.bt_add_quantity);
            btRemove = itemView.findViewById(R.id.bt_remove);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            etAddQuantity = itemView.findViewById(R.id.et_add_quantity);
        }
    }

    public static interface IAdapterCommunicator {
        void deleteItem(int position, int quantityToRemove);
        void updateProductQuantity(int position, String productId,  int unitStock,
                                   String userId, String imageUrl,
                                   double productCost,String productName);

    }
}