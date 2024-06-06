package com.example.appmobuas.model.sports;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Sports{

	@SerializedName("data")
	private List<SportData> data;

	@SerializedName("message")
	private String message;

	public void setData(List<SportData> data){
		this.data = data;
	}

	public List<SportData> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}