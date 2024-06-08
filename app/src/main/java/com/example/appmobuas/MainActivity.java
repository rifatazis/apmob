package com.example.appmobuas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    SessionManager sessionmanager ;
    String username;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn1.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,MainActivity.class));
        });
        binding.btn2.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,CatergoryActivity.class));
        });
        binding.btn3.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,CartActivity.class));
        });
        binding.btn4.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WishtlistActivity.class));
        });
        binding.btn5.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,AccountActivity.class));
        });

        sessionmanager = new SessionManager(this);
        username = sessionmanager.getUser().get(SessionManager.NAME);
        binding.welcome.setText("Hi,Welcome "+username);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            sessionmanager.logout();
            goLogin();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

}
