package com.example.sipemroomapp.CustomerAdapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sipemroomapp.Customer_fragment.CustomerDetaillRoomFragment;
import com.example.sipemroomapp.Customer_fragment.CustomerHomeFragment;
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

public class ListRoomAdapter extends RecyclerView.Adapter<ListRoomAdapter.ViewHolder> {
    Context context;
    List<RuanganModel>ruanganModelList;
    SharedPreferences sharedPreferences;

    public ListRoomAdapter(Context context, List<RuanganModel> ruanganModelList) {
        this.context = context;
        this.ruanganModelList = ruanganModelList;
    }

    @NonNull
    @Override
    public ListRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_list_room,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRoomAdapter.ViewHolder holder, int position) {
        holder.tvRoomName.setText(ruanganModelList.get(position).getMerk());
        Glide.with(context)
                .load(ruanganModelList.get(position).getGambar())
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(holder.ivRoom);

    }

    @Override
    public int getItemCount() {

        return ruanganModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivRoom;
        Button btnDetail, btnSewa;
        TextView tvRoomName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            ivRoom = itemView.findViewById(R.id.ivRoom);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            btnSewa = itemView.findViewById(R.id.btnSewa);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);


            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.optiion_menu);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            final Button btnSewa, btnDetail;
            btnSewa = dialog.findViewById(R.id.btnSewa);
            btnDetail = dialog.findViewById(R.id.btnDetail);

            if (ruanganModelList.get(getAdapterPosition()).getStatus() == 1) {

                btnSewa.setEnabled(true);
                btnSewa.setBackgroundColor(context.getResources().getColor(R.color.main));
                btnSewa.setText("Sewa");


            }else {
                btnSewa.setEnabled(false);
                btnSewa.setBackgroundColor(context.getResources().getColor(R.color.red));
                btnSewa.setText("Tidak Tersedia");
                btnSewa.setTextColor(context.getResources().getColor(R.color.white));

            }

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Fragment fragment = new CustomerDetaillRoomFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("room_id", ruanganModelList.get(getAdapterPosition()).getIdMobil());
                    bundle.putString("gambar", ruanganModelList.get(getAdapterPosition()).getGambar());
                    bundle.putString("room_name", ruanganModelList.get(getAdapterPosition()).getMerk());
                    bundle.putString("dekorasi", ruanganModelList.get(getAdapterPosition()).getWarna());
                    bundle.putString("tahun", ruanganModelList.get(getAdapterPosition()).getTahun());
                    bundle.putInt("status", ruanganModelList.get(getAdapterPosition()).getStatus());
                    fragment.setArguments(bundle);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frameCustomer, fragment).addToBackStack(null).commit();
                }
            });
            dialog.show();

            btnSewa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Dialog dialogSewa = new Dialog(context);
                    dialogSewa.setContentView(R.layout.layout_sewa);
                    dialogSewa.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    final Button btnSewa, btnBatal;
                    btnSewa = dialogSewa.findViewById(R.id.btnSewa);
                    btnBatal = dialogSewa.findViewById(R.id.btnCancel);
                    final TextView tvTanggalRental, tvTglKembali;
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
                                map.put("room_id", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ruanganModelList.get(getAdapterPosition()).getIdMobil())));
                                map.put("tgl_rental", RequestBody.create(MediaType.parse("text/plain"), tvTanggalRental.getText().toString()));
                                map.put("tgl_kembali",RequestBody.create(MediaType.parse("text/plain"), tvTglKembali.getText().toString()));
                                map.put("harga", RequestBody.create(MediaType.parse("text/plain"), ruanganModelList.get(getAdapterPosition()).getHarga()));
                                map.put("denda",RequestBody.create(MediaType.parse("text/plain"), ruanganModelList.get(getAdapterPosition()).getDenda()));

                                CustomerInterface customerInterface = DataApi.getClient().create(CustomerInterface.class);
                                customerInterface.sewa(map).enqueue(new Callback<ResponseModel>() {
                                    @Override
                                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                        ResponseModel responseModel = response.body();
                                        if (response.isSuccessful() && responseModel.getCode() == 200) {
                                            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frameCustomer, new CustomerHomeFragment()).commit();
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
                                        Log.e("adasd", "onFailure: ",t );
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
