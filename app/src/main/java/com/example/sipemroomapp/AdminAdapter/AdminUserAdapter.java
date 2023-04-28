package com.example.sipemroomapp.AdminAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.Model.UserModel;
import com.example.sipemroomapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {
    Context context;
    List<UserModel>userModelList;

    public AdminUserAdapter(Context context, List<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    @NonNull
    @Override
    public AdminUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminUserAdapter.ViewHolder holder, int position) {
        holder.tvUserName.setText(userModelList.get(holder.getAdapterPosition()).getUsername());
        holder.tvNama.setText(userModelList.get(holder.getAdapterPosition()).getNama());

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public void filterList(ArrayList<UserModel>filterList) {
        userModelList = filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvUserName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvName);
            tvUserName = itemView.findViewById(R.id.tvUsername);
        }
    }
}
