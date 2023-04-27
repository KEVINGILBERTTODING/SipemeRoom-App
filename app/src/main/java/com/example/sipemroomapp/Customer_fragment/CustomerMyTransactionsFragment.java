package com.example.sipemroomapp.Customer_fragment;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.sipemroomapp.CustomerAdapter.CustomerTransactionsAdapter;
import com.example.sipemroomapp.Model.TransactionsModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.CustomerInterface;
import com.example.sipemroomapp.util.DataApi;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerMyTransactionsFragment extends Fragment {

    SearchView searchView;
    TextView tvEmpty;
    RecyclerView rvTransactions;
    LinearLayoutManager linearLayoutManager;
    CustomerTransactionsAdapter customerTransactionsAdapter;
    List<TransactionsModel>transactionsModelList;
    SharedPreferences sharedPreferences;
    String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_my_transaction, container, false);
        searchView = view.findViewById(R.id.searchView);
        rvTransactions = view.findViewById(R.id.rvMyTransactions);
        tvEmpty = view.findViewById(R.id.tvEmpty);

        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);

        userId = sharedPreferences.getString("user_id", null);

        displayTransactions();

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

    private void displayTransactions() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(false).setTitle("Loading").setMessage("Memuat data...");
        AlertDialog progressDialog = alert.create();
        progressDialog.show();

        CustomerInterface customerInterface = DataApi.getClient().create(CustomerInterface.class);
        customerInterface.getMyTransactions(userId).enqueue(new Callback<List<TransactionsModel>>() {
            @Override
            public void onResponse(Call<List<TransactionsModel>> call, Response<List<TransactionsModel>> response) {
                transactionsModelList = response.body();
                if (response.isSuccessful() && response.body().size() > 0) {
                    customerTransactionsAdapter = new CustomerTransactionsAdapter(getContext(), transactionsModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvTransactions.setLayoutManager(linearLayoutManager);
                    rvTransactions.setAdapter(customerTransactionsAdapter);
                    rvTransactions.setHasFixedSize(true);
                    progressDialog.dismiss();

                }else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<TransactionsModel>> call, Throwable t) {
                Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }

    private void filter(String newText) {
        ArrayList<TransactionsModel>filteredList = new ArrayList<>();
        for (TransactionsModel item : transactionsModelList) {
            if (item.getRoomName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        customerTransactionsAdapter.filterList(filteredList);
        if (filteredList.isEmpty()) {
            Toasty.normal(getContext(), "Tidak ditemukan", Toasty.LENGTH_SHORT).show();
        }else {
            customerTransactionsAdapter.filterList(filteredList);
        }
    }
}