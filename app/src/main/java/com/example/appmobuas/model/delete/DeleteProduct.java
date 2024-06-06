package com.example.appmobuas.model.delete;

import com.google.gson.annotations.SerializedName;

public class DeleteProduct {

	@SerializedName("sport_id")
	private int sportId;

	@SerializedName("id_product")
	private int idProduct;

	@SerializedName("product_image")
	private String productImage;

	@SerializedName("product_price")
	private int productPrice;

	@SerializedName("product_details")
	private String productDetails;

	@SerializedName("product_name")
	private String productName;

	public void setSportId(int sportId){
		this.sportId = sportId;
	}

	public int getSportId(){
		return sportId;
	}

	public void setIdProduct(int idProduct){
		this.idProduct = idProduct;
	}

	public int getIdProduct(){
		return idProduct;
	}

	public void setProductImage(String productImage){
		this.productImage = productImage;
	}

	public String getProductImage(){
		return productImage;
	}

	public void setProductPrice(int productPrice){
		this.productPrice = productPrice;
	}

	public int getProductPrice(){
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