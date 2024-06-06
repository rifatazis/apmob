package com.example.appmobuas.model.products;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products{

	@SerializedName("data")
	private List<ProductsData> data;

	@SerializedName("message")
	private String message;

	public void setData(List<ProductsData> data){
		this.data = data;
	}

	public List<ProductsData> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}