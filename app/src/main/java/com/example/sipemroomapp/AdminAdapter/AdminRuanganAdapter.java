package com.example.sipemroomapp.AdminAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sipemroomapp.Customer_fragment.CustomerDetaillRoomFragment;
import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.RuanganModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.AdminInterface;
import com.example.sipemroomapp.util.DataApi;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRuanganAdapter extends RecyclerView.Adapter<AdminRuanganAdapter.ViewHolder> {
    Context context;
    List<RuanganModel>ruanganModelList;
    AdminInterface adminInterface;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivRoom;
        TextView tvRoomName, tvStatus;
        CardView cvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRoom = itemView.findViewById(R.id.ivRoom);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cvStatus = itemView.findViewById(R.id.cvStatus);
            adminInterface = DataApi.getClient().create(AdminInterface.class);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Dialog optionMenu = new Dialog(context);
            optionMenu.setCancelable(true);
            optionMenu.setContentView(R.layout.room_option_menu);
            optionMenu.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            final Button btnDelete, btnEdit, btnDetail;
            btnDelete = optionMenu.findViewById(R.id.btnDelete);
            btnEdit = optionMenu.findViewById(R.id.btnEdit);
            btnDetail = optionMenu.findViewById(R.id.btnDetail);
            optionMenu.show();


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setMessage("Menghapus ruangan...").setTitle("Loading").setCancelable(false);
                    AlertDialog progressDialog = alert.create();
                    progressDialog.show();

                    adminInterface.deleteRoom(ruanganModelList.get(getAdapterPosition()).getIdMobil()).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            ResponseModel responseModel = response.body();
                            if (response.isSuccessful() && responseModel.getStatus() == true) {
                                ruanganModelList.remove(getAdapterPosition());
                                notifyDataSetChanged();
                                notifyItemRangeChanged(getAdapterPosition(), ruanganModelList.size());
                                notifyItemRangeRemoved(getAdapterPosition(), ruanganModelList.size());
                                Toasty.success(context, "Berhasil menghapus data", Toasty.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                optionMenu.dismiss();
                            }else {
                                Toasty.error(context, "Gagal menghapus data", Toasty.LENGTH_SHORT).show();
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
            });
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optionMenu.dismiss();
                    Fragment fragment = new CustomerDetaillRoomFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("room_id", ruanganModelList.get(getAdapterPosition()).getIdMobil());
                    bundle.putString("gambar", ruanganModelList.get(getAdapterPosition()).getGambar());
                    bundle.putString("room_name", ruanganModelList.get(getAdapterPosition()).getMerk());
                    bundle.putString("dekorasi", ruanganModelList.get(getAdapterPosition()).getWarna());
                    bundle.putString("tahun", ruanganModelList.get(getAdapterPosition()).getTahun());
                    bundle.putInt("status", ruanganModelList.get(getAdapterPosition()).getStatus());
                    bundle.putString("harga", ruanganModelList.get(getAdapterPosition()).getHarga());
                    bundle.putString("denda", ruanganModelList.get(getAdapterPosition()).getDenda());
                    fragment.setArguments(bundle);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();
                }
            });

        }
    }
}
