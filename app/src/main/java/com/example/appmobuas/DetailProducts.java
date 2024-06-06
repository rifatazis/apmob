package com.example.appmobuas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        Intent intent = getIntent();
        if (intent != null) {
            String productName = intent.getStringExtra("product_name");
            int productPrice = intent.getIntExtra("product_price", 0);
            String productDetails = intent.getStringExtra("product_details");
            String productImage = intent.getStringExtra("product_image");

            tvProductName.setText(productName);
            tvProductPrice.setText(String.format("Rp %,d", productPrice));
            tvProductDetails.setText(productDetails);

            if (productImage != null && !productImage.isEmpty()) {
                byte[] imageBytes = Base64.decode(productImage, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                ivProductImage.setImageBitmap(decodedImage);
            } else {
                ivProductImage.setImageResource(R.drawable.baseline_error_24);
            }
        } else {
            Toast.makeText(this, "Data produk tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
