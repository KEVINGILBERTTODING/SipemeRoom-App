package com.example.sipemroomapp.CustomerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.Model.TransactionsModel;
import com.example.sipemroomapp.R;

import java.util.ArrayList;
import java.util.List;

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
