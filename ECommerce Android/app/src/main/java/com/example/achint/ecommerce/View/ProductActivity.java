package com.example.achint.ecommerce.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.achint.ecommerce.Adapter.OrderAdapter;
import com.example.achint.ecommerce.Controller.MainController;
import com.example.achint.ecommerce.Interface.CartInterface;
import com.example.achint.ecommerce.Interface.OrderInterface;
import com.example.achint.ecommerce.Interface.ProductInteface;
import com.example.achint.ecommerce.Model.OrderModel;
import com.example.achint.ecommerce.Model.ProductData;
import com.example.achint.ecommerce.R;
import com.example.achint.ecommerce.Sessions.AlertDialogManager;
import com.example.achint.ecommerce.Sessions.SessionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  ProductActivity extends AppCompatActivity {

    private Button buyButton, addToCartButton, productDesc, merchantBtn;
    private OrderInterface orderApi;
    private ProductInteface productApi;
    private RecyclerView orderRecycler;
    private List<ProductData> productList = new ArrayList<ProductData>();
    AlertDialogManager alert = new AlertDialogManager();
    private OrderAdapter orderAdapter;
    private String pId;
    private String merchantId;
    private int pCost;
    SessionManagement session;
    String pName, pImage, pMerchant, pDesc;
    String productUrl;
    private int pQuantity = 0;
    private int productVal;
    String productTotalCount;
    TextView produtQuantity;
    private AlertDialog progressDialog;
    TextView productName, product_name, productPrice, productIdentity, merchantName, productDescription;
    ImageView productImage;
    RatingBar merchantRating;
    ImageView upButton, downButton;
    TextView outOfStock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productApi = MainController.getInstance().getClientForProducts().create(ProductInteface.class);

        Bundle products = getIntent().getExtras();
        int pRating = products.getInt("productRating");
        pId = products.getString("productId");

        getProductById(pId);

        outOfStock = findViewById(R.id.out_of_stock);
        buyButton = findViewById(R.id.buy_btn);
        addToCartButton = findViewById(R.id.add_to_cart_btn);
        merchantBtn = findViewById(R.id.merchant);
        upButton = findViewById(R.id.top_btn);
        downButton = findViewById(R.id.down_btn);




        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtQuantity = findViewById(R.id.quant);
                produtQuantity.setText(String.valueOf(Integer.parseInt(produtQuantity.getText().toString())+1));
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtQuantity = findViewById(R.id.quant);
                produtQuantity.setText(String.valueOf(Integer.parseInt(produtQuantity.getText().toString())-1));
            }
        });


        merchantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent merchantIntent = new Intent(ProductActivity.this, MerchantActivity.class);
                merchantIntent.putExtra("productName", pName);
                startActivity(merchantIntent);
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            //productTotalCount = produtQuantity.getText().toString();
            @Override
            public void onClick(View v) {
                productTotalCount = produtQuantity.getText().toString();
                if (pQuantity == 0) {
                    alert.showAlertDialog(ProductActivity.this, "Out Of Stock", "Please try some other item", false);
                } else {
                    if (productTotalCount.equals("")) {
                        productVal = 1;
                    } else {
                        productVal = Integer.parseInt(productTotalCount);
                        if (productVal == 0) {
                            productVal = 1;
                        }
                    }
                    if (productVal > pQuantity || productVal < 0) {
                        alert.showAlertDialog(ProductActivity.this, "Out of stock", "Please select quantity between 0 - " + pQuantity, false);
                    } else {
                        session = new SessionManagement(getApplicationContext());
                        if (session.isLoggedIn()) {
                            HashMap<String, String> user = session.getUserDetails();
                            String userEmail = user.get(SessionManagement.KEY_EMAIL);
                            String userId = user.get(SessionManagement.KEY_ID);
                            OrderInterface orderApi = MainController.getInstance().getClientForOrder().create(OrderInterface.class);
                            final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
                            progressDialog.show();
                            Call<OrderModel> call = orderApi.placeOrder(userEmail, productUrl, pId, userId, merchantId, pCost * productVal, productVal);
                            call.enqueue(new Callback<OrderModel>() {
                                @Override
                                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                                    if (200 == response.code()) {
                                        progressDialog.dismiss();
                                        alert.showAlertDialog(ProductActivity.this, "Congrats", "Order Placed", true);
                                        pQuantity = pQuantity - productVal;
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderModel> call, Throwable t) {
                                    progressDialog.dismiss();
                                    alert.showAlertDialog(ProductActivity.this, "Out of stock", "Please try again", false);
                                }
                            });
                            if(pQuantity<=0){
                               outOfStock.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Intent loginIntent = new Intent(ProductActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                            finish();
                        }
                    }
                }
            }
        });


        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productTotalCount = produtQuantity.getText().toString();
                if (productTotalCount.equals("")) {
                    productVal = 1;
                } else {
                    productVal = Integer.parseInt(productTotalCount);
                    if (productVal == 0) {
                        productVal = 1;
                    }
                }
                if (productVal > pQuantity || productVal < 0) {
                    alert.showAlertDialog(ProductActivity.this, "Out of stock", "Please select some other quantity", false);
                } else {
                    session = new SessionManagement(getApplicationContext());
                    if (session.isLoggedIn()) {
                        HashMap<String, String> user = session.getUserDetails();
                        String userEmail = user.get(SessionManagement.KEY_EMAIL);
                        String userId = user.get(SessionManagement.KEY_ID);
                        CartInterface cartApi = MainController.getInstance().getClientForCart().create(CartInterface.class);
                        final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
                        progressDialog.show();
                        Call<Boolean> call = cartApi.addToCart(pId, productVal, userId, pImage, pCost, pName);
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
                    } else {
                        Intent loginIntent = new Intent(ProductActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                }
            }
        });
    }

    private void getProductById(String productId) {
        progressDialog = new SpotsDialog(ProductActivity.this, R.style.Custom);
        progressDialog.show();
        Call<ProductData> call = productApi.getProductById(productId);
        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
                if (200 == response.code()) {
                    ProductData productDetails = response.body();
                    productName = findViewById(R.id.product_name);
                    productImage = findViewById(R.id.product_image);
                    productPrice = findViewById(R.id.product_price);
                    produtQuantity = findViewById(R.id.quant);
                    product_name = findViewById(R.id.productName);
                    //productIdentity = findViewById(R.id.productId);
                    merchantName = findViewById(R.id.merchantName);
                    merchantRating = findViewById(R.id.merchantRating);
                    productDescription = findViewById(R.id.productDesc);

                    pImage = productDetails.getProductImageUrl();
                    productUrl = productDetails.getProductImageUrl();
                    merchantId = productDetails.getMerchantId();
                    pId = productDetails.getProductId();
                    merchantId = productDetails.getMerchantId();
                    pCost = productDetails.getProductPrice();
                    pQuantity = productDetails.getUnitStock();
                    productVal = pQuantity;
                    pName = productDetails.getProductName();

                    productName.setText(productDetails.getProductName());
                    Glide.with(productImage).load(productDetails.getProductImageUrl()).into(productImage);
                    productPrice.setText("Rs."+productDetails.getProductPrice());
                    produtQuantity.setText(String.valueOf(productDetails.getUnitStock()));
                    product_name.setText(productDetails.getProductName());
                    //productIdentity.setText(productDetails.getProductId());
                    merchantName.setText(productDetails.getProductMerchant());
                    merchantRating.setRating((int)productDetails.getMerchantRating());
                    productDescription.setText(productDetails.getProductDescription());

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProductActivity.this, "Failed to fetch", Toast.LENGTH_LONG).show();
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
