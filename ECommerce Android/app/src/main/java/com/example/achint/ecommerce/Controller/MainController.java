package com.example.achint.ecommerce.Controller;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainController extends Application{

    private static Retrofit retrofitForOrder = null;
    private static Retrofit retrofitForProducts = null;
    private static Retrofit retrofitForLogin = null;
    private static Retrofit retrofitForCart = null;
    private static Retrofit retrofitForSearch = null;
    public static MainController mInstance;

    private static final String cartUrl = "http://10.177.1.144:8080/cart/";
    private static final String productUrl = "http://10.177.1.68:8081/products/";
    private static final String orderUrl = "http://10.177.1.68:8080/orders/";
    private static final String loginUrl = "http://10.177.1.234:8080/easybuy/";
    private static final String searchUrl = "http:/10.177.1.143:8080/";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MainController getInstance() {
        return mInstance;
    }

    public Retrofit getClientForOrder() {
        if (null == retrofitForOrder) {
            OkHttpClient client = new OkHttpClient.Builder().build();

            retrofitForOrder = new Retrofit.Builder()
                    .baseUrl(orderUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitForOrder;
    }

    public Retrofit getClientForProducts() {
        if (null == retrofitForProducts) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            retrofitForProducts = new Retrofit.Builder()
                    .baseUrl(productUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitForProducts;
    }

    public Retrofit getClientForLogin() {
        if (null == retrofitForLogin) {
            OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                    .build();

            retrofitForLogin = new Retrofit.Builder()
                    .baseUrl(loginUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitForLogin;
    }

    public Retrofit getClientForCart() {
        if (null == retrofitForCart) {
            OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                    .build();

            retrofitForCart = new Retrofit.Builder()
                    .baseUrl(cartUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitForCart;
    }

    public Retrofit getClientForSearch() {
        if (null == retrofitForSearch) {
            OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                    .build();
            retrofitForSearch = new Retrofit.Builder()
                    .baseUrl(searchUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitForSearch;
    }
}
