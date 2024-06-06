package com.example.appmobuas.model.create;

import com.google.gson.annotations.SerializedName;

public class ProductC {

	@SerializedName("product_image")
	private String productImage;

	@SerializedName("id")
	private String id;

	@SerializedName("product_price")
	private String productPrice;

	@SerializedName("product_details")
	private String productDetails;

	@SerializedName("product_name")
	private String productName;

	public void setProductImage(String productImage){
		this.productImage = productImage;
	}

	public String getProductImage(){
		return productImage;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setProductPrice(String productPrice){
		this.productPrice = productPrice;
	}

	public String getProductPrice(){
		return productPrice;
	}

	public void setProductDetails(String productDetails){
		this.productDetails = productDetails;
	}

	public String getProductDetails(){
		return productDetails;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}
}