package com.example.sipemroomapp.AdminFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDataTransaksiFragment extends Fragment {
    RecyclerView rvTransaksi;
    SearchView searchView;
    List<TransactionsModel>transactionsModelList;
    AdminTransactionAdapter adminTransactionAdapter;
    AdminInterface adminInterface;
    LinearLayoutManager linearLayoutManager;
    TextView tvEmpty;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_data_transaksi, container, false);

        init(view);


        displayData();

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
        adminInterface = DataApi.getClient().create(AdminInterface.class);


    }

    private void displayData() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage("Memuat data...").setTitle("Loading").setCancelable(false);
        AlertDialog progressDialog = alert.create();
        progressDialog.show();



        // dialog refresh
        Dialog dialogRefresh = new Dialog(getContext());
        dialogRefresh.setContentView(R.layout.menu_retry);
        dialogRefresh.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogRefresh.setCanceledOnTouchOutside(false);


        adminInterface.getAllTransactions().enqueue(new Callback<List<TransactionsModel>>() {
            @Override
            public void onResponse(Call<List<TransactionsModel>> call, Response<List<TransactionsModel>> response) {
                transactionsModelList = response.body();
                if (response.isSuccessful() && response.body().size() > 0) {

                    adminTransactionAdapter = new AdminTransactionAdapter(getContext(), transactionsModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                    rvTransaksi.setLayoutManager(linearLayoutManager);
                    rvTransaksi.setAdapter(adminTransactionAdapter);
                    rvTransaksi.setHasFixedSize(true);
                    tvEmpty.setVisibility(View.GONE);
                    dialogRefresh.dismiss();
                    progressDialog.dismiss();
                }else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    dialogRefresh.dismiss();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<TransactionsModel>> call, Throwable t) {
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
}