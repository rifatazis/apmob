package com.example.appmobuas.model.category;

import com.google.gson.annotations.SerializedName;

public class DataCategory {

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("category")
	private String category;

	public void setCategoryId(int categoryId){
		this.categoryId = categoryId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}
}