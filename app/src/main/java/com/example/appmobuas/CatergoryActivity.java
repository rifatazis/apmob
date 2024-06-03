package com.example.appmobuas;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityCatergoryBinding;
import com.example.appmobuas.model.adapter.CategoryAdapter;
import com.example.appmobuas.model.category.Category;
import com.example.appmobuas.model.category.DataCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatergoryActivity extends AppCompatActivity {

    private ActivityCatergoryBinding binding;
    private ArrayList<DataCategory> category = new ArrayList<>();
    private CategoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatergoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();
        fetchProduct();
    }

    private void fetchProduct() {
        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);
        Call<Category> call = apiService.fetchProduct();
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful() && response.body() != null){
                    adapter.updateData(response.body().getData());
                }else {
                    Toast.makeText(CatergoryActivity.this, "Failed", Toast  .LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }

    private void setupRecyclerView() {
        adapter = new CategoryAdapter(this,category);
        binding.recycleCategory.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleCategory.setAdapter(adapter);
    }
}