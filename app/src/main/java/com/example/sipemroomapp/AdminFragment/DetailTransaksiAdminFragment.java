package com.example.sipemroomapp.AdminFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sipemroomapp.FileDownload;
import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.AdminInterface;
import com.example.sipemroomapp.util.DataApi;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailTransaksiAdminFragment extends Fragment {
    TextView tvNama, tvRuangan, tvtglSewa, tvTglKembali, tvTglSelesai,
            tvStatusSelesai, tvStatusSewa;
    Button btnDownload, btnKonfirmasi, btnSelesai, btnReject, btnKembali;
    String transId;
    AdminInterface adminInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_transaksi_admin, container, false);

        init(view);
        adminInterface = DataApi.getClient().create(AdminInterface.class);

        tvNama.setText(getArguments().getString("nama_customer"));
        tvRuangan.setText(getArguments().getString("ruangan"));
        tvtglSewa.setText(getArguments().getString("tgl_sewa"));
        tvTglKembali.setText(getArguments().getString("tgl_kembali"));
        tvTglSelesai.setText(getArguments().getString("tgl_selesai"));
        tvStatusSewa.setText(getArguments().getString("status_pengembalian"));
        tvStatusSelesai.setText(getArguments().getString("status_selesai"));

        transId = getArguments().getString("id_rental");

        //disabled button download jika tidak ada bukti persetujuan
        if (getArguments().getString("bukti_pembayaran").equals("")) {
            btnDownload.setEnabled(false);
            btnDownload.setText("Tidak ada bukti persetujuan");
            btnDownload.setTextColor(getContext().getColor(R.color.white));
            btnDownload.setBackgroundColor(getContext().getColor(R.color.dark_gray));
            btnKonfirmasi.setVisibility(View.GONE);

        }

        Log.d("dsad", "onCreateView: "+getArguments().getString("status_pembayaran"));

        // jika status persetujuan  == TRUE maka hide button konfirmasi
        if (getArguments().getString("status_pembayaran").equals("1")) {
            btnKonfirmasi.setVisibility(View.GONE);
            btnSelesai.setVisibility(View.VISIBLE);
            btnReject.setVisibility(View.VISIBLE);
        }

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = DataApi.DOWNLOAD_BUKTI_PERSETUJUAN+transId;
                String title = "Buti_Persetujuan_"+transId;
                String description = "Downloading PDF file";
                String fileName = "Bukti_persetujuan_"+transId+".pdf";
                Log.d("dasd", "onClick: " +url);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permissions, 1000);
                    } else {

                        FileDownload fileDownload = new FileDownload(getContext());
                        fileDownload.downloadFile(url, title, description, fileName);

                    }
                } else {

                    FileDownload fileDownload = new FileDownload(getContext());
                    fileDownload.downloadFile(url, title, description, fileName);
                }
            }
        });
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Loading").setCancelable(false).setMessage("Konfirmasi data...");
                AlertDialog progressDialog = alert.create();
                progressDialog.show();
                adminInterface.konfirmasi(transId).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        ResponseModel responseModel = response.body();
                        if (response.isSuccessful() && responseModel.getStatus() == true) {
                            Toasty.success(getContext(), "Transaksi berhasil dikonfirmasi", Toasty.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                            progressDialog.dismiss();

                        }else {
                            Toasty.error(getContext(), "Something went wrong", Toasty.LENGTH_SHORT).show();

                            progressDialog.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                        Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                });


            }
        });


        return view;
    }


    private void init(View view) {
        tvNama = view.findViewById(R.id.etNama);
        tvtglSewa = view.findViewById(R.id.etTglSewa);
        tvTglKembali = view.findViewById(R.id.etTglKembali);
        tvTglSelesai = view.findViewById(R.id.etTglSelesai);
        tvStatusSewa = view.findViewById(R.id.etStatusSewa);
        tvStatusSelesai = view.findViewById(R.id.etStatusSelesai);
        tvRuangan = view.findViewById(R.id.etRoomName);
        btnDownload = view.findViewById(R.id.btnDownloadBukttiPersetujuan);
        btnKonfirmasi = view.findViewById(R.id.btnAccBuktiPersetujuan);
        btnSelesai = view.findViewById(R.id.btnSelesai);
        btnReject = view.findViewById(R.id.btnTolak);
        btnKembali = view.findViewById(R.id.btnKembali) ;




    }
}