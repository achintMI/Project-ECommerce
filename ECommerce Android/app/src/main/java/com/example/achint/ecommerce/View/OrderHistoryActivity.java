package com.example.achint.ecommerce.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.achint.ecommerce.Adapter.OrderAdapter;
import com.example.achint.ecommerce.Controller.MainController;
import com.example.achint.ecommerce.Interface.OrderInterface;
import com.example.achint.ecommerce.Model.OrderModel;
import com.example.achint.ecommerce.R;
import com.example.achint.ecommerce.Sessions.SessionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity implements OrderAdapter.IAdapterCommunicator {

    private OrderInterface orderApi;
    private RecyclerView orderRecycler;
    private List<OrderModel> orderList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        orderRecycler = findViewById(R.id.rv_order_history);
        orderAdapter = new OrderAdapter(orderList, this);

        orderRecycler.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this, LinearLayoutManager.VERTICAL, false));
        orderRecycler.setAdapter(orderAdapter);

        orderApi = MainController.getInstance().getClientForOrder().create(OrderInterface.class);
        getAllOrderHistory();
    }

    public void getAllOrderHistory() {
        SessionManagement session = new SessionManagement(getApplicationContext());
        if(session.isLoggedIn()) {
            HashMap<String, String> user = session.getUserDetails();
            String userEmail = user.get(SessionManagement.KEY_EMAIL);
            String userId = user.get(SessionManagement.KEY_ID);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            Call<OrderModel[]> call = orderApi.getCartHistory(userId);
            call.enqueue(new Callback<OrderModel[]>() {
                @Override
                public void onResponse(Call<OrderModel[]> call, Response<OrderModel[]> response) {
                    if (200 == response.code()) {
                        orderList.addAll(Arrays.asList(response.body()));
                        Collections.reverse(orderList);
                        orderAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                        Toast.makeText(OrderHistoryActivity.this, "Worked", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OrderModel[]> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(OrderHistoryActivity.this, "Failed to fetch", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Intent loginIntent = new Intent(OrderHistoryActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }

    @Override
    public void deleteItem(int position) {
        // ToDo
    }
}
