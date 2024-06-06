package com.example.appmobuas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityProductsBinding;
import com.example.appmobuas.model.adapter.ProductsAdapter;
import com.example.appmobuas.model.products.Products;
import com.example.appmobuas.model.products.ProductsData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {
    private ActivityProductsBinding binding;
    private ProductsAdapter productsAdapter;
    private ArrayList<ProductsData> productList = new ArrayList<>();
    private int sportId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn1.setOnClickListener(v -> {
            startActivity(new Intent(ProductsActivity.this,MainActivity.class));
        });
        binding.btn2.setOnClickListener(v -> {
            startActivity(new Intent(ProductsActivity.this,CatergoryActivity.class));
        });
        binding.btn3.setOnClickListener(v -> {
            startActivity(new Intent(ProductsActivity.this,CartActivity.class));
        });
        binding.btn4.setOnClickListener(v -> {
            startActivity(new Intent(ProductsActivity.this, WhistlistActivity.class));
        });
        binding.btn5.setOnClickListener(v -> {
            startActivity(new Intent(ProductsActivity.this,AccountActivity.class));
        });

        sportId = getIntent().getIntExtra("sport_id", -1);
        if (sportId == -1) {
            Toast.makeText(this, "ID Olahraga tidak valid", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.btnNewProduct.setOnClickListener(v -> {
            startActivity(new Intent(ProductsActivity.this, CreateProduct.class));
        });


        setupRecyclerView();
        fetchProducts(sportId);
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
