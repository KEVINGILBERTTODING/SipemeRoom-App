package com.example.sipemroomapp.AdminFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.AdminAdapter.AdminTipeAdapter;
import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.TipeModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.AdminInterface;
import com.example.sipemroomapp.util.DataApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminDataTipeFragment extends Fragment {
    RecyclerView rvTipe;
    FloatingActionButton fabInsert;
    LinearLayoutManager linearLayoutManager;

    AdminTipeAdapter tipeAdapter;
    List<TipeModel> tipeModelList;
    AdminInterface adminInterface;
    SearchView searchView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_data_tipe, container, false);
        rvTipe = view.findViewById(R.id.rvListTipe);
        fabInsert = view.findViewById(R.id.fabInsert);
        adminInterface = DataApi.getClient().create(AdminInterface.class);
        searchView = view.findViewById(R.id.searchView);

        displayRoom();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        fabInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_insert_tipe);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCanceledOnTouchOutside(false);
                final EditText etKodeTipe, etNamaTipe;
                final Button btnSimpan, btnBatal;
                etKodeTipe = dialog.findViewById(R.id.etKodeTipe);
                etNamaTipe = dialog.findViewById(R.id.etNamaTipe);
                btnSimpan = dialog.findViewById(R.id.btnSimpan);
                btnBatal = dialog.findViewById(R.id.btnBatal);
                dialog.show();
                btnSimpan.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        AlertDialog.Builder alert  = new AlertDialog.Builder(getContext());
                        alert.setCancelable(false).setMessage("Menyimpan data...").setTitle("Loading");
                        AlertDialog pd = alert.create();
                        pd.show();

                        if (etNamaTipe.getText().toString().isEmpty()){
                            Toasty.error(getContext(), "Field nama tipe tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                        } else if (etKodeTipe.getText().toString().isEmpty()){
                            Toasty.error(getContext(), "Field kode tipe tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                        }else {
                            adminInterface.insertTipe(etKodeTipe.getText().toString(),
                                    etNamaTipe.getText().toString()).enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    ResponseModel responseModel = response.body();
                                    if (response.isSuccessful() && responseModel.getStatus() == true) {
                                        Toasty.success(getContext(), "Berhasil menambahkan tipe room baru", Toasty.LENGTH_SHORT).show();
                                        rvTipe.setAdapter(null);
                                        displayRoom();
                                        pd.dismiss();
                                        dialog.dismiss();
                                    }else {
                                        Toasty.success(getContext(), "Gagal menambahkan tipe room baru", Toasty.LENGTH_SHORT).show();
                                        pd.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    Toasty.success(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }
                            });
                        }

                    }
                });
                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });



            }
        });

        return view;
    }

    private void displayRoom() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(false).setMessage("Memuat data...").setTitle("Loading");
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getAllTipe().enqueue(new Callback<List<TipeModel>>() {
            @Override
            public void onResponse(Call<List<TipeModel>> call, Response<List<TipeModel>> response) {
                tipeModelList = response.body();
                if (response.isSuccessful() && tipeModelList.size() > 0) {
                    tipeAdapter = new AdminTipeAdapter(getContext(), tipeModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvTipe.setLayoutManager(linearLayoutManager);
                    rvTipe.setAdapter(tipeAdapter);
                    rvTipe.setHasFixedSize(true);
                    pd.dismiss();

                }else {
                    Dialog optionRefresh = new Dialog(getContext());
                    optionRefresh.setContentView(R.layout.menu_retry);
                    optionRefresh.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    optionRefresh.show();
                    final Button btnRefresh;
                    btnRefresh = optionRefresh.findViewById(R.id.btnRefresh);
                    btnRefresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            displayRoom();
                        }
                    });
                    pd.dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<TipeModel>> call, Throwable t) {
                Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                Dialog optionRefresh = new Dialog(getContext());
                optionRefresh.setContentView(R.layout.menu_retry);
                optionRefresh.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                optionRefresh.show();
                final Button btnRefresh;
                btnRefresh = optionRefresh.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayRoom();
                    }
                });
                pd.dismiss();

            }
        });

    }

    private void filter(String newText){
        ArrayList<TipeModel>filteredList = new ArrayList<>();
        for (TipeModel item : tipeModelList) {
            if (item.getNamaTipe().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        tipeAdapter.filterList(filteredList);
        if (filteredList.isEmpty()) {
            Toasty.normal(getContext(), "Tidak ditemukan", Toasty.LENGTH_SHORT).show();
        }else {
            tipeAdapter.filterList(filteredList);
        }
    }
}