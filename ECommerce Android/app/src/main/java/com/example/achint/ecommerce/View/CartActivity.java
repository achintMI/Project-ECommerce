package com.example.achint.ecommerce.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.achint.ecommerce.Controller.MainController;
import com.example.achint.ecommerce.Interface.CartInterface;
import com.example.achint.ecommerce.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class CartActivity extends AppCompatActivity implements CartAdapter.IAdapterCommunicator{

    private CartInterface mIProductApi;
    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;
    private List<Product> mProductList = new ArrayList<>();

    private String userId;

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

    }

    private void listCartItems()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        Call< Product[]> call = mIProductApi.cartListItems(userId);
        call.enqueue(new Callback<Product[]>() {
            @Override
            public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                if (response.code() == 200) {
                    mProductList.addAll(Arrays.asList(response.body()));
                    mCartAdapter.notifyDataSetChanged();
                    Toast.makeText(CartActivity.this, "Removed from Cart", Toast.LENGTH_LONG).show();
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

        if(quantityToRemove >= product.getUnitStock())
        {
            call = mIProductApi.removeFromCart(product.getProductId(),
                    product.getUnitStock(),product.getUserId());

            mProductList.remove(position);
            mCartAdapter.notifyItemRemoved(position);
            mCartAdapter.notifyDataSetChanged();
        }
        else
        {
            int remainingQuantity = abs(quantityToRemove-product.getUnitStock());

            product.setUnitStock(remainingQuantity);
            mProductList.set(position,product);
            mCartAdapter.notifyItemChanged(position);
            mCartAdapter.notifyDataSetChanged();
            product = mProductList.get(position);
            int quantityToBeRemoved = product.getUnitStock()-remainingQuantity;
            call = mIProductApi.removeFromCart(product.getProductId(),
                    quantityToRemove,product.getUserId());
        }
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                progressDialog.dismiss();
                if (response.code() == 200 && response.body() == true) {
                    Toast.makeText(CartActivity.this, "Successfully removed product from Cart", Toast.LENGTH_LONG).show();

                    if(mProductList.size() == 0)
                    {
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
}
