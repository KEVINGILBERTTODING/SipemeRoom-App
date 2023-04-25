package com.example.sipemroomapp.Customer_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sipemroomapp.R;


public class CustomerDetaillRoomFragment extends Fragment {
    TextView tvRoomName, tvTotalPerson, tvDekorasi, tvTahun, tvStatus;
    Button btnSewa;
    ImageView ivRoom;
    Integer status, roomId;
    String gambar, room_name, dekorasi, tahun;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_custome_detaill_room, container, false);
       tvRoomName = view.findViewById(R.id.tvRoomName);
       tvTotalPerson = view.findViewById(R.id.tvTotalPerson);
       tvDekorasi  = view.findViewById(R.id.tvDekorasi);
       tvTahun = view.findViewById(R.id.tvTahun);
       tvStatus = view.findViewById(R.id.tvStatus);
       btnSewa = view.findViewById(R.id.btnSewa);
       ivRoom = view.findViewById(R.id.ivRoom);

       roomId = getArguments().getInt("room_id");
       status = getArguments().getInt("status");
       gambar = getArguments().getString("gambar");
       room_name = getArguments().getString("room_name");
       dekorasi = getArguments().getString("dekorasi");
       tahun = getArguments().getString("tahun");

       if (status.equals("1")) {
           btnSewa.setEnabled(false);
           btnSewa.setBackgroundColor(getContext().getResources().getColor(R.color.red));
           btnSewa.setText("Telah sewa");
           btnSewa.setTextColor(getContext().getResources().getColor(R.color.white));
           tvStatus.setText("Tidak tersedia / sedang dirental");
       }else {
           tvStatus.setText("Tersedia");
       }

        Glide.with(getContext())
                        .load(gambar).dontAnimate().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).into(ivRoom);


       tvRoomName.setText(room_name);
       tvDekorasi.setText(dekorasi);
       tvTahun.setText(tahun);



       return view;
    }
}