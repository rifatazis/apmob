package com.example.appmobuas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn1.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this,MainActivity.class));
        });
        binding.btn2.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this,CatergoryActivity.class));
        });
        binding.btn3.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this,CartActivity.class));
        });
        binding.btn4.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, WhistlistActivity.class));
        });
        binding.btn5.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this,AccountActivity.class));
        });
    }
}