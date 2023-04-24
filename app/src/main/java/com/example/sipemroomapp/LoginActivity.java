package com.example.sipemroomapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sipemroomapp.Model.UserModel;
import com.example.sipemroomapp.util.AuthInterface;
import com.example.sipemroomapp.util.DataApi;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView btnRegister;
    EditText etUsername, etPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegister = findViewById(R.id.btnRegist);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPasword);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        if (sharedPreferences.getBoolean("logged_in", false)) {
            if (sharedPreferences.getString("role",null).equals("2")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().isEmpty()) {
                    Toasty.error(getApplicationContext(), "Field username tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etPassword.getText().toString().isEmpty()){
                    Toasty.error(getApplicationContext(), "Field password tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                }else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle("Loading").setMessage("Memeriksa username dan password...").setCancelable(false);
                    AlertDialog progressDialog = alert.create();
                    progressDialog.show();

                    AuthInterface authInterface = DataApi.getClient().create(AuthInterface.class);
                    authInterface.login(etUsername.getText().toString(), etPassword.getText().toString()).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            UserModel userModel = response.body();
                            if (response.isSuccessful() && userModel.getCode().equals("200")) {
                                editor.putString("user_id", userModel.getUserId());
                                editor.putString("nama", userModel.getNama());
                                editor.putString("username", userModel.getUsername());
                                editor.putString("role", userModel.getRole());
                                editor.putBoolean("logged_in", true);
                                editor.apply();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                Toasty.success(getApplicationContext(), "Selamat datang " + userModel.getNama(), Toasty.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else {
                                Toasty.error(getApplicationContext(), userModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }


                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Toasty.error(getApplicationContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    });

                }
            }
        });




        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}