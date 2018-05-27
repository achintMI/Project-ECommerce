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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Intent i =getIntent();
        //userId = i.getStringExtra("productUser");

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
        Call< Product[]> call = mIProductApi.cartListItems("555");
        call.enqueue(new Callback<Product[]>() {
            @Override
            public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                if (response.code() == 200) {
                    mProductList.addAll(Arrays.asList(response.body()));
                    mCartAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<Product[]> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, "Failed to Show Cart", Toast.LENGTH_LONG);
            }

        });
    }




    @Override
    public void deleteItem(final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        Product product = mProductList.get(position);

        Call<Boolean> call = mIProductApi.removeFromCart(product.getProductId(),
                product.getProductQuantity(),product.getUserId());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                progressDialog.dismiss();

                if (response.code() == 200 && response.body() == true) {
                    mProductList.remove(position);
                    mCartAdapter.notifyItemRemoved(position);
                    Toast.makeText(CartActivity.this, "Successfully removed product from Cart", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, "Failed to remove product from Cart", Toast.LENGTH_LONG);
            }
        });
    }
}
