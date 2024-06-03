package com.example.appmobuas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobuas.api.ApiConfig;
import com.example.appmobuas.api.ApiService;
import com.example.appmobuas.databinding.ActivityLoginBinding;
import com.example.appmobuas.model.login.Login;
import com.example.appmobuas.model.login.LoginData;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String Username;
    private String Password;
    private SessionManager sessionmanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvCreate.setOnClickListener(v -> {
            if (v.getId() == R.id.tvCreate){
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            Username = binding.etUser.getText().toString();
            Password = binding.etPassword.getText().toString();
            login(Username, Password);
        });
    }
    public void login(String Username, String Password){
        ApiService apiService = ApiConfig.getConfig().create(ApiService.class);
        Call<Login> call = apiService.LoginResponse(Username,Password);
        call.enqueue(new Callback<Login>(){

            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()){

                    sessionmanager = new SessionManager(LoginActivity.this);
                    LoginData loginData = response.body().getData();
                    sessionmanager.loginSession(loginData);

                    Snackbar.make(findViewById(android.R.id.content), "Login Succes", Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Snackbar.make(findViewById(android.R.id.content), "Incorrect Username and Password", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), "Login Failed : " + t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}

