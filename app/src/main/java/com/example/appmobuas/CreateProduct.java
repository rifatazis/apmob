package com.example.appmobuas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityCreateProductBinding;
import com.example.appmobuas.model.create.CreateResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateProduct extends AppCompatActivity {

    private ActivityCreateProductBinding binding;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inisialisasi Retrofit
        Retrofit retrofit = ApiConfig.getConfig();
        apiService = retrofit.create(ApiService.class);

        binding.btnChooseImage.setOnClickListener(v -> openImageChooser());
        binding.btnCreateProduct.setOnClickListener(v -> createProduct());
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private byte[] imageData;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                if (inputStream != null) {
                    imageData = getBytes(inputStream);
                    binding.ivProductImage.setImageURI(data.getData());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void createProduct() {
        String productName = binding.etProductName.getText().toString().trim();
        String productDetails = binding.etProductDetails.getText().toString().trim();
        String productPrice = binding.etProductPrice.getText().toString().trim();

        if (productName.isEmpty() || productDetails.isEmpty() || productPrice.isEmpty() || imageData == null) {
            Toast.makeText(this, "Complete all product details", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), productName);
        RequestBody detailsBody = RequestBody.create(MediaType.parse("text/plain"), productDetails);
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), productPrice);

        // Buat request body untuk gambar
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), imageData);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("product_image", "product_image.jpg", fileBody);

        Call<CreateResponse> call = apiService.createProduct(nameBody, detailsBody, priceBody, imagePart);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CreateResponse productResponse = response.body();
                    if (!productResponse.isError()) {
                        Toast.makeText(CreateProduct.this, "Product created successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreateProduct.this, "Failed to create product: " + productResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateProduct.this, "Failed to create product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {
                Toast.makeText(CreateProduct.this, "Failed to create product: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
