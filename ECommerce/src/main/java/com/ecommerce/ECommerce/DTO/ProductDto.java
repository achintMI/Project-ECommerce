package com.ecommerce.ECommerce.DTO;


import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    private String Id;
    private String productId;
    private String productName;
    private Double productPrice;
    private int unitStock;
    private String productDescription;
    private String productMerchant;
    private MultipartFile productImage;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
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

    public MultipartFile getProductImage() {
        return productImage;
    }

    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }
}
