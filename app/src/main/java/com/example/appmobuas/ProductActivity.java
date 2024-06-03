package com.example.appmobuas;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityProductBinding;
import com.example.appmobuas.model.adapter.ProductAdapter;
import com.example.appmobuas.model.product.DataItem;
import com.example.appmobuas.model.product.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding binding;
    private ArrayList<DataItem> products = new ArrayList<>();

    private ProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();
        fetchProducts();

    }

    private void setupRecyclerView() {
        adapter = new ProductAdapter(this,products);
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recycleView.setAdapter(adapter);
    }

    private void fetchProducts() {
        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);
        Call<Product> call = apiService.fetchProducts();
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null){
                    adapter.updateData(response.body().getData());
                }else {
                    Toast.makeText(ProductActivity.this, "Failed", Toast  .LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }


}