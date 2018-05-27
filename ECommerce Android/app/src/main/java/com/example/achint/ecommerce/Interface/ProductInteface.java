package com.example.achint.ecommerce.Interface;

import com.example.achint.ecommerce.Model.ProductData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductInteface {
//    @POST("addOrUpdate")
//    Call<Object> addOrUpdate(@Body ProductData product);

    @GET("getAllProducts")
    Call<ProductData[]>  getAllProducts();

    @GET("/get-product-by-category")
    Call<ProductData[]> getProductsByCategory(@Query("productCategory") String productCategory);
//
//    @GET("delete/{employeeId}")
//    Call<Boolean> deleteEmployee(@Path("employeeId") String employeeId);
}
