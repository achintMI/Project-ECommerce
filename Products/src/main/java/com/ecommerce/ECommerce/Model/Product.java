package com.ecommerce.ECommerce.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = Product.COLLECTION_NAME)
public class Product {
    public static final String COLLECTION_NAME = "product";

    //@NotNull(message = "Product Id mustn\'t be null")
    @Id
    private String productId;

    //@NotNull(message = "Product Name mustn\'t be null")
    private String productName;

    //@NotNull(message = "product Price mustn\'t be null")
    private Double productPrice;

    private int unitStock;

    //@NotNull(message = "Product Description mustn\'t be null")
    private String productDescription;

    //@NotNull(message = "Product Merchant mustn\'t be null")
    private String productMerchant;

    //@NotNull(message = "Product Category mustn\'t be null")
    private String productCategory;

    private String productImageUrl;

    private String merchantId;

    private int merchantRating;

    private int productRating;

    private boolean isIndexed;



    public Product(String merchantId, int productRating, String productCategory, int merchantRating, String productImageUrl, String productId, String productName, Double productPrice, int unitStock, String productDescription, String productMerchant) {
        //Id = id;
        this.merchantId = merchantId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.unitStock = unitStock;
        this.productDescription = productDescription;
        this.productMerchant = productMerchant;
        this.productImageUrl = productImageUrl;
        this.merchantRating = merchantRating;
        this.productCategory = productCategory;
        this.productRating = productRating;
        this.isIndexed = false;
    }

    public Product(){

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

    public int getMerchantRating() {
        return merchantRating;
    }

    public void setMerchantRating(int merchantRating) {
        this.merchantRating = merchantRating;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
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
