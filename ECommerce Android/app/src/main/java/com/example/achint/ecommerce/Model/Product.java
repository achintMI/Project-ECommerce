package com.example.achint.ecommerce.Model;


import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("productName")
    private String productName;

    @SerializedName("productId")
    private String productId;

    @SerializedName("unitStock")
    private int unitStock;

    @SerializedName("userId")
    private String userId;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("productCost")
    private double productCost;


    public Product(String productName, String productId, int unitStock, String userId, String imageUrl, double productCost) {
        this.productName = productName;
        this.productId = productId;
        this.unitStock = unitStock;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.productCost = productCost;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getUnitStock() {
        return unitStock;
    }

    public void setUnitStock(int unitStock) {
        this.unitStock = unitStock;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }
}