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
        //Binding UI
        final Product productDetails = productList.get(position);
        holder.tvProductName.setText(productDetails.getProductName());
        holder.tvProductPrice.setText(String.valueOf(productDetails.getProductCost()*productDetails.getUnitStock()));
        Glide.with(holder.imgvProductImage.getContext()).load(productDetails.getImageUrl()).into(holder.imgvProductImage);

        holder.tvQuantity.setText("Quantity:" + productDetails.getUnitStock());
        holder.etRemoveQuantity.setText(String.valueOf(productDetails.getUnitStock()));

        holder.btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityToRemove = Integer.parseInt(holder.etRemoveQuantity.getText().toString());
                iAdapterCommunicator.deleteItem(position, quantityToRemove);
            }
        });

        holder.btBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new SessionManagement(mContext);
                if(session.isLoggedIn()){
                    HashMap<String, String> user = session.getUserDetails();
                    String userEmail = user.get(SessionManagement.KEY_EMAIL);
                    String userId = user.get(SessionManagement.KEY_ID);
                    OrderInterface orderApi = MainController.getInstance().getClientForOrder().create(OrderInterface.class);
                    final ProgressDialog progressDialog = new ProgressDialog(mContext);
                    progressDialog.show();
                    Call<OrderModel> call = orderApi.placeOrder(userEmail, productDetails.getImageUrl(), productDetails.getProductId(), userId, productDetails.getProductName(), productDetails.getProductCost()*productDetails.getUnitStock(), productDetails.getUnitStock());
                    call.enqueue(new Callback<OrderModel>() {
                        @Override
                        public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                            if (200 == response.code()) {
                                progressDialog.dismiss();
                                Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                                iAdapterCommunicator.deleteItem(position, productDetails.getUnitStock());
                            }
                        }
                        @Override
                        public void onFailure(Call<OrderModel> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "Please try again.", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(loginIntent);
                }
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
        Button btBuyNow;
        Button btRemove;
        TextView tvQuantity;
        EditText etRemoveQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            etRemoveQuantity = itemView.findViewById(R.id.et_remove_quantity);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            imgvProductImage = itemView.findViewById(R.id.imgv_product_image);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            btBuyNow = itemView.findViewById(R.id.bt_buy_now);
            btRemove = itemView.findViewById(R.id.bt_remove);
        }
    }

    public static interface IAdapterCommunicator {
        void deleteItem(int position, int quantityToRemove);

    }
}