package com.example.appmobuas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.databinding.ActivityWhistlistBinding;

public class WhistlistActivity extends AppCompatActivity {

    private ActivityWhistlistBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWhistlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}