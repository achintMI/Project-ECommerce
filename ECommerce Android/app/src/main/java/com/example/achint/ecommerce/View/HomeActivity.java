package com.example.achint.ecommerce.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.achint.ecommerce.Controller.MainController;
import com.example.achint.ecommerce.Adapter.ProductAdapter;
import com.example.achint.ecommerce.Interface.ProductInteface;
import com.example.achint.ecommerce.Model.ProductData;
import com.example.achint.ecommerce.R;
import com.example.achint.ecommerce.Sessions.SessionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements ProductAdapter.IAdapterCommunicator {

    private ProductInteface productApi;
    private RecyclerView productRecycler;
    private List<ProductData> productList = new ArrayList<>();
    private ProductAdapter productAdapter;
    SessionManagement session;
    AlertDialog alert;
    private AlertDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_products);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productRecycler = findViewById(R.id.rv_product_list);
        productAdapter = new ProductAdapter(productList,  this);
        productRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        productRecycler.setAdapter(productAdapter);

        productApi = MainController.getInstance().getClientForProducts().create(ProductInteface.class);
        getAllProductDetails();

        Button search = findViewById(R.id.search_btn);
       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent searchIntent = new Intent(HomeActivity.this, SearchActivity.class);
               EditText text = findViewById(R.id.search_text);
               String searchText = text.getText().toString();
               searchIntent.putExtra("search", searchText);
               startActivity(searchIntent);
           }
       });

        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Do you want to log out ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                session.logoutUser();    // stop chronometer here

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });


        session = new SessionManagement(getApplicationContext());
        Button loginButton = findViewById(R.id.login_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!session.isLoggedIn()){
                    Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }else{
                    alert = builder.create();
                    alert.show();
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
        switch (item.getItemId()){
            case R.id.cat:
                Intent categoryIntent = new Intent(HomeActivity.this, ChooseCategory.class);
                startActivity(categoryIntent);
                return true;
            case R.id.price_filter:
                Intent priceIntent = new Intent(HomeActivity.this, FilterCategory.class);
                priceIntent.putExtra("filter", "price");
                startActivity(priceIntent);
                return true;
            case R.id.rating_filter:
                Intent ratingIntent = new Intent(HomeActivity.this, FilterCategory.class);
                ratingIntent.putExtra("filter", "rating");
                startActivity(ratingIntent);
                return true;
            case R.id.order_history_act:
                Intent orderIntent = new Intent(HomeActivity.this, OrderHistoryActivity.class);
                startActivity(orderIntent);
                return true;
            case R.id.cart:
                SessionManagement session = new SessionManagement(getApplicationContext());
                if(session.isLoggedIn()) {
                    HashMap<String, String> user = session.getUserDetails();
                    String userEmail = user.get(SessionManagement.KEY_EMAIL);
                    String userId = user.get(SessionManagement.KEY_ID);
                    Intent cartIntent = new Intent(HomeActivity.this, CartActivity.class);
                    cartIntent.putExtra("userId", userId);
                    startActivity(cartIntent);
                }else{
                    Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAllProductDetails() {
        progressDialog = new SpotsDialog(HomeActivity.this, R.style.Custom);
        progressDialog.show();
        Call<ProductData[]> call = productApi.getAllProducts();
        call.enqueue(new Callback<ProductData[]>() {
            @Override
            public void onResponse(Call<ProductData[]> call, Response<ProductData[]> response) {
                if (200 == response.code()) {
                    productList.addAll(Arrays.asList(response.body()));
                    productAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ProductData[]> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, "Failed to fetch", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void deleteItem(int position) {
        //TODO
    }
}
