package com.example.appmobuas.model.update;

import com.google.gson.annotations.SerializedName;

public class Product{

	@SerializedName("id_product")
	private String idProduct;

	@SerializedName("product_image")
	private String productImage;

	@SerializedName("product_price")
	private String productPrice;

	@SerializedName("product_details")
	private String productDetails;

	@SerializedName("product_name")
	private String productName;

	public void setIdProduct(String idProduct){
		this.idProduct = idProduct;
	}

	public String getIdProduct(){
		return idProduct;
	}

	public void setProductImage(String productImage){
		this.productImage = productImage;
	}

	public String getProductImage(){
		return productImage;
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