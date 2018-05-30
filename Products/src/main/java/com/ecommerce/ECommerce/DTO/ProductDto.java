package com.ecommerce.ECommerce.DTO;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductDto {
    private Long Id;
    private String productId;
    private String productName;
    private Double productPrice;
    private int unitStock;
    private String productDescription;
    private String productMerchant;
    private String productCategory;
    private double productRating;
    private boolean isIndexed;
    private String merchantId;
    private String productImageUrl;
    private double merchantRating;


    public ProductDto() {
    }

    public ProductDto(String productId, String productName, Double productPrice, int unitStock, String productDescription, String productMerchant, String productCategory, double productRating, boolean isIndexed, String merchantId, String productImageUrl, double merchantRating) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.unitStock = unitStock;
        this.productDescription = productDescription;
        this.productMerchant = productMerchant;
        this.productCategory = productCategory;
        this.productRating = productRating;
        this.isIndexed = isIndexed;
        this.merchantId = merchantId;
        this.productImageUrl = productImageUrl;
        this.merchantRating = merchantRating;
    }

    public double getMerchantRating() {
        return merchantRating;
    }

    public void setMerchantRating(double merchantRating) {
        this.merchantRating = merchantRating;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productUrl) {
        this.productImageUrl = productUrl;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public double getProductRating() {
        return productRating;
    }

    public boolean isIndexed() {
        return isIndexed;
    }

    public void setIndexed(boolean indexed) {
        isIndexed = indexed;
    }

    public void setProductRating(double productRating) {
        this.productRating = productRating;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public int getUnitStock() {
        return unitStock;
    }

    public void setUnitStock(int unitStock) {
        this.unitStock = unitStock;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductMerchant() {
        return productMerchant;
    }

    public void setProductMerchant(String productMerchant) {
        this.productMerchant = productMerchant;
    }
}
