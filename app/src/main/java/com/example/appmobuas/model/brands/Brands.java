package com.example.appmobuas.model.brands;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Brands{

	@SerializedName("data")
	private List<BrandsData> data;

	@SerializedName("message")
	private String message;

	public void setData(List<BrandsData> data){
		this.data = data;
	}

	public List<BrandsData> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}