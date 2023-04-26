package com.example.sipemroomapp.CustomerAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.Customer_fragment.CustomerDetailTransaksiFragment;
import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.TransactionsModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.CustomerInterface;
import com.example.sipemroomapp.util.DataApi;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerTransactionsAdapter extends RecyclerView.Adapter<CustomerTransactionsAdapter.ViewHolder> {
    Context context;
    List<TransactionsModel> transactionsModelList;

    public CustomerTransactionsAdapter(Context context, List<TransactionsModel> transactionsModelList) {
        this.context = context;
        this.transactionsModelList = transactionsModelList;
    }


    @NonNull
    @Override
    public CustomerTransactionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_transactions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerTransactionsAdapter.ViewHolder holder, int position) {
        holder.tvRoomName.setText(transactionsModelList.get(holder.getAdapterPosition()).getRoomName());
        holder.tvTotalPeople.setText(String.valueOf(transactionsModelList.get(holder.getAdapterPosition()).getTotalPeople()) + " Orang");

        if (transactionsModelList.get(holder.getAdapterPosition()).getStatusRental().equals("Selesai")) {
            holder.btnBatal.setVisibility(View.GONE);
            holder.btnCekPemesanan.setText("Sewa selesai");
            holder.btnCekPemesanan.setBackgroundColor(context.getResources().getColor(R.color.gray));
            holder.btnCekPemesanan.setTextColor(context.getResources().getColor(R.color.white));
            holder.btnCekPemesanan.setEnabled(false);

        }

        holder.btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage("Apakah anda yakin ingin membatalkan transaksi ini?").setTitle("Peringatan")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setCancelable(false).setTitle("Loading").setMessage("Menghapus transaksi...");
                                AlertDialog progressDialog = alert.create();
                                progressDialog.show();

                                CustomerInterface customerInterface = DataApi.getClient().create(CustomerInterface.class);
                                customerInterface.cancelOrder(
                                        transactionsModelList.get(holder.getAdapterPosition()).getRoomId(),
                                        transactionsModelList.get(holder.getAdapterPosition()).getIdRental()
                                ).enqueue(new Callback<ResponseModel>() {
                                    @Override
                                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                        ResponseModel responseModel = response.body();
                                        if (response.isSuccessful() && responseModel.getStatus() == true) {
                                            transactionsModelList.remove(holder.getAdapterPosition());
                                            notifyDataSetChanged();
                                            notifyItemRangeChanged(holder.getAdapterPosition(), transactionsModelList.size());
                                            notifyItemRangeRemoved(holder.getAdapterPosition(), transactionsModelList.size());
                                            Toasty.success(context, "Transaksi berhasil dibatalkan", Toasty.LENGTH_SHORT).show();
                                            progressDialog.dismiss();

                                        }else {
                                            Toasty.error(context, "Something went wrong", Toasty.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                                        Toasty.error(context, "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();

                                        progressDialog.dismiss();

                                    }
                                });

                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alertDialog.show();


            }
        });
        holder.btnCekPemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CustomerDetailTransaksiFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("trans_id", Integer.parseInt(transactionsModelList.get(holder.getAdapterPosition()).getIdRental()));
                fragment.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frameCustomer, fragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactionsModelList.size();
    }

    public void filterList(ArrayList<TransactionsModel>filterList) {
        transactionsModelList = filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvTotalPeople;
        Button btnBatal, btnCekPemesanan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvTotalPeople = itemView.findViewById(R.id.tvTotalPeople);
            btnBatal = itemView.findViewById(R.id.btnBatal);
            btnCekPemesanan = itemView.findViewById(R.id.btnCekPemesanan);
        }
    }
}
