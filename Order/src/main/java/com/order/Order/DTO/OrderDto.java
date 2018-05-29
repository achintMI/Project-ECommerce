package com.order.Order.DTO;

import java.time.LocalDate;

public class OrderDto {

    private Long cartId;
    private String productId;
    private String merchantId;
    private String userId;
    private double totalCost;
    private LocalDate orderDate;
    private String productUrl;
    private int quantity;

    public OrderDto(String productUrl, String productId, String userId, String merchantId, double totalCost, int quantity) {
        this.productId = productId;
        this.merchantId = merchantId;
        this.userId = userId;
        this.totalCost = totalCost;
        this.orderDate = LocalDate.now();
        this.productUrl = productUrl;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
