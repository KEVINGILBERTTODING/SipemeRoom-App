package com.example.sipemroomapp.CustomerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sipemroomapp.Model.RuanganModel;
import com.example.sipemroomapp.R;

import java.util.List;

public class MainRoomAdapter extends RecyclerView.Adapter<MainRoomAdapter.ViewHolder> {
    Context context;
    List<RuanganModel>ruanganModelList;

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
}
