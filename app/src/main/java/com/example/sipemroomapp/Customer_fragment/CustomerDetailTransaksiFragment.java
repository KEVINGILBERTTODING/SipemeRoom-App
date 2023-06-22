package com.example.sipemroomapp.Customer_fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.TransactionsModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.CustomerInterface;
import com.example.sipemroomapp.util.DataApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerDetailTransaksiFragment extends Fragment {
    Button btnDownload;
    TextView tvRoomName, tvTanggalSewa, tvTanggalKembali, tvDurasi, tvDate, tvStatus, tvPathFile;
    Integer transId;
    Button btnUploadBuktiPersetujuan;

    CustomerInterface customerInterface;
    private File file;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_detail_transaksi, container, false);
        btnDownload = view.findViewById(R.id.btnDownload);
        tvDurasi = view.findViewById(R.id.tvDurasi);
        tvRoomName = view.findViewById(R.id.tvRoomName);
        tvTanggalKembali = view.findViewById(R.id.tvTglKembali);
        tvTanggalSewa = view.findViewById(R.id.tvTglSewa);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvDate = view.findViewById(R.id.tvDate);
        btnUploadBuktiPersetujuan = view.findViewById(R.id.btnUploadBuktiPersetujuan);

        transId = getArguments().getInt("trans_id");
        customerInterface = DataApi.getClient().create(CustomerInterface.class);

        displayData();
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });

        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH);
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);
        tvDate.setText(day+"-"+month+"-"+year);


        btnUploadBuktiPersetujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_upload);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final Button btnUpload, btnFilePicker, btnBatal;

                btnUpload = dialog.findViewById(R.id.btnUpload);
                btnFilePicker = dialog.findViewById(R.id.btnFilePicker);
                btnBatal = dialog.findViewById(R.id.btnBatal);
                tvPathFile = dialog.findViewById(R.id.tvFilePath);
                dialog.show();

                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnFilePicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("application/pdf|image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
                    }
                });
                btnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (tvPathFile.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "Anda belum memilih file", Toasty.LENGTH_SHORT).show();
                        }else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setMessage("Mengunggah berkas...").setTitle("Uploading").setCancelable(false);
                            AlertDialog pd = alert.create();
                            pd.show();

                            HashMap map = new HashMap();
                            map.put("trans_id", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(transId)));
                            RequestBody requestBody = RequestBody.create(MediaType.parse("pdf/*"), file);
                            MultipartBody.Part filePersetujuan = MultipartBody.Part.createFormData("bukti", file.getName(), requestBody);

                            customerInterface.uploadBuktiPersetujuan(map, filePersetujuan).enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    ResponseModel responseModel = response.body();
                                    if (response.isSuccessful() && responseModel.getStatus() == true) {
                                        Toasty.success(getContext(), "Berhasil upload bukti persetujuan", Toasty.LENGTH_SHORT).show();
                                        displayData();
                                        dialog.dismiss();
                                        pd.dismiss();

                                    }else {
                                        Toasty.error(getContext(), responseModel.getMessage(),Toasty.LENGTH_SHORT).show();
                                        pd.dismiss();
                                    }

                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    Toasty.error(getContext(), "Periksa koneksi internet anda",Toasty.LENGTH_SHORT).show();
                                    pd.dismiss();

                                }
                            });


                        }
                    }
                });
            }
        });




        return view;
    }

    private void displayData() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(false).setTitle("Loading").setMessage("Memuat data...");
        AlertDialog progressDialog = alert.create();
        progressDialog.show();
        customerInterface.getDetailTransaction(transId).enqueue(new Callback<TransactionsModel>() {
            @Override
            public void onResponse(Call<TransactionsModel> call, Response<TransactionsModel> response) {
                TransactionsModel transactionsModel = response.body();
                if (response.isSuccessful()  && transactionsModel !=null) {
                    tvRoomName.setText(transactionsModel.getRoomName());
                    tvTanggalSewa.setText(transactionsModel.getTglRental());
                    tvTanggalKembali.setText(transactionsModel.getTglKembali());
                    tvDurasi.setText(String.valueOf(transactionsModel.getDurasi())+" hari");
                    if (transactionsModel.getStatusPembayaran() == 1) {
                        tvStatus.setText("Telah disetujui");
                        tvStatus.setTextColor(getContext().getColor(R.color.green));
                        btnUploadBuktiPersetujuan.setTextColor(getContext().getColor(R.color.white));
                        btnUploadBuktiPersetujuan.setText("Telah disetujui");
                        btnUploadBuktiPersetujuan.setEnabled(false);
                    }else {
                        tvStatus.setText("belum disetujui");
                        tvStatus.setTextColor(getContext().getColor(R.color.red));
                        if (transactionsModel.getBuktiPembayaran().equals("")) {
                            btnUploadBuktiPersetujuan.setEnabled(true);
                        }else {
                            btnUploadBuktiPersetujuan.setEnabled(false);
                            btnUploadBuktiPersetujuan.setText("Menunggu konfirmasi");
                            btnUploadBuktiPersetujuan.setTextColor(getContext().getColor(R.color.white));

                        }
                    }


                    progressDialog.dismiss();
                }else {

                   dialogRefresh();
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<TransactionsModel> call, Throwable t) {
                dialogRefresh();
                progressDialog.dismiss();

            }
        });

    }

    private void download() {
        String url = DataApi.CUSTOMER_DOWNLOAD_INVOICE+transId;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void dialogRefresh() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.menu_retry);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnRefresh;
        btnRefresh = dialog.findViewById(R.id.btnRefresh);

        dialog.show();
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayData();
                dialog.dismiss();

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                file = new File(pdfPath);
                tvPathFile.setText(file.getName());

            }
        }
    }


    public String getRealPathFromUri(Uri uri) {
        String filePath = "";
        if (getContext().getContentResolver() != null) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                File file = new File(getContext().getCacheDir(), getFileName(uri));
                writeFile(inputStream, file);
                filePath = file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void writeFile(InputStream inputStream, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

}