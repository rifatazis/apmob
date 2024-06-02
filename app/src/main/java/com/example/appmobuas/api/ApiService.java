package com.example.appmobuas.api;

import com.example.appmobuas.model.login.Login;
import com.example.appmobuas.model.product.Product;
import com.example.appmobuas.model.profil.Profil;
import com.example.appmobuas.model.register.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

    @GET("product.php")
    Call<Product> fetchProducts();
}