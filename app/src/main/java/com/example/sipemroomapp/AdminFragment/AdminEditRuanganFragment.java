package com.example.sipemroomapp.AdminFragment;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sipemroomapp.AdminAdapter.SpinnerTipeAdapter;
import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.TipeModel;
import com.example.sipemroomapp.R;
import com.example.sipemroomapp.util.AdminInterface;
import com.example.sipemroomapp.util.DataApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminEditRuanganFragment extends Fragment {
    Spinner spTipe, spAc, spMakanan, spMp3, spCentralLock, spStatus;
    Button btnImagePicker, btnSave;
    String kodeTipe;
    ImageView ivGambar;
    Integer ac, makanan, mp3, centralLock, status;
    EditText etRuangan, etKapasitas, etDekorasi, etHargaSewa, etDenda, etTahun;
    String [] opsiJenis = {"Tersedia", "Tidak tersedia"};
    private String TAG ="testing";
    SpinnerTipeAdapter spinnerTipeAdapter;

    AdminInterface adminInterface;
    File file;
    TextView tvPathImage;
    ArrayAdapter adapterJenis;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_edit_ruangan, container, false);
        spTipe = view.findViewById(R.id.spTipe);
        spMakanan = view.findViewById(R.id.spMakanan);
        spMp3 = view.findViewById(R.id.spMp3);
        btnImagePicker = view.findViewById(R.id.btnImagePicker);
        spCentralLock = view.findViewById(R.id.spCentralLock);
        spAc = view.findViewById(R.id.spAc);
        btnSave = view.findViewById(R.id.btnSave);
        etRuangan = view.findViewById(R.id.etRuangan);
        etKapasitas = view.findViewById(R.id.etKapasitas);
        etDekorasi = view.findViewById(R.id.etDekorasi);
        etHargaSewa = view.findViewById(R.id.etHargaSewa);
        etDenda = view.findViewById(R.id.etDenda);
        spStatus = view.findViewById(R.id.spStatus);
        etTahun = view.findViewById(R.id.etTahun);
        ivGambar = view.findViewById(R.id.ivGambar);
        tvPathImage = view.findViewById(R.id.tvPathImage);
        adminInterface = DataApi.getClient().create(AdminInterface.class);

        adapterJenis = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,opsiJenis);
        adapterJenis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spAc.setAdapter(adapterJenis);
        spMp3.setAdapter(adapterJenis);
        spMakanan.setAdapter(adapterJenis);
        spStatus.setAdapter(adapterJenis);

        spCentralLock.setAdapter(adapterJenis);

        etRuangan.setText(getArguments().getString("room_name"));
        etKapasitas.setText(getArguments().getString("kapasitas"));
        etDekorasi.setText(getArguments().getString("dekorasi"));
        etHargaSewa.setText(getArguments().getString("harga_sewa"));
        etDenda.setText(getArguments().getString("denda"));
        etDenda.setText(getArguments().getString("denda"));
        etTahun.setText(getArguments().getString("tahun"));

        Glide.with(getContext())
                .load(getArguments().getString("gambar"))
                .dontAnimate().skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivGambar);





        spMakanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapterJenis.getItem(position).equals("Tersedia")) {
                    makanan =1;
                }else {
                    makanan = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spAc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapterJenis.getItem(position).equals("Tersedia")) {
                    ac =1;
                }else {
                    ac = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spMp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapterJenis.getItem(position).equals("Tersedia")) {
                    mp3 =1;
                }else {
                    mp3 = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapterJenis.getItem(position).equals("Tersedia")) {
                    status =1;
                }else {
                    status = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spCentralLock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapterJenis.getItem(position).equals("Tersedia")) {
                    centralLock =1;
                }else {
                    centralLock = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kodeTipe = spinnerTipeAdapter.getKodeTipe(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getTipe();

        btnImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etRuangan.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field ruangan tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etKapasitas.getText().toString().isEmpty()) {
                        Toasty.error(getContext(), "Field kapasitas tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etDekorasi.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field dekorasi tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etHargaSewa.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field harga sewa tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etDenda.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field denda tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etTahun.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field tahun tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setMessage("Mengirim data...").setTitle("Loading").setCancelable(false);
                    AlertDialog progressDialog = alert.create();
                    progressDialog.show();







                    if (tvPathImage.getText().toString().isEmpty()) {

                        HashMap map = new HashMap();
                        map.put("kode_tipe", RequestBody.create(MediaType.parse("text/plain"), kodeTipe));
                        map.put("room_name", RequestBody.create(MediaType.parse("text/plain"), etRuangan.getText().toString()));
                        map.put("kapasitas", RequestBody.create(MediaType.parse("text/plain"), etKapasitas.getText().toString()));
                        map.put("dekorasi", RequestBody.create(MediaType.parse("text/plain"), etDekorasi.getText().toString()));
                        map.put("tahun", RequestBody.create(MediaType.parse("text/plain"), etTahun.getText().toString()));
                        map.put("harga", RequestBody.create(MediaType.parse("text/plain"), etHargaSewa.getText().toString()));
                        map.put("denda", RequestBody.create(MediaType.parse("text/plain"), etDenda.getText().toString()));
                        map.put("status", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(status)));
                        map.put("ac", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ac)));
                        map.put("sopir", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(makanan)));
                        map.put("mp3_player", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mp3)));
                        map.put("central_lock", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(centralLock)));
                        map.put("room_id", RequestBody.create(MediaType.parse("text/plain"), getArguments().getString("room_id")));

                        adminInterface.updateRoomNotImage(map).enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                ResponseModel responseModel = response.body();
                                if (response.isSuccessful() && responseModel.getStatus() == true) {
                                    Toasty.success(getContext(), "Berhasil mengubah data", Toasty.LENGTH_SHORT).show();
                                    ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, new AdminDataRuanganFragment()).commit();
                                    progressDialog.dismiss();

                                }else {
                                    Toasty.error(getContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                        });

                    }else {
                        HashMap map = new HashMap();
                        map.put("kode_tipe", RequestBody.create(MediaType.parse("text/plain"), kodeTipe));
                        map.put("room_name", RequestBody.create(MediaType.parse("text/plain"), etRuangan.getText().toString()));
                        map.put("kapasitas", RequestBody.create(MediaType.parse("text/plain"), etKapasitas.getText().toString()));
                        map.put("dekorasi", RequestBody.create(MediaType.parse("text/plain"), etDekorasi.getText().toString()));
                        map.put("tahun", RequestBody.create(MediaType.parse("text/plain"), etTahun.getText().toString()));
                        map.put("harga", RequestBody.create(MediaType.parse("text/plain"), etHargaSewa.getText().toString()));
                        map.put("denda", RequestBody.create(MediaType.parse("text/plain"), etDenda.getText().toString()));
                        map.put("status", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(status)));
                        map.put("ac", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ac)));
                        map.put("sopir", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(makanan)));
                        map.put("mp3_player", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mp3)));
                        map.put("central_lock", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(centralLock)));
                        map.put("path_image", RequestBody.create(MediaType.parse("text/plain"), "dasdas"));
                        map.put("room_id", RequestBody.create(MediaType.parse("text/plain"), getArguments().getString("room_id")));
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part image = MultipartBody.Part.createFormData("gambar", file.getName(), requestBody);


                        adminInterface.updateRoom(map, image).enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                ResponseModel responseModel = response.body();
                                if (response.isSuccessful() && responseModel.getStatus() == true) {
                                    Toasty.success(getContext(), "Berhasil mengubah data", Toasty.LENGTH_SHORT).show();
                                    ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, new AdminDataRuanganFragment()).commit();
                                    progressDialog.dismiss();

                                }else {
                                    Toasty.error(getContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
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



                }


            }
        });






        return view;
    }

    private void getTipe() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(false).setMessage("Memuat data...").setTitle("Loading");
        AlertDialog progressDialog = alert.create();
        progressDialog.show();

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.menu_retry);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        final Button btnRetry = dialog.findViewById(R.id.btnRefresh);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTipe();
            }
        });


        adminInterface.getAllTipe().enqueue(new Callback<List<TipeModel>>() {
            @Override
            public void onResponse(Call<List<TipeModel>> call, Response<List<TipeModel>> response) {

                if (response.isSuccessful() && response.body().size() > 0) {
                    List<TipeModel> tipeModelList = response.body();
                    spinnerTipeAdapter = new SpinnerTipeAdapter(getContext(), tipeModelList);
                    spTipe.setAdapter(spinnerTipeAdapter);
                    progressDialog.dismiss();
                    dialog.dismiss();

                }else {
                    Toasty.error(getContext(), "Gagal memuat data...", Toasty.LENGTH_SHORT).show();
                    dialog.show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<TipeModel>> call, Throwable t) {
                Toasty.error(getContext(), "Periksa koneksi internet anda", Toasty.LENGTH_SHORT).show();
                dialog.show();
                progressDialog.dismiss();

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
                ivGambar.setVisibility(View.VISIBLE);
                ivGambar.setImageURI(uri);
                tvPathImage.setText(file.getName());
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