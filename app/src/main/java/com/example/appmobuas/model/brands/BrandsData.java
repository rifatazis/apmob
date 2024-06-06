package com.example.appmobuas.model.brands;

import com.google.gson.annotations.SerializedName;

public class BrandsData {

	@SerializedName("nama")
	private String nama;

	@SerializedName("logo")
	private String logo;

	@SerializedName("id")
	private int id;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}