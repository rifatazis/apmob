package com.example.appmobuas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.databinding.ActivityAccountBinding;

public class AccountActivity extends AppCompatActivity {
    SessionManager sessionmanager ;
    String username,name,bio;
    private ActivityAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionmanager = new SessionManager(this);
        if (!sessionmanager.isLogIng()){
            goLogin();
        }

        username = sessionmanager.getUser().get(SessionManager.USERNAME);
        name = sessionmanager.getUser().get(SessionManager.NAME);
        bio = sessionmanager.getUser().get(SessionManager.BIO);

        binding.tvUsername.setText(username);
        binding.tvname.setText(name);
        binding.tvBio.setText(bio);

        binding.editProfil.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void goLogin() {
        Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            sessionmanager.logout();
            goLogin();
        }
        return super.onOptionsItemSelected(item);
    }
}