package com.example.sipemroomapp.AdminFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.sipemroomapp.R;

public class AdminHomeFragment extends Fragment {
    TextView tvUsername;
    SharedPreferences sharedPreferences;
    CardView cvDataRuangan, cvDataTipe, cvmenuDataCustomer,cvDataTransaksi, cvMenuLaporan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
       tvUsername = view.findViewById(R.id.tvUsername);
       sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
       tvUsername.setText("Hai, " +sharedPreferences.getString("nama", null));
       cvDataRuangan = view.findViewById(R.id.menuDataRuangan);
       cvDataTipe = view.findViewById(R.id.menuDataTipe);
       cvmenuDataCustomer = view.findViewById(R.id.menuDataCustomer);
       cvDataTransaksi = view.findViewById(R.id.menuDataTransaksi);
       cvMenuLaporan = view.findViewById(R.id.menuDataLapora);

      cvDataRuangan.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              replace(new AdminDataRuanganFragment());
          }
      });

      cvDataTipe.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              replace(new AdminDataTipeFragment());
          }
      });

      cvmenuDataCustomer.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              replace(new AdminDataUserFragment());
          }
      });

      cvDataTransaksi.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              replace(new AdminDataTransaksiFragment());
          }
      });

      cvMenuLaporan.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              replace(new AdminLaporanFragment());
          }
      });






        return view;
    }

    private void replace (Fragment fragment) {
        ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();
    }


}