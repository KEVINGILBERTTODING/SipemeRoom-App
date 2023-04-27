package com.example.sipemroomapp.Customer_fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.sipemroomapp.LoginActivity;
import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.UserModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.CustomerInterface;
import com.example.sipemroomapp.util.DataApi;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerProfileFragment extends Fragment {
    TextView tvUsername;
    CardView cvMenuProfile, cvMenuPassword, cvMenuLogOut;
    SharedPreferences sharedPreferences;
    String username, userId;
    CustomerInterface customerInterface;
    String [] opsiGender = {"Laki-laki", "Perempuan"};
    private String gender;
    SharedPreferences.Editor editor;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_profile, container, false);
        tvUsername = view.findViewById(R.id.tvUsername);
        cvMenuLogOut = view.findViewById(R.id.cvKeluar);
        cvMenuProfile = view.findViewById(R.id.cvUbahProfile);
        cvMenuPassword = view.findViewById(R.id.cvUbahPassword);

        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("nama", null);
        userId = sharedPreferences.getString("user_id", null);
        customerInterface = DataApi.getClient().create(CustomerInterface.class);
        editor = sharedPreferences.edit();


        tvUsername.setText(username);

        cvMenuPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_ubah_password);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCanceledOnTouchOutside(false);
                final Button btnSimpan, btnBtal;
                final EditText etOldPass, etNewPass, etPassKonfir;
                etOldPass = dialog.findViewById(R.id.etOldPass);
                etNewPass = dialog.findViewById(R.id.etNewPass);
                etPassKonfir = dialog.findViewById(R.id.etPassKonfir);
                btnSimpan = dialog.findViewById(R.id.btnSimpan);
                btnBtal = dialog.findViewById(R.id.btnCancel);
                dialog.show();
                btnBtal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etOldPass.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "Password lama tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                        }else {
                            if (!etNewPass.getText().toString().equals(etPassKonfir.getText().toString())) {

                                Toasty.error(getContext(), "Password tidak sesuai", Toasty.LENGTH_SHORT).show();
                            }else {
                                AlertDialog.Builder alert  = new AlertDialog.Builder(getContext());
                                alert.setTitle("Loading").setMessage("Mengubah password").setCancelable(false);
                                AlertDialog pd = alert.create();
                                pd.show();

                                customerInterface.ubahPassword(
                                        userId,
                                        etOldPass.getText().toString(),
                                        etPassKonfir.getText().toString()
                                ).enqueue(new Callback<ResponseModel>() {
                                    @Override
                                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                        ResponseModel responseModel = response.body();
                                        if (response.isSuccessful() && responseModel.getStatus() == true) {
                                            Toasty.success(getContext(), "Berhasil mengubah password", Toasty.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            pd.dismiss();

                                        }else {
                                            Toasty.error(getContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();

                                            pd.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                                        Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();

                                        pd.dismiss();

                                    }
                                });




                            }
                        }
                    }
                });
            }
        });

        cvMenuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter genderAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, opsiGender);
                genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_ubah_profile);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final Spinner spGender = dialog.findViewById(R.id.spGender2);
                final EditText etUsername, etNama, etAlamat, etTelepon, etKtp;
                final Button btnSImpan, btnBatal;

                spGender.setAdapter(genderAdapter);
                etUsername = dialog.findViewById(R.id.etUsername);
                etNama = dialog.findViewById(R.id.etNama);
                etAlamat = dialog.findViewById(R.id.etAlamat);
                etTelepon = dialog.findViewById(R.id.etNoTelp);
                etKtp = dialog.findViewById(R.id.etNoKtp);
                btnSImpan = dialog.findViewById(R.id.btnSimpan);
                btnBatal = dialog.findViewById(R.id.btnCancel);

                AlertDialog.Builder alert2 = new AlertDialog.Builder(getContext());
                alert2.setCancelable(false).setMessage("Memuat data...").setTitle("Loading");
                AlertDialog progressDialog = alert2.create();
                progressDialog.show();

                //load data
                customerInterface.getUserById(userId).enqueue(new Callback<List<UserModel>>() {
                    @Override
                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                        if (response.isSuccessful() && response.body().size() > 0) {
                            List<UserModel> userModel = response.body();
                            etUsername.setText(userModel.get(0).getUsername());
                            etNama.setText(userModel.get(0).getNama());
                            etAlamat.setText(userModel.get(0).getAlamat());
                            etTelepon.setText(userModel.get(0).getNoTelp());
                            etKtp.setText(userModel.get(0).getNoKtp());
                            progressDialog.dismiss();

                        }else {
                            Toasty.error(getContext(), "Something went wrong", Toasty.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserModel>> call, Throwable t) {
                        Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                });

                dialog.show();

                spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (genderAdapter.getItem(position).equals("Laki-laki")) {
                            gender = "laki-laki";
                        }else {
                            gender = "perempuan";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnSImpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        if (etUsername.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "Username tidak boleh kosong", Toasty.LENGTH_SHORT);
                        }else if (etNama.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "nama tidak boleh kosong", Toasty.LENGTH_SHORT);

                        }else if (etAlamat.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "Alamat tidak boleh kosong", Toasty.LENGTH_SHORT);

                        }else if (etTelepon.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "No telepon tidak boleh kosong", Toasty.LENGTH_SHORT);

                        } else if (etKtp.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "No Ktp tidak boleh kosong", Toasty.LENGTH_SHORT);

                        }else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setCancelable(false).setMessage("Menyimpan perubahan").setTitle("Loading");
                            AlertDialog pd = alert.create();
                            pd.show();

                            HashMap map = new HashMap();
                            map.put("username", RequestBody.create(MediaType.parse("text/plain"), etUsername.getText().toString()));
                            map.put("user_id", RequestBody.create(MediaType.parse("text/plain"), userId));
                            map.put("nama", RequestBody.create(MediaType.parse("text/plain"), etNama.getText().toString()));
                            map.put("alamat", RequestBody.create(MediaType.parse("text/plain"), etAlamat.getText().toString()));
                            map.put("gender", RequestBody.create(MediaType.parse("text/plain"), gender));
                            map.put("no_telepon", RequestBody.create(MediaType.parse("text/plain"), etTelepon.getText().toString()));
                            map.put("no_ktp", RequestBody.create(MediaType.parse("text/plain"), etKtp.getText().toString()));


                            customerInterface.updateProfile(map).enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    ResponseModel responseModel = response.body();
                                    if (response.isSuccessful() && responseModel.getStatus() == true){
                                        Toasty.success(getContext(), "Berhasil mengubah profil", Toasty.LENGTH_SHORT).show();
                                        pd.dismiss();
                                        dialog.dismiss();
                                    }else {
                                        Toasty.error(getContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                        pd.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                                    pd.dismiss();

                                }
                            });
                        }
                    }
                });




            }
        });

        cvMenuLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Peringatan").setMessage("Apakah anda yakin ingin keluar?").setCancelable(false);
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.clear().apply();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();

            }
        });

        return view;
    }
}