package com.example.appmobuas.api;

import com.example.appmobuas.model.brands.Brands;
import com.example.appmobuas.model.login.Login;
import com.example.appmobuas.model.products.Products;
import com.example.appmobuas.model.products.ProductsData;
import com.example.appmobuas.model.profil.Profil;
import com.example.appmobuas.model.register.Register;
import com.example.appmobuas.model.sports.Sports;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<Login> LoginResponse(
            @Field("username") String username,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("register.php")
    Call<Register> RegisterResponse(
            @Field("username") String username,
            @Field("name") String name,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("updateProfil.php")
    Call<Profil> updateProfile(
            @Field("id") String id,
            @Field("username") String username,
            @Field("name") String name,
            @Field("bio") String bio
    );
    @GET("brands.php")
    Call<Brands> fetchBrands();

    @GET("sports.php")
    Call<Sports> fetchSports(@Query("brand_id") int brandId);

    @GET("products.php")
    Call<Products> fetchProducts(@Query("sport_id") int sportId);

    @FormUrlEncoded
    @POST("create.php")
    Call<Products> createProduct(
            @Field("product_name") String productName,
            @Field("product_details") String productDetails,
            @Field("sport_id") int sportId,
            @Field("product_price") int productPrice,
            @Field("product_image") String productImage
    );

    @GET("products.php")
    Call<ProductsData> getProductDetails(@Query("sport_id") int sportId);

    @PUT("products/{id}")
    Call<ProductsData> updateProduct(
            @Path("id") int id,
            @Body ProductsData product
    );

    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

}