package com.example.achint.ecommerce.Interface;

import com.example.achint.ecommerce.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartInterface {

    @GET("add-to-cart")
    Call<Boolean> addToCart(@Query("productId") String productId,@Query("unitStock") int unitStock,@Query("userId") String userId, @Query("imageUrl") String imageUrl, @Query("productCost") double productCost, @Query("productName") String productName);

    @GET("remove-from-cart")
    Call<Boolean> removeFromCart(@Query("productId") String productId,@Query("unitStock") int unitStock,@Query("userId") String userId);//(@Body Product product);

    @GET("cart-list-items")
    Call<Product[]> cartListItems(@Query("userId") String userId);

    @GET("clear-cart")
    Call<Boolean> clearCart(@Query("userId") String userId);

}
