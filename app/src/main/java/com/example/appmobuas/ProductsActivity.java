package com.example.appmobuas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityProductsBinding;
import com.example.appmobuas.model.adapter.ProductsAdapter;
import com.example.appmobuas.model.products.Products;
import com.example.appmobuas.model.products.ProductsData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityProductsBinding binding;
    private String encodedImage;
    private ProductsAdapter productsAdapter;
    private ArrayList<ProductsData> productList = new ArrayList<>();
    private int sportId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sportId = getIntent().getIntExtra("sport_id", -1);
        if (sportId == -1) {
            Toast.makeText(this, "ID Olahraga tidak valid", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.btnChooseImage.setOnClickListener(v -> openImageChooser());
        binding.btnCreateProduct.setOnClickListener(v -> createProduct());

        setupRecyclerView();
        fetchProducts(sportId);
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                binding.ivProductImage.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createProduct() {
        String productName = binding.etProductName.getText().toString().trim();
        String productDetails = binding.etProductDetails.getText().toString().trim();
        String productPrice = binding.etProductPrice.getText().toString().trim();

        if (productName.isEmpty() || productDetails.isEmpty() || productPrice.isEmpty() || encodedImage == null) {
            Toast.makeText(this, "Lengkapi semua data produk", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);
        Call<Products> call = apiService.createProduct(productName, productDetails, sportId, Integer.parseInt(productPrice), encodedImage);
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ProductsActivity.this, "Produk berhasil dibuat", Toast.LENGTH_SHORT).show();
                    fetchProducts(sportId);
                } else {
                    Toast.makeText(ProductsActivity.this, "Gagal membuat produk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        productsAdapter = new ProductsAdapter(this, productList);
        binding.recyclerViewProducts.setLayoutManager(new GridLayoutManager(this,2));
        binding.recyclerViewProducts.setAdapter(productsAdapter);
    }

    private void fetchProducts(int sportId) {
        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);
        Call<Products> call = apiService.fetchProducts(sportId);
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    productList.addAll(response.body().getData());
                    productsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ProductsActivity.this, "Gagal mengambil data produk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
