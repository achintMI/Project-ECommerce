package com.example.achint.ecommerce.Interface;

import com.example.achint.ecommerce.Model.ProductData;
import com.example.achint.ecommerce.Model.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchInterface {
    @GET("search")
    Call<Search[]> SearchProducts(@Query("searchTerm") String searchTerm, @Query("page") int page, @Query("pageSize") int pageSize);

    @GET("suggest")
    Call<String[]> findSuggestion(@Query("requestQuery") String requestQuery);

}
