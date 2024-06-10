package com.example.appmobuas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityDetailProductsBinding;
import com.example.appmobuas.model.delete.DefaultResponse;
import com.example.appmobuas.model.wishlist.WishlistItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProducts extends AppCompatActivity {

    private ActivityDetailProductsBinding binding;
    private int productId, sportId , productPrice;
    String productName,productDetails, productImage;

    public static ArrayList<WishlistItem> wishlist = new ArrayList<>();

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            productId = intent.getIntExtra("id_product", 0);
            productName = intent.getStringExtra("product_name");
            productPrice = intent.getIntExtra("product_price", 0);
            productDetails = intent.getStringExtra("product_details");
            productImage = intent.getStringExtra("product_image");

            binding.tvProductName.setText(productName);
            binding.tvProductPrice.setText(String.format("Rp %,d", productPrice));
            binding.tvProductDetails.setText(productDetails);

            if (productImage != null && !productImage.isEmpty()) {
                byte[] imageBytes = Base64.decode(productImage, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                binding.ivProductImage.setImageBitmap(decodedImage);
            } else {
                binding.ivProductImage.setImageResource(R.drawable.baseline_error_24);
            }
        } else {
            Toast.makeText(this, "Data produk tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }

        binding.btn1.setOnClickListener(v -> {
            startActivity(new Intent(DetailProducts.this,MainActivity.class));
        });
        binding.btn2.setOnClickListener(v -> {
            startActivity(new Intent(DetailProducts.this,CatergoryActivity.class));
        });
        binding.btn4.setOnClickListener(v -> {
            startActivity(new Intent(DetailProducts.this, WishtlistActivity.class));
        });
        binding.btn5.setOnClickListener(v -> {
            startActivity(new Intent(DetailProducts.this,AccountActivity.class));
        });
        binding.btnBuy.setOnClickListener(v ->{
            Toast.makeText(this, "The product was successfully purchased", Toast.LENGTH_SHORT).show();
        });
        binding.btnDeleteProduct.setOnClickListener(v -> deleteProduct());
        binding.btnUpdateProduct.setOnClickListener(v -> {
            Intent updateIntent = new Intent(DetailProducts.this, UpdateProducts.class);
            updateIntent.putExtra("id_product", productId);
            updateIntent.putExtra("sport_id", sportId);
            startActivity(updateIntent);
        });
        binding.btnAddToWishlist.setOnClickListener(v -> addToWishlist());
    }
    private void addToWishlist() {
        WishlistItem wishlistItem = new WishlistItem(productId, productName, productPrice, productImage);
        wishlist.add(wishlistItem);

        Toast.makeText(this, "Produk ditambahkan ke wishlist", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DetailProducts.this, WishtlistActivity.class);
        startActivity(intent);
    }

    private void deleteProduct() {
        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);
        Call<DefaultResponse> call = apiService.deleteProduct(productId);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    DefaultResponse defaultResponse = response.body();
                    if (defaultResponse != null && !defaultResponse.isError()) {
                        Toast.makeText(DetailProducts.this, "Produk berhasil dihapus", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailProducts.this, CatergoryActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(DetailProducts.this, "Gagal menghapus produk", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailProducts.this, "Gagal menghapus produk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(DetailProducts.this, "Gagal menghapus produk", Toast.LENGTH_SHORT).show();
            }
        });
    }
}