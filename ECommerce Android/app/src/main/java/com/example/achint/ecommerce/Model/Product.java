package com.example.achint.ecommerce.Model;


import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("productName")
    private String productName;

    @SerializedName("productId")
    private String productId;

    @SerializedName("productPrice")
    private String productPrice;

    @SerializedName("productQuantity")
    private int productQuantity;

    @SerializedName("productImageURL")
    private String productImageURL;

    @SerializedName("userId")
    private String userId;


    public Product(String productName, String productId, String productPrice, int productQuantity, String productImageURL) {
        this.productName = productName;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productImageURL = productImageURL;
    }

    public Product() {
    }

    public Product(String productName, String productId, String productPrice, int productQuantity, String productImageURL, String userId) {
        this.productName = productName;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productImageURL = productImageURL;
        this.userId = userId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }


    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productId='" + productId + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productQuantity=" + productQuantity +
                ", productImageURL='" + productImageURL + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}