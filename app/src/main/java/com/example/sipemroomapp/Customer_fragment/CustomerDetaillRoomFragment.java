package com.example.sipemroomapp.Customer_fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.UserModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.CustomerInterface;
import com.example.sipemroomapp.util.DataApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerDetaillRoomFragment extends Fragment {
    TextView tvRoomName, tvTotalPerson, tvDekorasi, tvTahun, tvStatus;
    Button btnSewa, btnKembali;
    ImageView ivRoom;
    Integer status, roomId;
    String gambar, room_name, dekorasi, tahun, denda, harga;
    private FloatingActionButton fabChat;
    SharedPreferences sharedPreferences;
    private CustomerInterface customerInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_custome_detaill_room, container, false);
       customerInterface  = DataApi.getClient().create(CustomerInterface.class);
       tvRoomName = view.findViewById(R.id.tvRoomName);
       tvTotalPerson = view.findViewById(R.id.tvTotalPerson);
       tvDekorasi  = view.findViewById(R.id.tvDekorasi);
       tvTahun = view.findViewById(R.id.tvTahun);
       fabChat = view.findViewById(R.id.fabChat);
       tvStatus = view.findViewById(R.id.tvStatus);
       btnSewa = view.findViewById(R.id.btnSewa);
       ivRoom = view.findViewById(R.id.ivRoom);
       btnKembali = view.findViewById(R.id.btnKembali);


        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);

       roomId = getArguments().getInt("room_id");
       status = getArguments().getInt("status");
       gambar = getArguments().getString("gambar");
       room_name = getArguments().getString("room_name");
       dekorasi = getArguments().getString("dekorasi");
       tahun = getArguments().getString("tahun");
       harga = getArguments().getString("harga");
       denda = getArguments().getString("denda");

       tvTotalPerson.setText(String.valueOf(getArguments().getInt("total_person")) + " Orang");

       if (status == 0) {
           btnSewa.setEnabled(false);
           btnSewa.setBackgroundColor(getContext().getResources().getColor(R.color.red));
           btnSewa.setText("Telah sewa");
           btnSewa.setTextColor(getContext().getResources().getColor(R.color.white));
           tvStatus.setText("Tidak tersedia / sedang dirental");
           getUserOrder();
       }else {
           tvStatus.setText("Tersedia");
       }

        Glide.with(getContext())
                        .load(gambar).dontAnimate().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).into(ivRoom);


       tvRoomName.setText(room_name);
       tvDekorasi.setText(dekorasi);
       tvTahun.setText(tahun);

       fabChat.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openWhatsApp();
           }
       });

       btnKembali.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().onBackPressed();
           }
       });

        btnSewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialogSewa = new Dialog(getContext());
                dialogSewa.setContentView(R.layout.layout_sewa);
                dialogSewa.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final TextView tvTanggalRental, tvTglKembali;
                final Button btnSewa, btnBatal;
                btnSewa = dialogSewa.findViewById(R.id.btnSewa);
                btnBatal = dialogSewa.findViewById(R.id.btnCancel);
                tvTanggalRental = dialogSewa.findViewById(R.id.tvTanggalRental);
                tvTglKembali = dialogSewa.findViewById(R.id.tvTanggalKembali);


                tvTanggalRental.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(tvTanggalRental);
                    }
                });
                tvTglKembali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(tvTglKembali);
                    }
                });
                dialogSewa.show();

                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSewa.dismiss();
                    }
                });

                btnSewa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (tvTanggalRental.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "Field tanggal rental tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                        }else if (tvTglKembali.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "Field tanggal kembali tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setTitle("Loading").setMessage("Menyimppan data...").setCancelable(false);
                            AlertDialog progressBar = alert.create();
                            progressBar.show();

                            HashMap map = new HashMap();
                            map.put("id_customer", RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("user_id", null)));
                            map.put("room_id", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(roomId)));
                            map.put("tgl_rental", RequestBody.create(MediaType.parse("text/plain"), tvTanggalRental.getText().toString()));
                            map.put("tgl_kembali",RequestBody.create(MediaType.parse("text/plain"), tvTglKembali.getText().toString()));
                            map.put("harga", RequestBody.create(MediaType.parse("text/plain"), harga));
                            map.put("denda",RequestBody.create(MediaType.parse("text/plain"), denda));

                            CustomerInterface customerInterface = DataApi.getClient().create(CustomerInterface.class);
                            customerInterface.sewa(map).enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    ResponseModel responseModel = response.body();
                                    if (response.isSuccessful() && responseModel.getCode() == 200) {
                                        ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameCustomer, new CustomerMyTransactionsFragment()).commit();
                                        Toasty.success(getContext(), "Transaksi berhasil", Toasty.LENGTH_SHORT).show();
                                        progressBar.dismiss();
                                        dialogSewa.dismiss();
                                    }else {
                                        Toasty.error(getContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                        progressBar.dismiss();
                                    }

                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    Log.e("adasd", "onFailure: ",t );
                                    Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                                    progressBar.dismiss();

                                }
                            });

                        }

                    }
                });



            }
        });



       return view;
    }

    private void getUserOrder() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data....");
        AlertDialog progressDialog = alert.create();
        progressDialog.show();

        customerInterface.getUserOrder(String.valueOf(getArguments().getInt("room_id"))).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.layout_notif);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    final Button btnOke = dialog.findViewById(R.id.btnOke);
                    final TextView tvNamaLengkap, tvNoTelp;
                    tvNamaLengkap = dialog.findViewById(R.id.tvNamaLengkap);
                    tvNoTelp = dialog.findViewById(R.id.tvNoTelp);

                    tvNamaLengkap.setText(response.body().getNama().toString());
                    tvNoTelp.setText(response.body().getNoTelp());


                    dialog.show();

                    btnOke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }else {

                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }


    private void datePicker(TextView tvDatePicker) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String monthFormatted, dayFormatted;
                if (month < 10) {
                    monthFormatted = String.format("%02d", month +1);
                }else {
                    monthFormatted = String.valueOf(month + 1);
                }

                if (dayOfMonth <10) {
                    dayFormatted = String.format("%02d", dayOfMonth + 1);
                }else {
                    dayFormatted = String.valueOf(dayOfMonth);
                }

                tvDatePicker.setText(year + "-" +monthFormatted+"-"+dayFormatted);
            }
        });

        datePickerDialog.show();
    }

    private void openWhatsApp() {
        String phoneNumber = "62895704370441";
        String message = "Halo, Saya ingin bertanya apakah ruangan " + getArguments().getString("room_name") + " tersedia?";


        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}