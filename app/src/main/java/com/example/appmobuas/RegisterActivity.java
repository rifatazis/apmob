package com.example.appmobuas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityRegisterBinding;
import com.example.appmobuas.model.register.Register;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.linkLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });


        binding.btnRegister.setOnClickListener(v -> {
            String Username = binding.etUser.getText().toString().trim();
            String Name = binding.etName.getText().toString().trim();
            String Password = binding.etPassword.getText().toString().trim();
            register(Username,Name,Password);
        });
    }
    private void register(String username, String name, String password){
        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);
        Call<Register> call = apiService.RegisterResponse(username, name, password);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.isSuccessful() && response.body() != null){
//
//                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("username", username);
//                    editor.putString("name", name);
//                    editor.apply();


                    Snackbar.make(findViewById(android.R.id.content), "Akun berhasil dibuat", Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Snackbar.make(findViewById(android.R.id.content), response.body().toString(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), "Gagal Register : " + t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}