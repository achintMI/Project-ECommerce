package com.ecommerce.ECommerce.DTO;


import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    private Long Id;
    private String productId;
    private String productName;
    private Double productPrice;
    private int unitStock;
    private String productDescription;
    private String productMerchant;
    private String productCategory;
    private int productRating;
    private boolean isIndexed;
    private String merchantId;
    private String productImageUrl;



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

    public int getProductRating() {
        return productRating;
    }

    public boolean isIndexed() {
        return isIndexed;
    }

    public void setIndexed(boolean indexed) {
        isIndexed = indexed;
    }

    public void setProductRating(int productRating) {
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
