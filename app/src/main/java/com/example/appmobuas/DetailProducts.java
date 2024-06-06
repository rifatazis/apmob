package com.example.appmobuas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.model.products.ProductsData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProducts extends AppCompatActivity {

    private TextView tvProductName, tvProductPrice, tvProductDetails;
    private ImageView ivProductImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_products);

        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDetails = findViewById(R.id.tvProductDetails);
        ivProductImage = findViewById(R.id.ivProductImage);

        int productId = getIntent().getIntExtra("product_id", -1);
        if (productId == -1) {
            Toast.makeText(this, "ID Produk tidak valid", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        fetchProductDetails(productId);
    }

    private void fetchProductDetails(int productId) {
        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);
        Call<ProductsData> call = apiService.getProductDetails(productId);
        call.enqueue(new Callback<ProductsData>() {
            @Override
            public void onResponse(Call<ProductsData> call, Response<ProductsData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductsData product = response.body();
                    tvProductName.setText(product.getProductName());
                    tvProductPrice.setText(String.format("Rp %,d", product.getProductPrice()));
                    tvProductDetails.setText(product.getProductDetails());

                    if (product.getProductImage() != null && !product.getProductImage().isEmpty()) {
                        byte[] imageBytes = Base64.decode(product.getProductImage(), Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        ivProductImage.setImageBitmap(decodedImage);
                    } else {
                        ivProductImage.setImageResource(R.drawable.baseline_error_24);
                    }
                } else {
                    Toast.makeText(DetailProducts.this, "Gagal mengambil detail produk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductsData> call, Throwable t) {
                Toast.makeText(DetailProducts.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
