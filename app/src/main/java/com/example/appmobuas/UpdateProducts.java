package com.example.appmobuas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityUpdateProductsBinding;
import com.example.appmobuas.model.products.ProductsData;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProducts extends AppCompatActivity {

    private ActivityUpdateProductsBinding binding;
    private int productId;
    private int sportId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productId = getIntent().getIntExtra("id_product", -1);
        sportId = getIntent().getIntExtra("sport_id", -1);
        String productName = getIntent().getStringExtra("product_name");
        String productDetails = getIntent().getStringExtra("product_details");
        int productPrice = getIntent().getIntExtra("product_price", -1);
        String productImage = getIntent().getStringExtra("product_image");

        if (productId == -1 || sportId == -1) {
            Toast.makeText(this, "ID produk atau olahraga tidak valid", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.etProductName.setText(productName);
        binding.etProductDetails.setText(productDetails);
        binding.etProductPrice.setText(formatRupiah(productPrice));
        if (productImage != null && !productImage.isEmpty()) {
            byte[] imageBytes = Base64.decode(productImage.substring(productImage.indexOf(",") + 1), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            binding.ivProductImage.setImageBitmap(decodedImage);
        }

        binding.btnUpdateProduct.setOnClickListener(v -> {
            String newProductName = binding.etProductName.getText().toString().trim();
            String newProductDetails = binding.etProductDetails.getText().toString().trim();
            String newProductPriceStr = binding.etProductPrice.getText().toString().replaceAll("[Rp,.\\s]", "").trim();

            if (newProductName.isEmpty() || newProductDetails.isEmpty() || newProductPriceStr.isEmpty()) {
                Toast.makeText(UpdateProducts.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int newProductPrice = Integer.parseInt(newProductPriceStr);

            ProductsData updatedProduct = new ProductsData();
            updatedProduct.setIdProduct(productId);
            updatedProduct.setProductName(newProductName);
            updatedProduct.setProductDetails(newProductDetails);
            updatedProduct.setProductPrice(newProductPrice);
            updatedProduct.setSportId(sportId);

            updateProductDetails(productId, updatedProduct);
        });


    }

    private String formatRupiah(int number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format((double) number);
    }

    private void updateProductDetails(int productId, ProductsData updatedProductData) {
        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);
        Call<ProductsData> call = apiService.updateProduct(productId, updatedProductData);
        call.enqueue(new Callback<ProductsData>() {
            @Override
            public void onResponse(Call<ProductsData> call, Response<ProductsData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductsData updatedProduct = response.body();
                    Toast.makeText(UpdateProducts.this, "Produk berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateProducts.this, "Gagal memperbarui produk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductsData> call, Throwable t) {
                Toast.makeText(UpdateProducts.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
