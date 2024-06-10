package com.example.appmobuas.model.sports;

import com.google.gson.annotations.SerializedName;

public class SportData {

	@SerializedName("nama")
	private String nama;

	@SerializedName("id")
	private int id;

	@SerializedName("image")
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@SerializedName("brand_id")
	private int brandId;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setBrandId(int brandId){
		this.brandId = brandId;
	}

	public int getBrandId(){
		return brandId;
	}
}