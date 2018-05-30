package com.example.achint.ecommerce.Interface;

import com.example.achint.ecommerce.Model.ProductData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductInteface {

    @GET("getAllProducts")
    Call<ProductData[]>  getAllProducts();

    @GET("get-product-by-category")
    Call<ProductData[]> getAllProductsByCategory(@Query("productCategory") String productCategory);

    @GET("getProductSortByPrice")
    Call<ProductData[]> getProductSortByPrice();

    @GET("getProductSortByRating")
    Call<ProductData[]> getProductSortByRating();

    @GET("get-product-by-name")
    Call<ProductData[]> getProductByName(@Query(value = "productName", encoded = true) String productName);

    @GET("getProductById")
    Call<ProductData> getProductById(@Query("productId") String productId);
}
