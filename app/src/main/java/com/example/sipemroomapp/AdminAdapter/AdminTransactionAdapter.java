package com.example.sipemroomapp.AdminAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaLengkap, tvRoomName, tvStatus;
        CardView cvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaLengkap = itemView.findViewById(R.id.tvNamaLengkap);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cvStatus = itemView.findViewById(R.id.cvStatus);
        }


    }
}
