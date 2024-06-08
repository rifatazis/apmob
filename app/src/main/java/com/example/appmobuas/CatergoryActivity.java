package com.example.appmobuas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityCatergoryBinding;
import com.example.appmobuas.model.adapter.BrandsAdapter;
import com.example.appmobuas.model.adapter.SportsAdapter;
import com.example.appmobuas.model.brands.Brands;
import com.example.appmobuas.model.brands.BrandsData;
import com.example.appmobuas.model.sports.Sports;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatergoryActivity extends AppCompatActivity implements BrandsAdapter.OnBrandClickListener {
    private ActivityCatergoryBinding binding;
    private ArrayList<BrandsData> brands = new ArrayList<>();
    private BrandsAdapter brandsAdapter;
    private SportsAdapter sportsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatergoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn1.setOnClickListener(v -> {
            startActivity(new Intent(CatergoryActivity.this,MainActivity.class));
        });
        binding.btn2.setOnClickListener(v -> {
            startActivity(new Intent(CatergoryActivity.this,CatergoryActivity.class));
        });
        binding.btn3.setOnClickListener(v -> {
            startActivity(new Intent(CatergoryActivity.this,CartActivity.class));
        });
        binding.btn4.setOnClickListener(v -> {
            startActivity(new Intent(CatergoryActivity.this, WhistlistActivity.class));
        });
        binding.btn5.setOnClickListener(v -> {
            startActivity(new Intent(CatergoryActivity.this,AccountActivity.class));
        });

        fetchBrands();
        setupRecyclerBrands();
        setupRecyclerView();
    }

    private void fetchBrands() {
        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);
        Call<Brands> call = apiService.fetchBrands();
        binding.progressBarBrands.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Brands>() {
            @Override
            public void onResponse(Call<Brands> call, Response<Brands> response) {
                binding.progressBarBrands.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    brandsAdapter.updateBrands(response.body().getData());
                } else {
                    Toast.makeText(CatergoryActivity.this, "Gagal mengambil data merek", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Brands> call, Throwable t) {
                Toast.makeText(CatergoryActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerBrands() {
        brandsAdapter = new BrandsAdapter(this, brands, this);
        binding.recycleBrands.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recycleBrands.setAdapter(brandsAdapter);
    }

    private void fetchSportsByBrand(int brandId) {
        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);
        Call<Sports> call = apiService.fetchSports(brandId);
        call.enqueue(new Callback<Sports>() {
            @Override
            public void onResponse(Call<Sports> call, Response<Sports> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sportsAdapter.updateData(response.body().getData());
                } else {
                    Toast.makeText(CatergoryActivity.this, "Gagal mengambil data kategori", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sports> call, Throwable t) {
                Toast.makeText(CatergoryActivity.this, "Server tidak merespon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        sportsAdapter = new SportsAdapter(this, new ArrayList<>());
        binding.recycleCategory.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleCategory.setAdapter(sportsAdapter);
    }

    @Override
    public void onBrandClick(int brandId) {
        fetchSportsByBrand(brandId);
    }
}


