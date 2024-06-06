package com.example.appmobuas.model.update;

import com.google.gson.annotations.SerializedName;

public class UpdateRequest {
    @SerializedName("product_id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_details")
    private String productDetails;
    @SerializedName("product_price")
    private int productPrice;
    @SerializedName("product_image")
    private String productImage;
    @SerializedName("sport_id")
    private int sportId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public UpdateRequest(int productId, String productName, String productDetails, int productPrice, String productImage, int sportId) {
        this.productId = productId;
        this.productName = productName;
        this.productDetails = productDetails;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.sportId = sportId;
    }
}
