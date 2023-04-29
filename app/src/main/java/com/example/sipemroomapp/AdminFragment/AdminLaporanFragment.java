package com.example.sipemroomapp.AdminFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.AdminAdapter.AdminTransactionAdapter;
import com.example.sipemroomapp.Model.TransactionsModel;
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

public class AdminLaporanFragment extends Fragment {
    RecyclerView rvTransaksi;
    SearchView searchView;
    List<TransactionsModel>transactionsModelList;
    AdminTransactionAdapter adminTransactionAdapter;
    AdminInterface adminInterface;
    LinearLayoutManager linearLayoutManager;
    TextView tvEmpty, tvTanggalDari, tvTanggalSampai;
    FloatingActionButton fabFilter;
    String tglDari, tglSampai;
    Button btnDownload;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_laporan_fragment, container, false);

        init(view);

        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogFilter = new Dialog(getContext());
                dialogFilter.setContentView(R.layout.layout_filter);
                dialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogFilter.setCanceledOnTouchOutside(false);
                tvTanggalDari = dialogFilter.findViewById(R.id.tvTglDari);
                tvTanggalSampai = dialogFilter.findViewById(R.id.tvTglSampai);
                final Button btnFilter, btnBatal;
                btnBatal = dialogFilter.findViewById(R.id.btnBatal);
                btnFilter = dialogFilter.findViewById(R.id.btnFilter);
                dialogFilter.show();
                tvTanggalDari.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDatePicker(tvTanggalDari);
                    }
                });

                tvTanggalSampai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDatePicker(tvTanggalSampai);
                    }
                });
                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogFilter.dismiss();
                    }
                });

                btnFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (tvTanggalSampai.getText().toString().isEmpty()) {
                           Toasty.error(getContext(), "Harap memilih tanggal terlebih dahulu", Toasty.LENGTH_SHORT).show();
                       }else if (tvTanggalDari.getText().toString().isEmpty()) {
                           Toasty.error(getContext(), "Harap memilih tanggal terlebih dahulu", Toasty.LENGTH_SHORT).show();
                       }else {
                           dialogFilter.dismiss();
                           displayData(
                                   tvTanggalDari.getText().toString(),
                                   tvTanggalSampai.getText().toString()
                           );
                       }
                    }
                });

            }
        });


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


        return view;
    }

    private void init(View view) {
        rvTransaksi = view.findViewById(R.id.rvListTransaksi);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        searchView = view.findViewById(R.id.searchView);
        fabFilter = view.findViewById(R.id.fabFilter);
        adminInterface = DataApi.getClient().create(AdminInterface.class);
        btnDownload = view.findViewById(R.id.btnDownload);




    }

    private void displayData(String dari, String sampai) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage("Memuat data...").setTitle("Loading").setCancelable(false);
        AlertDialog progressDialog = alert.create();
        progressDialog.show();



        // dialog refresh
        Dialog dialogRefresh = new Dialog(getContext());
        dialogRefresh.setContentView(R.layout.menu_retry);
        dialogRefresh.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogRefresh.setCanceledOnTouchOutside(false);
        final Button btnRefresh = dialogRefresh.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayData(dari, sampai);
                dialogRefresh.dismiss();
            }
        });


        adminInterface.filterLaporanTransaksi(dari, sampai).enqueue(new Callback<List<TransactionsModel>>() {
            @Override
            public void onResponse(Call<List<TransactionsModel>> call, Response<List<TransactionsModel>> response) {
                transactionsModelList = response.body();
                if (response.isSuccessful() && response.body().size() > 0) {
                    rvTransaksi.setAdapter(null);

                    adminTransactionAdapter = new AdminTransactionAdapter(getContext(), transactionsModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                    rvTransaksi.setLayoutManager(linearLayoutManager);
                    rvTransaksi.setAdapter(adminTransactionAdapter);
                    rvTransaksi.setHasFixedSize(true);
                    tvEmpty.setVisibility(View.GONE);
                    btnDownload.setVisibility(View.VISIBLE);
                    dialogRefresh.dismiss();
                    progressDialog.dismiss();
                }else {
                    rvTransaksi.setAdapter(null);
                    tvEmpty.setVisibility(View.VISIBLE);
                    btnDownload.setEnabled(false);
                    dialogRefresh.dismiss();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<TransactionsModel>> call, Throwable t) {
                rvTransaksi.setAdapter(null);
                btnDownload.setEnabled(false);
                tvEmpty.setVisibility(View.GONE);
                dialogRefresh.show();
                progressDialog.dismiss();

            }
        });


    }
    private void filter(String newText){
        ArrayList<TransactionsModel>filteredList = new ArrayList<>();
        for (TransactionsModel item : transactionsModelList) {
            if (item.getNama() != null && item.getNama().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adminTransactionAdapter.filterList(filteredList);
        if (filteredList.isEmpty()) {
            Toasty.normal(getContext(), "Tidak ditemukan", Toasty.LENGTH_SHORT).show();
        }else {
            adminTransactionAdapter.filterList(filteredList);
        }
    }

    private void getDatePicker(TextView tvDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateFormatted, monthFormatted;
                if (dayOfMonth < 10) {
                    dateFormatted = String.format("%02d", dayOfMonth);
                }else {
                    dateFormatted = String.valueOf(dayOfMonth);
                }


                if (month < 10) {
                    monthFormatted = String.format("%02d", month + 1);
                }else {
                    monthFormatted = String.valueOf(dayOfMonth);
                }
                tvDate.setText(year+"-"+monthFormatted+"-"+dateFormatted);
            }
        });
        datePickerDialog.show();

    }
}