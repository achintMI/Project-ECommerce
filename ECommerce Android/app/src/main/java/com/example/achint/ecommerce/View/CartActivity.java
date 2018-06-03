package com.example.achint.ecommerce.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.achint.ecommerce.Controller.MainController;
import com.example.achint.ecommerce.Interface.CartInterface;
import com.example.achint.ecommerce.Interface.OrderInterface;
import com.example.achint.ecommerce.Interface.ProductInteface;
import com.example.achint.ecommerce.Model.OrderModel;
import com.example.achint.ecommerce.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.achint.ecommerce.Adapter.CartAdapter;
import com.example.achint.ecommerce.Model.Product;
import com.example.achint.ecommerce.Sessions.SessionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Math.abs;

public class CartActivity extends AppCompatActivity implements CartAdapter.IAdapterCommunicator {

    private CartInterface mIProductApi;
    private ProductInteface productApi;
    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;
    private List<Product> mProductList = new ArrayList<>();
    private Button btCheckout;
    //    private Button btAddQuantity;
    private TextView tvCartTotal;
    private String userId;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_cart);

        Bundle intent = getIntent().getExtras();
        userId = intent.getString("userId");
        mRecyclerView = findViewById(R.id.rv_cart_product_list);
        mCartAdapter = new CartAdapter(mProductList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager((this)));
        mRecyclerView.setAdapter(mCartAdapter);
        mIProductApi = MainController.getInstance().getClientForCart().create(CartInterface.class);

        listCartItems();
        tvCartTotal = findViewById(R.id.tv_cart_total);
        cartTotal();
        btCheckout = findViewById(R.id.bt_checkout);
        productApi = MainController.getInstance().getClientForProducts().create(ProductInteface.class);
        btCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProductList.size() > 0) {
                    for (int productCounter = 0; productCounter < mProductList.size(); productCounter++) {
                        Call<Integer> call = productApi.getStockCount(mProductList.get(productCounter).getProductId());
                        final int finalProductCounter = productCounter;
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.code() == 200) {
                                    Integer productCount = response.body();
                                    if (productCount < mProductList.get(finalProductCounter).getUnitStock()) {
                                        Toast.makeText(CartActivity.this, "Out Of Stock", Toast.LENGTH_LONG).show();
                                    } else {
                                        placeOrder(mProductList.get(finalProductCounter).getImageUrl(),
                                                mProductList.get(finalProductCounter).getProductName(),
                                                mProductList.get(finalProductCounter).getProductId(),
                                                mProductList.get(finalProductCounter).getUnitStock(),
                                                mProductList.get(finalProductCounter).getProductCost());
                                        removeItemFromCart(mProductList.get(finalProductCounter).getProductId());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
//                                progressDialog.dismiss();
                                Toast.makeText(CartActivity.this, "Failed to Show Cart", Toast.LENGTH_LONG).show();
                            }

                        });
                    }
                }

            }
        });

    }

    public void cartTotal() {
        int totalCost = 0;
        for(Product cartProducts : mProductList) {
            totalCost += (cartProducts.getProductCost()*cartProducts.getUnitStock());
        }
        tvCartTotal.setText(String.valueOf(totalCost));
    }

    public void removeItemFromCart(String productId) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        Call<Boolean> call = mIProductApi.removeItemFromCart(productId, userId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                progressDialog.dismiss();
                if (response.code() == 200 && response.body()) {
                    mProductList.clear();
                    mCartAdapter.notifyDataSetChanged();
                    Toast.makeText(CartActivity.this, "Successfully Checked Out " +
                            "Your Cart", Toast.LENGTH_LONG).show();
                    if (mProductList.size() == 0) {
                        Toast.makeText(CartActivity.this, "Your Cart Is Empty", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, "Failed to Checkout Cart",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void listCartItems() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        Call<Product[]> call = mIProductApi.cartListItems(userId);
        call.enqueue(new Callback<Product[]>() {
            @Override
            public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                if (response.code() == 200) {
                    mProductList.addAll(Arrays.asList(response.body()));
                    mCartAdapter.notifyDataSetChanged();
                    cartTotal();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Product[]> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, "Failed to Show Cart", Toast.LENGTH_LONG).show();
            }

        });
    }


    @Override
    public void deleteItem(final int position, final int quantityToRemove) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        Product product = mProductList.get(position);
        Call<Boolean> call;

        if (quantityToRemove >= product.getUnitStock()) {
            call = mIProductApi.removeFromCart(product.getProductId(),
                    product.getUnitStock(), product.getUserId());

            mProductList.remove(position);
            mCartAdapter.notifyItemRemoved(position);
            mCartAdapter.notifyDataSetChanged();
            cartTotal();
        } else {
            int remainingQuantity = abs(quantityToRemove - product.getUnitStock());

            product.setUnitStock(remainingQuantity);
            mProductList.set(position, product);
            mCartAdapter.notifyItemChanged(position);
            mCartAdapter.notifyDataSetChanged();
            product = mProductList.get(position);
            int quantityToBeRemoved = product.getUnitStock() - remainingQuantity;
            call = mIProductApi.removeFromCart(product.getProductId(),
                    quantityToRemove, product.getUserId());
        }
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                progressDialog.dismiss();
                if (response.code() == 200 && response.body() == true) {
                    Toast.makeText(CartActivity.this, "Successfully removed product from Cart", Toast.LENGTH_LONG).show();
                    if (mProductList.size() == 0) {
                        Toast.makeText(CartActivity.this, "Your Cart Is Empty", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, "Failed to remove product from Cart", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void placeOrder(String imageUrl, String productName, String productId, int stock, double cost) {
        session = new SessionManagement(CartActivity.this);
        if (session.isLoggedIn()) {
            HashMap<String, String> user = session.getUserDetails();
            String userEmail = user.get(SessionManagement.KEY_EMAIL);
            String userId = user.get(SessionManagement.KEY_ID);
            OrderInterface orderApi = MainController.getInstance().getClientForOrder().create(OrderInterface.class);
            final ProgressDialog progressDialog = new ProgressDialog(CartActivity.this);
            progressDialog.show();
            Call<OrderModel> call = orderApi.placeOrder(userEmail, imageUrl, productId, userId, productName, cost * stock, stock);
            call.enqueue(new Callback<OrderModel>() {
                @Override
                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                    if (200 == response.code()) {
                        progressDialog.dismiss();
                        Toast.makeText(CartActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OrderModel> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(CartActivity.this, "Please try again.", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Intent loginIntent = new Intent(CartActivity.this, LoginActivity.class);
            CartActivity.this.startActivity(loginIntent);
        }
    }

    public void updateProductQuantity(final int position, final String productId,
                                      final int unitStock, final String userId,
                                      final String imageUrl, final double productCost,
                                      final String productName)
    {
        final ProgressDialog progressDialog = new ProgressDialog(CartActivity.this);
        progressDialog.show();

        Call<Boolean> callCartService = mIProductApi.addToCart(productId,
                unitStock,userId,imageUrl,
                productCost,productName);

        callCartService.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                progressDialog.dismiss();

                if (response.code() == 200 && response.body()) {
                    Toast.makeText(CartActivity.this, "Successfully Added " +
                            "product to Cart", Toast.LENGTH_LONG).show();

                    mProductList.get(position).setUnitStock((mProductList.get(position).getUnitStock())+unitStock);
                    mCartAdapter.notifyItemChanged(position);
                    mCartAdapter.notifyDataSetChanged();
                    cartTotal();
                }
                else
                {
                    Toast.makeText(CartActivity.this,"Received Different " +
                            "Response Code",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this,"Failed to add " +
                        "product to Cart ", Toast.LENGTH_LONG).show();
            }
        });
    }
}
