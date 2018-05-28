package com.example.achint.ecommerce.Model;


import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("productName")
    private String productName;

    @SerializedName("productId")
    private String productId;

    @SerializedName("productQuantity")
    private int productQuantity;

    @SerializedName("userId")
    private String userId;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("productCost")
    private double productCost;


    public Product(String productName, String productId, int productQuantity, String userId, String imageUrl, double productCost) {
        this.productName = productName;
        this.productId = productId;
        this.productQuantity = productQuantity;
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

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
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