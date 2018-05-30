package com.ecommerce.ECommerce.DTO;

/**
 * Primitive datatypes
 */
public class ProductSearchDto {
    private String pid;
    private String mid;
    private String productName;
    private Double productPrice;
    private int stock;
    private String productDescription;
    private String merchantName;
    private String productCategory;
    private String imgSrc;
    private Double productRating;
    private boolean isIndexed;

    public ProductSearchDto(ProductDto product) {
        this.setPid(product.getProductId());
        this.setMid(product.getMerchantId());
        this.setProductName(product.getProductName());
        this.setProductPrice(product.getProductPrice());
        this.setStock(product.getUnitStock());
        this.setProductDescription(product.getProductDescription());
        this.setMerchantName(product.getProductMerchant());
        this.setProductCategory(product.getProductCategory());
        this.setImgSrc(product.getProductImageUrl());
        this.setProductRating(product.getProductRating()*1.0);
        this.setIndexed(true);
    }

    public ProductSearchDto() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Double getProductRating() {
        return productRating;
    }

    public void setProductRating(Double productRating) {
        this.productRating = productRating;
    }

    public boolean isIndexed() {
        return isIndexed;
    }

    public void setIndexed(boolean indexed) {
        isIndexed = indexed;
    }
}
