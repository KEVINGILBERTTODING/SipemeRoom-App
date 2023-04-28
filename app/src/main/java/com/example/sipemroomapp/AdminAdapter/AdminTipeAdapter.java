package com.example.sipemroomapp.AdminAdapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.Model.TipeModel;
import com.example.sipemroomapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdminTipeAdapter extends RecyclerView.Adapter<AdminTipeAdapter.ViewHolder> {
    Context context;
    List<TipeModel>tipeModelList;

    public AdminTipeAdapter(Context context, List<TipeModel> tipeModelList) {
        this.context = context;
        this.tipeModelList = tipeModelList;
    }

    @NonNull
    @Override
    public AdminTipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_tipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminTipeAdapter.ViewHolder holder, int position) {
        holder.tvTipeRoom.setText(tipeModelList.get(holder.getAdapterPosition()).getNamaTipe());
        holder.tvKodeRoom.setText(tipeModelList.get(holder.getAdapterPosition()).getKodeTipe());

    }

    @Override
    public int getItemCount() {
        return tipeModelList.size();
    }

    public void filterList(ArrayList<TipeModel>filterList) {
        tipeModelList = filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTipeRoom, tvKodeRoom;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipeRoom = itemView.findViewById(R.id.tvTipeRoom);
            tvKodeRoom = itemView.findViewById(R.id.tvKodeRoom);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Dialog optionMenu = new Dialog(context);
            optionMenu.setContentView(R.layout.option_menu_tipe_room);
        }
    }
}
