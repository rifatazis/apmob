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

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityCreateProductBinding;
import com.example.appmobuas.model.products.Products;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProduct extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityCreateProductBinding binding;
    private String encodedImage;
    private int sportId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sportId = getIntent().getIntExtra("sport_id", -1);
        if (sportId == -1) {
            Toast.makeText(this, "ID Olahraga tidak valid", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.btnChooseImage.setOnClickListener(v -> openImageChooser());
        binding.btnCreateProduct.setOnClickListener(v -> createProduct());
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
                    Toast.makeText(CreateProduct.this, "Produk berhasil dibuat", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateProduct.this, "Gagal membuat produk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(CreateProduct.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
