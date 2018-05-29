package com.example.achint.ecommerce.Interface;

import com.example.achint.ecommerce.Model.OrderModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrderInterface {

    @GET("place-order")
    Call<OrderModel>  placeOrder(@Query("emailUser") String emailUser, @Query("productUrl") String productUrl,
                             @Query("productId") String productId, @Query("userId") String userId,
                             @Query("merchantId") String merchantId,
                             @Query("cost") double cost, @Query("quantity") int quantity);

    @GET("order-history")
    Call<OrderModel[]> getCartHistory(@Query("userId") String userId);
}
