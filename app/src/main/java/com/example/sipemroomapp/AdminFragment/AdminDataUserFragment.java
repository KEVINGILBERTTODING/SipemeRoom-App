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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.AdminAdapter.AdminUserAdapter;
import com.example.sipemroomapp.Model.UserModel;
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


public class AdminDataUserFragment extends Fragment {
    RecyclerView rvUser;
    FloatingActionButton fabInsert;
    LinearLayoutManager linearLayoutManager;

    AdminUserAdapter adminUserAdapter;
    List<UserModel> userModelList;
    AdminInterface adminInterface;
    SearchView searchView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_data_user, container, false);
        rvUser = view.findViewById(R.id.rvListUser);
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

            }
        });

        return view;
    }

    private void displayRoom() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(false).setMessage("Memuat data...").setTitle("Loading");
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getAllUser().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                userModelList = response.body();
                if (response.isSuccessful() && userModelList.size() > 0) {
                    adminUserAdapter = new AdminUserAdapter(getContext(), userModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvUser.setLayoutManager(linearLayoutManager);
                    rvUser.setAdapter(adminUserAdapter);
                    rvUser.setHasFixedSize(true);
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
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
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
        ArrayList<UserModel>filteredList = new ArrayList<>();
        for (UserModel item : userModelList) {
            if (item.getNama().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adminUserAdapter.filterList(filteredList);
        if (filteredList.isEmpty()) {
            Toasty.normal(getContext(), "Tidak ditemukan", Toasty.LENGTH_SHORT).show();
        }else {
            adminUserAdapter.filterList(filteredList);
        }
    }
}