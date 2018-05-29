package com.example.achint.ecommerce.View;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.achint.ecommerce.Adapter.ProductAdapter;
import com.example.achint.ecommerce.Adapter.SearchAdapter;
import com.example.achint.ecommerce.Controller.MainController;
import com.example.achint.ecommerce.Interface.SearchInterface;
import com.example.achint.ecommerce.Model.Search;
import com.example.achint.ecommerce.R;
import com.example.achint.ecommerce.Sessions.SessionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.IAdapterCommunicator, ProductAdapter.IAdapterCommunicator {

    private SearchInterface productApi;
    private RecyclerView productRecycler;
    private List<Search> productList = new ArrayList<>();
    private SearchAdapter searchAdapter;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_products);

        productRecycler = findViewById(R.id.rv_product_list);
        searchAdapter = new SearchAdapter(productList,  this);
        productRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        productRecycler.setAdapter(searchAdapter);

        Bundle intent = getIntent().getExtras();
        String search = intent.getString("search");
        productApi = MainController.getInstance().getClientForSearch().create(SearchInterface.class);
        SearchProducts(search);

    }

    private void SearchProducts(String search) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        Call<Search[]> call = productApi.SearchProducts(search, 0, 10);
        call.enqueue(new Callback<Search[]>() {
            @Override
            public void onResponse(Call<Search[]> call, Response<Search[]> response) {
                if (200 == response.code()) {
                    productList.addAll(Arrays.asList(response.body()));
                    searchAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                    Toast.makeText(SearchActivity.this, "Worked", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Search[]> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SearchActivity.this, "Failed to fetch", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void deleteItem(int position) {

    }
}
