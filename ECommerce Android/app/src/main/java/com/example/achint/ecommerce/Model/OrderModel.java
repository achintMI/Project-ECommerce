package com.example.achint.ecommerce.Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Date;

public class OrderModel{

	@SerializedName("productId")
	private String productId;

	@SerializedName("merchantId")
	private String merchantId;

	@SerializedName("productUrl")
	private String productUrl;

	@SerializedName("userId")
	private String userId;

	@SerializedName("orderDate")
	private Date orderDate;

	@SerializedName("totalCost")
	private double totalCost;

	@SerializedName("productQuantity")
	private int quantity;

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setProductUrl(String productUrl){
		this.productUrl = productUrl;
	}

	public String getProductUrl(){
		return productUrl;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setOrderDate(Date orderDate){
		this.orderDate = orderDate;
	}

	public Date getOrderDate(){
		return orderDate;
	}

	public void setTotalCost(int totalCost){
		this.totalCost = totalCost;
	}

	public double getTotalCost(){
		return totalCost;
	}

	@Override
 	public String toString(){
		return 
			"OrderModel{" + 
			"productId = '" + productId + '\'' + 
			",merchantId = '" + merchantId + '\'' + 
			",productUrl = '" + productUrl + '\'' + 
			",userId = '" + userId + '\'' + 
			",orderDate = '" + orderDate + '\'' + 
			",totalCost = '" + totalCost + '\'' + 
			"}";
		}
}