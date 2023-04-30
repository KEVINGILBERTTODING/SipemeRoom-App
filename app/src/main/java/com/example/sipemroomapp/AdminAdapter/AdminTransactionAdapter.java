package com.example.sipemroomapp.AdminAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.AdminFragment.DetailTransaksiAdminFragment;
import com.example.sipemroomapp.Model.TransactionsModel;
import com.example.sipemroomapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdminTransactionAdapter extends RecyclerView.Adapter<AdminTransactionAdapter.ViewHolder> {

    Context context;
    List<TransactionsModel>transactionsModelList;

    public AdminTransactionAdapter(Context context, List<TransactionsModel> transactionsModelList) {
        this.context = context;
        this.transactionsModelList = transactionsModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_transaksi_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminTransactionAdapter.ViewHolder holder, int position) {
        holder.tvRoomName.setText(transactionsModelList.get(holder.getAdapterPosition()).getRoomName());
        holder.tvNamaLengkap.setText(transactionsModelList.get(holder.getAdapterPosition()).getNama());
        holder.tvStatus.setText(transactionsModelList.get(holder.getAdapterPosition()).getStatusRental());

        if (transactionsModelList.get(holder.getAdapterPosition()).getStatusRental().equals("Selesai")) {
            holder.cvStatus.setBackgroundColor(context.getColor(R.color.green));
        }else {
            holder.cvStatus.setBackgroundColor(context.getColor(R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return transactionsModelList.size();
    }

    public void filterList(ArrayList<TransactionsModel>filteredList) {
        transactionsModelList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNamaLengkap, tvRoomName, tvStatus;
        CardView cvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaLengkap = itemView.findViewById(R.id.tvNamaLengkap);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cvStatus = itemView.findViewById(R.id.cvStatus);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Fragment fragment = new DetailTransaksiAdminFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id_rental", transactionsModelList.get(getAdapterPosition()).getIdRental());
            bundle.putString("id_room", transactionsModelList.get(getAdapterPosition()).getRoomId());
            bundle.putString("nama_customer", transactionsModelList.get(getAdapterPosition()).getNama());
            bundle.putString("ruangan", transactionsModelList.get(getAdapterPosition()).getRoomName());
            bundle.putString("tgl_sewa", transactionsModelList.get(getAdapterPosition()).getTglRental());
            bundle.putString("tgl_kembali", transactionsModelList.get(getAdapterPosition()).getTglKembali());
            bundle.putString("tgl_selesai", transactionsModelList.get(getAdapterPosition()).getTglPengembalian());
            bundle.putString("status_selesai", transactionsModelList.get(getAdapterPosition()).getStatusRental());
            bundle.putString("status_pengembalian", transactionsModelList.get(getAdapterPosition()).getStatusPengembalian());
            bundle.putString("status_pembayaran", String.valueOf(transactionsModelList.get(getAdapterPosition()).getStatusPembayaran()));
            bundle.putString("bukti_pembayaran", String.valueOf(transactionsModelList.get(getAdapterPosition()).getBuktiPembayaran()));
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();

        }
    }
}
