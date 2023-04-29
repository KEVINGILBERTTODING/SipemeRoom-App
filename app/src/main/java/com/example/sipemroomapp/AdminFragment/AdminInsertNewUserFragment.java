package com.example.sipemroomapp.AdminFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.AuthInterface;
import com.example.sipemroomapp.util.DataApi;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminInsertNewUserFragment extends Fragment {
    Spinner spGender;
    EditText etNama, etUsername, etAlamat, etTelepon, etKtp, etPass;
    Button btnRegister;

    String [] gender = {"Laki-laki", "Perempuan"};
    String jk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_insert_new_user, container, false);
        spGender = view.findViewById(R.id.spGender);
        etNama = view.findViewById(R.id.etNama);
        etUsername = view.findViewById(R.id.etUsername);
        etAlamat = view.findViewById(R.id.etAlamat);
        etTelepon = view.findViewById(R.id.etNoTelp);
        etKtp = view.findViewById(R.id.etNoKtp);
        etPass = view.findViewById(R.id.etPasword);
        btnRegister = view.findViewById(R.id.btnRegist);




        // create spinner adapter
        ArrayAdapter genderAdapter  = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, gender);
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
                    Toasty.error(getContext(), "Field nama tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                } else if (etUsername.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field username tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etAlamat.getText().toString().isEmpty()){
                    Toasty.error(getContext(), "Field alamat tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etTelepon.getText().toString().isEmpty()){
                    Toasty.error(getContext(), "Field nomor telepon tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                } else if (etKtp.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field no ktp tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                }else if (etPass.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field password tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                }else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
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
                                Toasty.success(getContext(), "Berhasil registrasi", Toasty.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                                progressDialog.dismiss();
                            }else {
                                Toasty.error(getContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    });
                }


            }
        });


        return view;
    }
}