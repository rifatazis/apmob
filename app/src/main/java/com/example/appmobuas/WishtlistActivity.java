package com.example.appmobuas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appmobuas.databinding.ActivityWhistlistBinding;
import com.example.appmobuas.model.adapter.WishlistAdapter;
import com.example.appmobuas.model.wishlist.WishlistItem;

import java.util.ArrayList;

public class WishtlistActivity extends AppCompatActivity {

    private ActivityWhistlistBinding binding;
    private WishlistAdapter wishlistAdapter;
    private ArrayList<WishlistItem> wishlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWhistlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn1.setOnClickListener(v -> {
            startActivity(new Intent(WishtlistActivity.this, MainActivity.class));
        });
        binding.btn2.setOnClickListener(v -> {
            startActivity(new Intent(WishtlistActivity.this, CatergoryActivity.class));
        });
        binding.btn4.setOnClickListener(v -> {
            startActivity(new Intent(WishtlistActivity.this, WishtlistActivity.class));
        });
        binding.btn5.setOnClickListener(v -> {
            startActivity(new Intent(WishtlistActivity.this, AccountActivity.class));
        });


        setupRecyclerView();
    }

    private void setupRecyclerView() {
        wishlist = DetailProducts.wishlist;
        wishlistAdapter = new WishlistAdapter(this, wishlist);
        binding.wishlistView.setLayoutManager(new LinearLayoutManager(this));
        binding.wishlistView.setAdapter(wishlistAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wishlistAdapter.notifyDataSetChanged();
    }
}
