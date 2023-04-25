package com.example.sipemroomapp.Customer_fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipemroomapp.CustomerAdapter.ListRoomAdapter;
import com.example.sipemroomapp.CustomerAdapter.MainRoomAdapter;
import com.example.sipemroomapp.Model.RuanganModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.CustomerInterface;
import com.example.sipemroomapp.util.DataApi;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerHomeFragment extends Fragment {

    RecyclerView rvMainRoom, rvListRoom;
    List<RuanganModel>ruanganModelList;
    LinearLayoutManager linearLayoutManager;
    MainRoomAdapter mainRoomAdapter;

    AlertDialog.Builder alert;
    CustomerInterface customerInterface;

    TextView tvEmptyMainRoom, tvUsername;
    SharedPreferences sharedPreferences;
    ListRoomAdapter listRoomAdapter;
    GridLayoutManager gridLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_customer_home, container, false);
       rvMainRoom = view.findViewById(R.id.rvMainRoom);
       tvEmptyMainRoom = view.findViewById(R.id.tvEmptyMainRoom);
       tvUsername = view.findViewById(R.id.tvUsername);
       rvListRoom = view.findViewById(R.id.rvListRoom);
       customerInterface = DataApi.getClient().create(CustomerInterface.class);
       sharedPreferences = getContext().getSharedPreferences("user_data", getContext().MODE_PRIVATE);
       tvUsername.setText("Hai, "+sharedPreferences.getString("nama", null));


       // call main room
        displayMainRoom();

        displayListRoom();




       return view;
    }

    private void displayMainRoom(){
        alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(false).setMessage("Memuat data...").setTitle("Loading");
        AlertDialog progressDialog = alert.create();
        progressDialog.show();

        customerInterface.getALLRuangan().enqueue(new Callback<List<RuanganModel>>() {
            @Override
            public void onResponse(Call<List<RuanganModel>> call, Response<List<RuanganModel>> response) {
                ruanganModelList = response.body();
                if (response.isSuccessful() && response.body().size() > 0) {

                    mainRoomAdapter = new MainRoomAdapter(getContext(), ruanganModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvMainRoom.setLayoutManager(linearLayoutManager);
                    rvMainRoom.setAdapter(mainRoomAdapter);
                    rvMainRoom.setHasFixedSize(true);
                    tvEmptyMainRoom.setVisibility(View.GONE);
                    progressDialog.dismiss();


                }else {
                    tvEmptyMainRoom.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<RuanganModel>> call, Throwable t) {
                Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });


    }

    private void displayListRoom(){
        alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(false).setMessage("Memuat data...").setTitle("Loading");
        AlertDialog progressDialog = alert.create();
        progressDialog.show();

        customerInterface.getALLRuangan().enqueue(new Callback<List<RuanganModel>>() {
            @Override
            public void onResponse(Call<List<RuanganModel>> call, Response<List<RuanganModel>> response) {
                ruanganModelList = response.body();
                if (response.isSuccessful() && response.body().size() > 0) {

                    listRoomAdapter = new ListRoomAdapter(getContext(), ruanganModelList);
                    gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                    rvListRoom.setLayoutManager(gridLayoutManager);
                    rvListRoom.setAdapter(listRoomAdapter);
                    rvListRoom.setHasFixedSize(true);
                    tvEmptyMainRoom.setVisibility(View.GONE);
                    progressDialog.dismiss();


                }else {
                    tvEmptyMainRoom.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<RuanganModel>> call, Throwable t) {
                Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });


    }
}