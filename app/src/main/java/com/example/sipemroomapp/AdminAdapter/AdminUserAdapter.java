package com.example.sipemroomapp.AdminAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.UserModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.AdminInterface;
import com.example.sipemroomapp.util.DataApi;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {
    Context context;
    List<UserModel>userModelList;
    AdminInterface adminInterface;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNama, tvUserName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvName);
            tvUserName = itemView.findViewById(R.id.tvUsername);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adminInterface = DataApi.getClient().create(AdminInterface.class);

            Dialog mainOptionMenu  = new Dialog(context);
            mainOptionMenu.setContentView(R.layout.option_menu_tipe_room);
            mainOptionMenu.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            final Button btnDelete, btnEdit;
            btnEdit =mainOptionMenu.findViewById(R.id.btnEdit);
            btnDelete = mainOptionMenu.findViewById(R.id.btnDelete);
            mainOptionMenu.show();

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainOptionMenu.dismiss();
                    AlertDialog.Builder alertDelete = new AlertDialog.Builder(context);
                    alertDelete.setTitle("Peringatan").setMessage("Apakah anda yakin ingin menghapus customer ini?")
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                    alert.setCancelable(false).setMessage("Menghapus data...").setTitle("Loading...");
                                    AlertDialog progressDialog = alert.create();
                                    progressDialog.show();

                                    adminInterface.deleteUser(userModelList.get(getAdapterPosition()).getCustomerId())
                                            .enqueue(new Callback<ResponseModel>() {
                                                @Override
                                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                                    ResponseModel responseModel = response.body();
                                                    if (response.isSuccessful() && responseModel.getStatus() == true) {
                                                        userModelList.remove(getAdapterPosition());
                                                        notifyDataSetChanged();
                                                        notifyItemRangeChanged(getAdapterPosition(), userModelList.size());
                                                        notifyItemRangeRemoved(getAdapterPosition(), userModelList.size());
                                                        Toasty.success(context, "Berhasil menghapus customer", Toasty.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();

                                                    }else {
                                                        Toasty.error(context, "Gagal menghapus customer", Toasty.LENGTH_SHORT).show();
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
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    alertDelete.show();
                }
            });

        }
    }
}
