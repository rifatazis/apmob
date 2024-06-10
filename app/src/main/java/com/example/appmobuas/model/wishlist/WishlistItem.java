package com.example.appmobuas.model.wishlist;

public class WishlistItem {
    private int id_product;
    private String product_name;
    private int product_price;
    private String product_image;

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public WishlistItem(int id_product, String product_name, int product_price, String product_image) {
        this.id_product = id_product;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
    }
}
