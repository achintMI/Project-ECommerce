package com.example.achint.ecommerce.Model;

import com.google.gson.annotations.SerializedName;

public class ProductData {

    @SerializedName("productImageUrl")
    private String productImageUrl;

    @SerializedName("unitStock")
    private int unitStock;

    @SerializedName("productId")
    private String productId;

    @SerializedName("productMerchant")
    private String productMerchant;

    @SerializedName("merchantRating")
    private double merchantRating;

    @SerializedName("productRating")
    private double productRating;

    @SerializedName("productName")
    private String productName;

    @SerializedName("productPrice")
    private int productPrice;

    @SerializedName("productDescription")
    private String productDescription;

    @SerializedName("merchantId")
    private String merchantId;

    @SerializedName("productCategory")
    private String productCategory;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setUnitStock(int unitStock) {
        this.unitStock = unitStock;
    }

    public int getUnitStock() {
        return unitStock;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductMerchant(String productMerchant) {
        this.productMerchant = productMerchant;
    }

    public String getProductMerchant() {
        return productMerchant;
    }

    public void setMerchantRating(double merchantRating) {
        this.merchantRating = merchantRating;
    }

    public double getMerchantRating() {
        return merchantRating;
    }

    public void setProductRating(double productRating) {
        this.productRating = productRating;
    }

    public double getProductRating() {
        return productRating;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductCategory() {
        return productCategory;
    }

    @Override
    public String toString() {
        return
                "ProductData{" +
                        "productImageUrl = '" + productImageUrl + '\'' +
                        ",unitStock = '" + unitStock + '\'' +
                        ",productId = '" + productId + '\'' +
                        ",productMerchant = '" + productMerchant + '\'' +
                        ",merchantRating = '" + merchantRating + '\'' +
                        ",productRating = '" + productRating + '\'' +
                        ",productName = '" + productName + '\'' +
                        ",productPrice = '" + productPrice + '\'' +
                        ",productDescription = '" + productDescription + '\'' +
                        ",productCategory = '" + productCategory + '\'' +
                        "}";
    }
}