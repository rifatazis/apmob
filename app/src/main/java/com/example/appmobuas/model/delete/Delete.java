package com.example.appmobuas.model.delete;

import com.google.gson.annotations.SerializedName;

public class Delete{

	@SerializedName("product")
	private DeleteProduct deleteProduct;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setProduct(DeleteProduct deleteProduct){
		this.deleteProduct = deleteProduct;
	}

	public DeleteProduct getProduct(){
		return deleteProduct;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}