package com.example.achint.ecommerce.Model;


import com.google.gson.annotations.SerializedName;

public class Search{

	@SerializedName("productDesc")
	private String productDesc;

	@SerializedName("searchRating")
	private double searchRating;

	@SerializedName("merchantRating")
	private double merchantRating;

	@SerializedName("stock")
	private int stock;

	@SerializedName("productRating")
	private double productRating;

	@SerializedName("imgSrc")
	private String imgSrc;

	@SerializedName("categoryName")
	private String categoryName;

	@SerializedName("productName")
	private String productName;

	@SerializedName("productPrice")
	private int productPrice;

	@SerializedName("merchantName")
	private String merchantName;

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public void setSearchRating(double searchRating){
		this.searchRating = searchRating;
	}

	public double getSearchRating(){
		return searchRating;
	}

	public void setMerchantRating(double merchantRating){
		this.merchantRating = merchantRating;
	}

	public double getMerchantRating(){
		return merchantRating;
	}

	public void setStock(int stock){
		this.stock = stock;
	}

	public int getStock(){
		return stock;
	}

	public void setProductRating(double productRating){
		this.productRating = productRating;
	}

	public double getProductRating(){
		return productRating;
	}

	public void setImgSrc(String imgSrc){
		this.imgSrc = imgSrc;
	}

	public String getImgSrc(){
		return imgSrc;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setProductPrice(int productPrice){
		this.productPrice = productPrice;
	}

	public int getProductPrice(){
		return productPrice;
	}

	public void setMerchantName(String merchantName){
		this.merchantName = merchantName;
	}

	public String getMerchantName(){
		return merchantName;
	}

	@Override
 	public String toString(){
		return 
			"Search{" + 
			"searchRating = '" + searchRating + '\'' + 
			",merchantRating = '" + merchantRating + '\'' + 
			",stock = '" + stock + '\'' + 
			",productRating = '" + productRating + '\'' + 
			",imgSrc = '" + imgSrc + '\'' + 
			",categoryName = '" + categoryName + '\'' + 
			",productName = '" + productName + '\'' + 
			",productPrice = '" + productPrice + '\'' + 
			",merchantName = '" + merchantName + '\'' + 
			"}";
		}
}