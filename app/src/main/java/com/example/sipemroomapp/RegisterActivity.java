package com.example.sipemroomapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.util.AuthInterface;
import com.example.sipemroomapp.util.DataApi;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Spinner spGender;
    EditText etNama, etUsername, etAlamat, etTelepon, etKtp, etPass;
    Button btnRegister;

    String [] gender = {"Laki-laki", "Perempuan"};
    String jk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spGender = findViewById(R.id.spGender);
        etNama = findViewById(R.id.etNama);
        etUsername = findViewById(R.id.etUsername);
        etAlamat = findViewById(R.id.etAlamat);
        etTelepon = findViewById(R.id.etNoTelp);
        etKtp = findViewById(R.id.etNoKtp);
        etPass = findViewById(R.id.etPasword);
        btnRegister = findViewById(R.id.btnRegist);

        // create spinner adapter
        ArrayAdapter genderAdapter  = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, gender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if (genderAdapter.getItem(position).equals("Laki-laki")) {
                  jk = "laki-laki";
              }else {
                  jk = "perempuan";
              }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etNama.getText().toString().isEmpty()) {
                    Toasty.error(getApplicationContext(), "Field nama tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                } else if (etUsername.getText().toString().isEmpty()) {
                    Toasty.error(getApplicationContext(), "Field username tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etAlamat.getText().toString().isEmpty()){
                    Toasty.error(getApplicationContext(), "Field alamat tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etTelepon.getText().toString().isEmpty()){
                    Toasty.error(getApplicationContext(), "Field nomor telepon tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                } else if (etKtp.getText().toString().isEmpty()) {
                    Toasty.error(getApplicationContext(), "Field no ktp tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                }else if (etPass.getText().toString().isEmpty()) {
                    Toasty.error(getApplicationContext(), "Field password tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                }else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                    alert.setCancelable(false).setMessage("Mengirim data").setTitle("Register");
                    AlertDialog progressDialog = alert.create();
                    progressDialog.show();

                    HashMap map = new HashMap();
                    map.put("username", RequestBody.create(MediaType.parse("text/plain"), etUsername.getText().toString()));
                    map.put("nama", RequestBody.create(MediaType.parse("text/plain"), etNama.getText().toString()));
                    map.put("alamat", RequestBody.create(MediaType.parse("text/plain"), etAlamat.getText().toString()));
                    map.put("gender", RequestBody.create(MediaType.parse("text/plain"), jk));
                    map.put("no_telepon", RequestBody.create(MediaType.parse("text/plain"), etTelepon.getText().toString()));
                    map.put("no_ktp", RequestBody.create(MediaType.parse("text/plain"), etKtp.getText().toString()));
                    map.put("password", RequestBody.create(MediaType.parse("text/plain"), etPass.getText().toString()));

                    AuthInterface authInterface = DataApi.getClient().create(AuthInterface.class);
                    authInterface.register(map).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            ResponseModel responseModel = response.body();
                            if (response.isSuccessful() && responseModel.getCode() == 200) {
                                Toasty.success(getApplicationContext(), "Berhasil registrasi", Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                progressDialog.dismiss();
                            }else {
                                Toasty.error(getApplicationContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toasty.error(getApplicationContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    });
                }


            }
        });
    }

    private void register() {

    }
}