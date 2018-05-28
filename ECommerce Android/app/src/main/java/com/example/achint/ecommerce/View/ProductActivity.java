package com.example.achint.ecommerce.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.achint.ecommerce.Adapter.OrderAdapter;
import com.example.achint.ecommerce.Controller.MainController;
import com.example.achint.ecommerce.Interface.CartInterface;
import com.example.achint.ecommerce.Interface.OrderInterface;
import com.example.achint.ecommerce.Model.OrderModel;
import com.example.achint.ecommerce.R;
import com.example.achint.ecommerce.Sessions.SessionManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  ProductActivity extends AppCompatActivity {

    private Button buyButton;
    private Button addToCartButton, productDesc;
    private OrderInterface orderApi;
    private RecyclerView orderRecycler;
    private List<OrderModel> productList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private String pId;
    private String merchantId;
    private int pCost;
    SessionManagement session;
    String pName, pImage, pMerchant, pDesc;
    String productUrl;
    private int pQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView productName = findViewById(R.id.product_name);
        ImageView productImage = findViewById(R.id.product_image);
        TextView productPrice = findViewById(R.id.product_price);

        Bundle products = getIntent().getExtras();
        pName = products.getString("productName");
        pCost = products.getInt("productPrice");
        pImage = products.getString("productImage");
        pDesc = products.getString("productDesc");
        int pRating = products.getInt("productRating");
        pId = products.getString("productId");
        merchantId = products.getString("merchantId");
        pMerchant = products.getString("productMerchant");
        pQuantity = products.getInt("productQuantity");
        productUrl = products.getString("productImage");


        productName.setText(pName);
        Glide.with(productImage).load(productUrl).into(productImage);
        productPrice.setText("Rs."+pCost);

        buyButton = findViewById(R.id.buy_btn);
        addToCartButton = findViewById(R.id.add_to_cart_btn);
        productDesc = findViewById(R.id.product_desc);

        productDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog fbDialogue = new Dialog(ProductActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                fbDialogue.setContentView(R.layout.activity_product_desc);
                TextView productName = fbDialogue.findViewById(R.id.product_name);
                TextView merchantName = fbDialogue.findViewById(R.id.merchant_name);
                TextView productPrice = fbDialogue.findViewById(R.id.product_price);
                TextView productDesc = fbDialogue.findViewById(R.id.product_desc);

                productName.setText(pName);
                merchantName.setText(pMerchant);
                productPrice.setText(String.valueOf(pCost));
                productDesc.setText(pDesc);

                fbDialogue.setCancelable(true);
                fbDialogue.show();
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new SessionManagement(getApplicationContext());
                if(session.isLoggedIn()){
                    HashMap<String, String> user = session.getUserDetails();
                    String userEmail = user.get(SessionManagement.KEY_EMAIL);
                    String userId = user.get(SessionManagement.KEY_ID);
                    OrderInterface orderApi = MainController.getInstance().getClientForOrder().create(OrderInterface.class);
                    final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
                    progressDialog.show();
                    Call<OrderModel> call = orderApi.placeOrder(userEmail, productUrl, pId, userId, merchantId, pCost);
                    call.enqueue(new Callback<OrderModel>() {
                        @Override
                        public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                            if (200 == response.code()) {
                                progressDialog.dismiss();
                                Toast.makeText(ProductActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderModel> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(ProductActivity.this, "Please try again.", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Intent loginIntent = new Intent(ProductActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new SessionManagement(getApplicationContext());
                if(session.isLoggedIn()){
                    HashMap<String, String> user = session.getUserDetails();
                    String userEmail = user.get(SessionManagement.KEY_EMAIL);
                    String userId = user.get(SessionManagement.KEY_ID);
                    CartInterface cartApi = MainController.getInstance().getClientForCart().create(CartInterface.class);
                    final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
                    progressDialog.show();
                    Call<Boolean> call = cartApi.addToCart(pId, pQuantity, userId, pImage, pCost, pName);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (200 == response.code()) {
                                progressDialog.dismiss();
                                Toast.makeText(ProductActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(ProductActivity.this, "Please try again.", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Intent loginIntent = new Intent(ProductActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_launcher_drawer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if(id==R.id){
//            Toast.makeText(ProductActivity.this, "Button Clciked", Toast.LENGTH_SHORT).show();
//        }
        return super.onOptionsItemSelected(item);
    }
}
