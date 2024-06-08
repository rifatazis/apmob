package com.example.appmobuas.model.details;

import com.google.gson.annotations.SerializedName;

public class Detail {
    @SerializedName("id_product")
    private String idProduct;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_price")
    private String productPrice;

    @SerializedName("product_details")
    private String productDetails;

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public Detail(String idProduct, String productName, String productPrice, String productDetails) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDetails = productDetails;
    }
}

