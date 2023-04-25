package com.example.sipemroomapp.CustomerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sipemroomapp.Model.RuanganModel;
import com.example.sipemroomapp.R;

import java.util.List;

public class ListRoomAdapter extends RecyclerView.Adapter<ListRoomAdapter.ViewHolder> {
    Context context;
    List<RuanganModel>ruanganModelList;

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRoom;
        Button btnDetail, btnSewa;
        TextView tvRoomName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            ivRoom = itemView.findViewById(R.id.ivRoom);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            btnSewa = itemView.findViewById(R.id.btnSewa);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
        }
    }
}
