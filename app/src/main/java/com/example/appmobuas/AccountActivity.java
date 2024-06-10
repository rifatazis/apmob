package com.example.appmobuas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.databinding.ActivityAccountBinding;

import java.io.IOException;
import java.util.HashMap;

public class AccountActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        if (!sessionManager.isLogIng()) {
            goLogin();
        }

        binding.changePicture.setOnClickListener(v -> {
            changePicture();
        });

        loadUserDetails();

        binding.btn1.setOnClickListener(v -> {
            startActivity(new Intent(AccountActivity.this, MainActivity.class));
        });
        binding.btn2.setOnClickListener(v -> {
            startActivity(new Intent(AccountActivity.this, CatergoryActivity.class));
        });
        binding.btn4.setOnClickListener(v -> {
            startActivity(new Intent(AccountActivity.this, WishtlistActivity.class));
        });
        binding.btn5.setOnClickListener(v -> {
            startActivity(new Intent(AccountActivity.this, AccountActivity.class));
        });

        binding.editProfil.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
        });

        binding.logout.setOnClickListener(v->{
            sessionManager.logout();
            goLogin();
        });
    }

    private void changePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void loadUserDetails() {
        HashMap<String, String> userDetails = sessionManager.getUser();
        String username = userDetails.get(SessionManager.USERNAME);
        String name = userDetails.get(SessionManager.NAME);
        String bio = userDetails.get(SessionManager.BIO);

        binding.tvUsername.setText(username);
        binding.tvname.setText(name);
        binding.tvBio.setText(bio);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                binding.image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void goLogin() {
        Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
        if (item.getItemId() == R.id.logout) {
            sessionManager.logout();
            goLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
