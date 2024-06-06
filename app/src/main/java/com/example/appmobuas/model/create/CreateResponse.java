package com.example.appmobuas.model.create;

import com.google.gson.annotations.SerializedName;

public class CreateResponse{

	@SerializedName("product")
	private Product product;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setProduct(Product product){
		this.product = product;
	}

	public Product getProduct(){
		return product;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}