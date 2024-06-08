package com.example.appmobuas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityUpdateProductsBinding;
import com.example.appmobuas.model.update.UpdateResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProducts extends AppCompatActivity {

    private ActivityUpdateProductsBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private static final String TAG = "UpdateProducts";
    private int productId, sportId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productId = getIntent().getIntExtra("id_product", 0);
        sportId = getIntent().getIntExtra("sport_id", 0);
        Log.d(TAG, "onCreate: Product ID: " + productId);

        binding.chooseImageButton.setOnClickListener(v -> openImagePicker());
        binding.updateButton.setOnClickListener(v -> updateProduct(productId));
    }

    private void openImagePicker() {
        Log.d(TAG, "openImagePicker: Opening image picker");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            Log.d(TAG, "onActivityResult: Image selected URI: " + selectedImageUri);
            binding.productImage.setImageURI(selectedImageUri);
        } else {
            Log.d(TAG, "onActivityResult: No image selected");
        }
    }

    private void updateProduct(int productId) {
        String productName = binding.productName.getText().toString().trim();
        String productDetails = binding.productDetails.getText().toString().trim();
        String productPrice = binding.productPrice.getText().toString().trim();

        if (productName.isEmpty() || productDetails.isEmpty() || productPrice.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "Mohon lengkapi semua field dan pilih gambar", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "updateProduct: Missing fields or image");
            return;
        }

        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);

        RequestBody productIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productId));
        RequestBody productNameBody = RequestBody.create(MediaType.parse("text/plain"), productName);
        RequestBody productDetailsBody = RequestBody.create(MediaType.parse("text/plain"), productDetails);
        RequestBody productPriceBody = RequestBody.create(MediaType.parse("text/plain"), productPrice);

        MultipartBody.Part imagePart = prepareFilePart("product_image", selectedImageUri);
        Log.d(TAG, "updateProduct: Prepared image part: " + imagePart);

        Call<UpdateResponse> call = apiService.updateProduct(productIdBody, productNameBody, productDetailsBody, productPriceBody, imagePart);
        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpdateResponse updateResponse = response.body();
                    Log.d(TAG, "onResponse: Update successful: " + updateResponse.getMessage());
                    if (!updateResponse.isError()) {
                        Toast.makeText(UpdateProducts.this, updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        // Kirim ID produk dan ID olahraga ke halaman DetailProducts
                        Intent intent = new Intent(UpdateProducts.this, DetailProducts.class);
                        intent.putExtra("id_product", productId);
                        intent.putExtra("sport_id", sportId);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(UpdateProducts.this, "Gagal mengupdate produk: " + updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "onResponse: response unsuccessful or body is null");
                    Log.e(TAG, "Response code: " + response.code());
                    Log.e(TAG, "Response message: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e(TAG, "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(UpdateProducts.this, "Gagal mengupdate produk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(UpdateProducts.this, "Gagal terhubung ke server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] imageData = bos.toByteArray();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageData);
            Log.d(TAG, "prepareFilePart: File prepared");
            return MultipartBody.Part.createFormData(partName, "image.jpg", requestFile);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "prepareFilePart: Error preparing file", e);
            return null;
        }
    }
}
