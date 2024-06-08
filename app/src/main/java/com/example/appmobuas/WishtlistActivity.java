package com.example.appmobuas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.databinding.ActivityWhistlistBinding;

public class WishtlistActivity extends AppCompatActivity {

    private ActivityWhistlistBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWhistlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn1.setOnClickListener(v -> {
            startActivity(new Intent(WishtlistActivity.this,MainActivity.class));
        });
        binding.btn2.setOnClickListener(v -> {
            startActivity(new Intent(WishtlistActivity.this,CatergoryActivity.class));
        });
        binding.btn3.setOnClickListener(v -> {
            startActivity(new Intent(WishtlistActivity.this,CartActivity.class));
        });
        binding.btn4.setOnClickListener(v -> {
            startActivity(new Intent(WishtlistActivity.this, WishtlistActivity.class));
        });
        binding.btn5.setOnClickListener(v -> {
            startActivity(new Intent(WishtlistActivity.this,AccountActivity.class));
        });

        wishlist();

    }

    private void wishlist() {

    }
}