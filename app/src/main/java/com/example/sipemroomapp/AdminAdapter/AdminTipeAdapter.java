package com.example.sipemroomapp.AdminAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.TipeModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.AdminInterface;
import com.example.sipemroomapp.util.DataApi;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTipeAdapter extends RecyclerView.Adapter<AdminTipeAdapter.ViewHolder> {
    Context context;
    List<TipeModel>tipeModelList;
    AdminInterface adminInterface;

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
            adminInterface = DataApi.getClient().create(AdminInterface.class);
            Dialog optionMenu = new Dialog(context);
            optionMenu.setContentView(R.layout.option_menu_tipe_room);
            optionMenu.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            final Button btnDelete, btnEdit;
            btnDelete = optionMenu.findViewById(R.id.btnDelete);
            btnEdit = optionMenu.findViewById(R.id.btnEdit);
            optionMenu.show();

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optionMenu.dismiss();
                    AlertDialog.Builder alertDiaalog = new AlertDialog.Builder(context);
                    alertDiaalog.setCancelable(false).setMessage("Menghapus tipe...").setTitle("Loading");
                    AlertDialog pd2 = alertDiaalog.create();

                    adminInterface.deleteTipe(Integer.parseInt(tipeModelList.get(getAdapterPosition()).getIdTipe())).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            ResponseModel responseModel = response.body();
                            if (response.isSuccessful() && responseModel.getStatus() == true) {
                                Toasty.success(context, "Berhasil menghapus tipe", Toasty.LENGTH_SHORT).show();
                                tipeModelList.remove(getAdapterPosition());
                                notifyDataSetChanged();
                                notifyItemRangeChanged(getAdapterPosition(), tipeModelList.size());
                                notifyItemRangeRemoved(getAdapterPosition(), tipeModelList.size());
                                pd2.dismiss();
                            }else {
                                Toasty.error(context, responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                pd2.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toasty.error(context, "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                            pd2.dismiss();

                        }
                    });

                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.layout_insert_tipe);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setCanceledOnTouchOutside(false);
                    final EditText etKodeTipe, etNamaTipe;
                    final Button btnSimpan, btnBatal;
                    etKodeTipe = dialog.findViewById(R.id.etKodeTipe);
                    etNamaTipe = dialog.findViewById(R.id.etNamaTipe);
                    btnSimpan = dialog.findViewById(R.id.btnSimpan);
                    btnBatal = dialog.findViewById(R.id.btnBatal);
                    etKodeTipe.setText(tipeModelList.get(getAdapterPosition()).getKodeTipe());
                    etNamaTipe.setText(tipeModelList.get(getAdapterPosition()).getNamaTipe());
                    dialog.show();
                    btnSimpan.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            androidx.appcompat.app.AlertDialog.Builder alert  = new androidx.appcompat.app.AlertDialog.Builder(context);
                            alert.setCancelable(false).setMessage("Menyimpan data...").setTitle("Loading");
                            androidx.appcompat.app.AlertDialog pd = alert.create();
                            pd.show();

                            if (etNamaTipe.getText().toString().isEmpty()){
                                Toasty.error(context, "Field nama tipe tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                            } else if (etKodeTipe.getText().toString().isEmpty()){
                                Toasty.error(context, "Field kode tipe tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                            }else {
                                adminInterface.updateTipe(
                                        Integer.parseInt(tipeModelList.get(getAdapterPosition()).getIdTipe()),
                                        etKodeTipe.getText().toString(),
                                        etNamaTipe.getText().toString()).enqueue(new Callback<ResponseModel>() {
                                    @Override
                                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                        ResponseModel responseModel = response.body();
                                        if (response.isSuccessful() && responseModel.getStatus() == true) {
                                            tipeModelList.get(getAdapterPosition()).setKodeTipe(etKodeTipe.getText().toString());
                                            tipeModelList.get(getAdapterPosition()).setNamaTipe(etNamaTipe.getText().toString());
                                            notifyDataSetChanged();
                                            Toasty.success(context, "Berhasil mengubah data", Toasty.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            pd.dismiss();
                                            optionMenu.dismiss();

                                        }else {
                                            Toasty.success(context, "Gagal mengubah data", Toasty.LENGTH_SHORT).show();
                                            pd.dismiss();
                                            optionMenu.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                                        Toasty.success(context, "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                                        pd.dismiss();
                                        optionMenu.dismiss();
                                    }
                                });
                            }

                        }
                    });
                    btnBatal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            optionMenu.dismiss();
                        }
                    });

                }
            });
        }
    }
}
