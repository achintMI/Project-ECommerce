package com.order.Order.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="orders")
public class OrderModel {

    @Id
    @GeneratedValue
    private Long cartId;

    private String productId;
    private String merchantId;
    private String userId;
    private LocalDate orderDate;
    private String productUrl;
    private double totalCost;

    public OrderModel(String productUrl, String productId, String userId, String merchantId, double totalCost) {
        this.orderDate = java.time.LocalDate.now();
        this.productId = productId;
        this.merchantId = merchantId;
        this.userId = userId;
        this.totalCost = totalCost;
        this.productUrl = productUrl;
        this.orderDate = LocalDate.now();
    }

    public OrderModel() {

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
