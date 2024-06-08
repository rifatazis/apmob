package com.example.appmobuas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityProfileBinding;
import com.example.appmobuas.model.profil.Profil;
import com.example.appmobuas.model.profil.ProfilData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        apiService = ApiConfig.getConfig().create(ApiService.class);
        loadUserDetails();

        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = binding.edittextUsername.getText().toString();
                String newName = binding.edittextName.getText().toString();
                String newBio = binding.edittextBio.getText().toString();

                updateProfile(newUsername, newName, newBio);
            }
        });
    }

    private void loadUserDetails() {
        HashMap<String, String> userDetails = sessionManager.getUser();
        String username = userDetails.get(SessionManager.USERNAME);
        String name = userDetails.get(SessionManager.NAME);
        String bio = userDetails.get(SessionManager.BIO);

        binding.edittextUsername.setText(username);
        binding.edittextName.setText(name);
        binding.edittextBio.setText(bio);
    }

    private void updateProfile(String username, String name, String bio) {
        HashMap<String, String> userDetails = sessionManager.getUser();
        String userId = userDetails.get(SessionManager.USER_ID);

        if (userId == null || userId.isEmpty() || username.isEmpty() || name.isEmpty() || bio.isEmpty()) {
            Toast.makeText(UpdateProfileActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Profil> call = apiService.updateProfile(userId, username, name, bio);
        call.enqueue(new Callback<Profil>() {
            @Override
            public void onResponse(Call<Profil> call, Response<Profil> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Profil profil = response.body();
                    if (!profil.isError()) {
                        ProfilData profilData = profil.getData();

                        binding.edittextUsername.setText(profilData.getUsername());
                        binding.edittextName.setText(profilData.getName());
                        binding.edittextBio.setText(profilData.getBio());

                        sessionManager.updateUser(profilData.getUsername(), profilData.getName(), profilData.getBio());

                        Toast.makeText(UpdateProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UpdateProfileActivity.this, AccountActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("ProfileUpdate", "Failed: " + profil.getMessage());
                        Toast.makeText(UpdateProfileActivity.this, "Failed to update profile: " + profil.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("ProfileUpdate", "Error: " + response.code() + " " + response.message());
                    Toast.makeText(UpdateProfileActivity.this, "Failed to update. Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Profil> call, Throwable t) {
                Log.e("ProfileUpdate", "Failure: " + t.getMessage(), t);
                Toast.makeText(UpdateProfileActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
