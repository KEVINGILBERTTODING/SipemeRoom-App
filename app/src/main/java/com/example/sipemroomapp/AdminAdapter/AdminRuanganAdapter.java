package com.example.sipemroomapp.AdminAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sipemroomapp.Model.RuanganModel;
import com.example.sipemroomapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdminRuanganAdapter extends RecyclerView.Adapter<AdminRuanganAdapter.ViewHolder> {
    Context context;
    List<RuanganModel>ruanganModelList;

    public AdminRuanganAdapter(Context context, List<RuanganModel> ruanganModelList) {
        this.context = context;
        this.ruanganModelList = ruanganModelList;
    }

    @NonNull
    @Override
    public AdminRuanganAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_list_room2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRuanganAdapter.ViewHolder holder, int position) {
        holder.tvRoomName.setText(ruanganModelList.get(holder.getAdapterPosition()).getMerk());
        if (ruanganModelList.get(holder.getAdapterPosition()).getStatus() == 1) {
            holder.tvStatus.setText("Tersedia");
            holder.cvStatus.setBackgroundColor(context.getColor(R.color.main));
            holder.tvStatus.setTextColor(context.getColor(R.color.white));
        }else {
            holder.tvStatus.setText("Tidak tersedia");
            holder.cvStatus.setBackgroundColor(context.getColor(R.color.dark_gray));
            holder.tvStatus.setTextColor(context.getColor(R.color.white));
        }

        Glide.with(context)
                .load(ruanganModelList.get(holder.getAdapterPosition()).getGambar())
                .dontAnimate()
                .skipMemoryCache(true)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.ivRoom);

    }

    @Override
    public int getItemCount() {
        return ruanganModelList.size();
    }

    public void filterList(ArrayList<RuanganModel>fillter){
        ruanganModelList = fillter;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRoom;
        TextView tvRoomName, tvStatus;
        CardView cvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRoom = itemView.findViewById(R.id.ivRoom);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cvStatus = itemView.findViewById(R.id.cvStatus);

        }
    }
}
