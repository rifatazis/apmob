package com.example.appmobuas.api;

import com.example.appmobuas.model.brands.Brands;
import com.example.appmobuas.model.create.CreateResponse;
import com.example.appmobuas.model.delete.DefaultResponse;
import com.example.appmobuas.model.login.Login;
import com.example.appmobuas.model.products.Products;
import com.example.appmobuas.model.profil.Profil;
import com.example.appmobuas.model.register.Register;
import com.example.appmobuas.model.sports.Sports;
import com.example.appmobuas.model.update.UpdateResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Multipart
    @POST("create.php")
    Call<CreateResponse> createProduct(
            @Part("product_name") RequestBody productName,
            @Part("product_details") RequestBody productDetails,
            @Part("product_price") RequestBody productPrice,
            @Part("sport_id") RequestBody sportId,
            @Part MultipartBody.Part productImage
    );


    @GET("products/{id_product}")
    Call<Products> getProductDetails(@Path("id_product") int productId);


    @Multipart
    @POST("update.php")
    Call<UpdateResponse> updateProduct(
            @Part("id_product") RequestBody productId,
            @Part("product_name") RequestBody productName,
            @Part("product_details") RequestBody productDetails,
            @Part("product_price") RequestBody productPrice,
            @Part MultipartBody.Part product_image
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<UpdateResponse> updateWithoutImage(
            @Field("id_product") RequestBody idProduct,
            @Field("product_name") RequestBody productName,
            @Field("product_details") RequestBody productDetails,
            @Field("product_price") RequestBody productPrice
    );


    @FormUrlEncoded
    @POST("delete.php")
    Call<DefaultResponse> deleteProduct(
            @Field("id_product") int productId
    );

}