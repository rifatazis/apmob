package com.example.appmobuas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityCreateProductBinding;
import com.example.appmobuas.model.create.CreateResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateProduct extends AppCompatActivity {

    private ActivityCreateProductBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ApiService apiService;
    private int sportId; // To store the selected sport ID
    private static final int REQUEST_CODE_PERMISSION = 123;
    private String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkPermissions();

        sportId = getIntent().getIntExtra("sport_id", -1);
        if (sportId == -1) {
            Toast.makeText(this, "Invalid sport ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Retrofit retrofit = ApiConfig.getConfig();
        apiService = retrofit.create(ApiService.class);

        binding.btnChooseImage.setOnClickListener(v -> openImageChooser());
        binding.btnCreateProduct.setOnClickListener(v -> createProduct());
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imagePath = getPathFromUri(selectedImageUri);
            if (imagePath != null) {
                binding.ivProductImage.setImageURI(selectedImageUri);
            } else {
                Toast.makeText(this, "Failed to get image path", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }

    private void createProduct() {
        String productName = binding.etProductName.getText().toString().trim();
        String productDetails = binding.etProductDetails.getText().toString().trim();
        String productPrice = binding.etProductPrice.getText().toString().trim();

        if (productName.isEmpty() || productDetails.isEmpty() || productPrice.isEmpty() || imagePath.isEmpty()) {
            Toast.makeText(this, "Complete all product details", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), productName);
        RequestBody detailsBody = RequestBody.create(MediaType.parse("text/plain"), productDetails);
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), productPrice);
        RequestBody sportIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(sportId));

        File file = new File(imagePath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("product_image", file.getName(), fileBody);

        Call<CreateResponse> call = apiService.createProduct(nameBody, detailsBody, priceBody, sportIdBody, imagePart);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CreateResponse createResponse = response.body();
                    if (!createResponse.isError()) {
                        Toast.makeText(CreateProduct.this, "Product created successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreateProduct.this, CatergoryActivity.class));
                    } else {
                        Toast.makeText(CreateProduct.this, "Failed to create product: " + createResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateProduct.this, "Failed to create product", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {
                Toast.makeText(CreateProduct.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
