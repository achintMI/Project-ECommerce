package com.example.achint.ecommerce.View;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class ProductActivity extends AppCompatActivity {

    private Button buyButton;
    private Button addToCartButton;
    private OrderInterface orderApi;
    private RecyclerView orderRecycler;
    private List<OrderModel> productList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private String pId;
    private String merchantId;
    private int pCost;
    SessionManagement session;


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
        String pName = products.getString("productName");
        pCost = products.getInt("productPrice");
        String pImage = products.getString("productImage");
        String pDesc = products.getString("productDesc");
        int pRating = products.getInt("productRating");
        pId = products.getString("productId");
        merchantId = products.getString("merchantId");
        final int pQuantity = products.getInt("productQuantity");
        String productUrl = products.getString("productImage");


        productName.setText(pName);
        Glide.with(productImage).load("https://images.pexels.com/photos/19090/pexels-photo.jpg?cs=srgb&dl=fashion-footwear-shoes-19090.jpg").into(productImage);
        productPrice.setText("Rs."+pCost);

        buyButton = findViewById(R.id.buy_btn);
        addToCartButton = findViewById(R.id.add_to_cart_btn);



        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new SessionManagement(getApplicationContext());
                System.out.println(session.isLoggedIn());
                if(session.isLoggedIn()){
                    HashMap<String, String> user = session.getUserDetails();
                    String userEmail = user.get(SessionManagement.KEY_EMAIL);


                    OrderInterface orderApi = MainController.getInstance().getClientForOrder().create(OrderInterface.class);
                    final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
                    progressDialog.show();
                    Call<OrderModel> call = orderApi.placeOrder(userEmail, "url", pId, "1", merchantId, pCost);
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
                Intent cartIntent = new Intent(ProductActivity.this, CartActivity.class);
                cartIntent.putExtra("productId", pId);
                cartIntent.putExtra("productQuantity", pQuantity);
                cartIntent.putExtra("productUser", "1");
                startActivity(cartIntent);
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
