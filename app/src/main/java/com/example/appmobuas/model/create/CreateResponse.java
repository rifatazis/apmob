package com.example.appmobuas.model.create;

import com.google.gson.annotations.SerializedName;

public class CreateResponse {

	@SerializedName("product")
	private ProductC productC;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setProduct(ProductC productC){
		this.productC = productC;
	}

	public ProductC getProduct(){
		return productC;
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