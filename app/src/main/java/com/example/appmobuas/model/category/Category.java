package com.example.appmobuas.model.category;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Category{

	@SerializedName("data")
	private List<DataCategory> data;

	@SerializedName("message")
	private String message;

	public void setData(List<DataCategory> data){
		this.data = data;
	}

	public List<DataCategory> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}