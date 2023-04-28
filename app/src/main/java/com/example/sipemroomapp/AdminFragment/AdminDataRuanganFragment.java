package com.example.sipemroomapp.AdminFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.AdminAdapter.AdminRuanganAdapter;
import com.example.sipemroomapp.Model.RuanganModel;
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


public class AdminDataRuanganFragment extends Fragment {
    RecyclerView rvRuangan;
    FloatingActionButton fabInsert;
    GridLayoutManager gridLayoutManager;
    AdminRuanganAdapter adminRuanganAdapter;
    List<RuanganModel>ruanganModelList;
    AdminInterface adminInterface;
    SearchView searchView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_data_ruangan, container, false);
        rvRuangan = view.findViewById(R.id.rvListRoom);
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
                ((FragmentActivity) getContext()).getSupportFragmentManager()
                        .beginTransaction().replace(R.id.frameAdmin, new AdminInsertRuanganFragment())
                        .addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void displayRoom() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(false).setMessage("Memuat data...").setTitle("Loading");
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getAllRoom().enqueue(new Callback<List<RuanganModel>>() {
            @Override
            public void onResponse(Call<List<RuanganModel>> call, Response<List<RuanganModel>> response) {
                ruanganModelList = response.body();
                if (response.isSuccessful() && ruanganModelList.size() > 0) {
                    adminRuanganAdapter = new AdminRuanganAdapter(getContext(), ruanganModelList);
                    gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                    rvRuangan.setLayoutManager(gridLayoutManager);
                    rvRuangan.setAdapter(adminRuanganAdapter);
                    rvRuangan.setHasFixedSize(true);
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
            public void onFailure(Call<List<RuanganModel>> call, Throwable t) {
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
        ArrayList<RuanganModel>filteredList = new ArrayList<>();
        for (RuanganModel item : ruanganModelList) {
            if (item.getMerk().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adminRuanganAdapter.filterList(filteredList);
        if (filteredList.isEmpty()) {
            Toasty.normal(getContext(), "Tidak ditemukan", Toasty.LENGTH_SHORT).show();
        }else {
            adminRuanganAdapter.filterList(filteredList);
        }
    }
}