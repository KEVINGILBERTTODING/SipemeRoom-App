package com.example.sipemroomapp.CustomerAdapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sipemroomapp.Customer_fragment.CustomerDetaillRoomFragment;
import com.example.sipemroomapp.Customer_fragment.CustomerMyTransactionsFragment;
import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.RuanganModel;
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

public class MainRoomAdapter extends RecyclerView.Adapter<MainRoomAdapter.ViewHolder> {
    Context context;
    List<RuanganModel>ruanganModelList;
    SharedPreferences sharedPreferences;

    public MainRoomAdapter(Context context, List<RuanganModel> ruanganModelList) {
        this.context = context;
        this.ruanganModelList = ruanganModelList;
    }

    @NonNull
    @Override
    public MainRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_main_available_room,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRoomAdapter.ViewHolder holder, int position) {
        holder.tvRoomName.setText(ruanganModelList.get(position).getMerk());
        Glide.with(context)
                .load(ruanganModelList.get(position).getGambar())
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(holder.ivRoom);

        if (ruanganModelList.get(position).getStatus() == 1) {
            holder.btnSewa.setEnabled(true);
        }else{
            holder.btnSewa.setText("Tidak tersedia");
            holder.btnSewa.setEnabled(false);
            holder.btnSewa.setTextColor(context.getResources().getColor(R.color.white));
            holder.btnSewa.setBackgroundColor(context.getResources().getColor(R.color.red));
        }


        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CustomerDetaillRoomFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("room_id", ruanganModelList.get(holder.getAdapterPosition()).getIdMobil());
                bundle.putString("gambar", ruanganModelList.get(holder.getAdapterPosition()).getGambar());
                bundle.putString("room_name", ruanganModelList.get(holder.getAdapterPosition()).getMerk());
                bundle.putString("dekorasi", ruanganModelList.get(holder.getAdapterPosition()).getWarna());
                bundle.putString("tahun", ruanganModelList.get(holder.getAdapterPosition()).getTahun());
                bundle.putInt("status", ruanganModelList.get(holder.getAdapterPosition()).getStatus());
                bundle.putString("harga", ruanganModelList.get(holder.getAdapterPosition()).getHarga());
                bundle.putString("denda", ruanganModelList.get(holder.getAdapterPosition()).getDenda());
                fragment.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frameCustomer, fragment).addToBackStack(null).commit();
            }
        });

        holder.btnSewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialogSewa = new Dialog(context);
                dialogSewa.setContentView(R.layout.layout_sewa);
                dialogSewa.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final Button btnSewa, btnBatal;
                btnSewa = dialogSewa.findViewById(R.id.btnSewa);
                btnBatal = dialogSewa.findViewById(R.id.btnCancel);
                final TextView tvTanggalRental, tvTglKembali;
                tvTanggalRental = dialogSewa.findViewById(R.id.tvTanggalRental);
                tvTglKembali = dialogSewa.findViewById(R.id.tvTanggalKembali);
                sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);

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



                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSewa.dismiss();
                    }
                });

                dialogSewa.show();

                btnSewa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvTanggalRental.getText().toString().isEmpty()) {
                            Toasty.error(context, "Field tanggal rental tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                        }else if (tvTglKembali.getText().toString().isEmpty()) {
                            Toasty.error(context, "Field tanggal kembali tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("Loading").setMessage("Menyimppan data...").setCancelable(false);
                            AlertDialog progressBar = alert.create();
                            progressBar.show();

                            HashMap map = new HashMap();
                            map.put("id_customer", RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("user_id", null)));
                            map.put("room_id", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ruanganModelList.get(holder.getAdapterPosition()).getIdMobil())));
                            map.put("tgl_sewa", RequestBody.create(MediaType.parse("text/plain"), tvTanggalRental.getText().toString()));
                            map.put("tgl_kembali",RequestBody.create(MediaType.parse("text/plain"), tvTglKembali.getText().toString()));
                            map.put("harga", RequestBody.create(MediaType.parse("text/plain"), ruanganModelList.get(holder.getAdapterPosition()).getHarga()));
                            map.put("denda",RequestBody.create(MediaType.parse("text/plain"), ruanganModelList.get(holder.getAdapterPosition()).getDenda()));

                            CustomerInterface customerInterface = DataApi.getClient().create(CustomerInterface.class);
                            customerInterface.sewa(map).enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    ResponseModel responseModel = response.body();
                                    if (response.isSuccessful() && responseModel.getCode() == 200) {
                                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frameCustomer, new CustomerMyTransactionsFragment()).commit();
                                        Toasty.success(context, "Transaksi berhasil", Toasty.LENGTH_SHORT).show();
                                        progressBar.dismiss();
                                        dialogSewa.dismiss();
                                    }else {
                                        Toasty.error(context, responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                        progressBar.dismiss();
                                    }

                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    Toasty.error(context, "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                                    progressBar.dismiss();

                                }
                            });

                        }
                    }
                });



            }
        });


    }

    @Override
    public int getItemCount() {

        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRoom;
        CardView cvAc, cvDekorasi, cvMp3, cvCentralLock;
        Button btnDetail, btnSewa;
        TextView tvRoomName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            ivRoom = itemView.findViewById(R.id.ivRoom);
            cvAc = itemView.findViewById(R.id.cvAc);
            cvDekorasi = itemView.findViewById(R.id.cvDekorasi);
            cvMp3 = itemView.findViewById(R.id.cvMp3);
            cvCentralLock = itemView.findViewById(R.id.cvCentalLock);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            btnSewa = itemView.findViewById(R.id.btnSewa);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);


        }
    }

    private void datePicker(TextView tvDatePicker) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context);
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
}
