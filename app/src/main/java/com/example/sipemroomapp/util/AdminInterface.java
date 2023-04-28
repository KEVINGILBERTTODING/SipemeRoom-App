package com.example.sipemroomapp.util;

import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.RuanganModel;
import com.example.sipemroomapp.Model.TipeModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface AdminInterface {
    @GET("admin/getAllRuangan")
    Call<List<RuanganModel>>getAllRoom();

    @GET("admin/getalltipe")
    Call<List<TipeModel>>getAllTipe();

    @Multipart
    @POST("admin/insertRoom")
    Call<ResponseModel>insertRoom(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part image
            );

    @FormUrlEncoded
    @POST("admin/deleteroom")
    Call<ResponseModel>deleteRoom(
            @Field("room_id") Integer roomId
    );

    @Multipart
    @POST("admin/updateRoom")
    Call<ResponseModel>updateRoom(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part image
    );
    @Multipart
    @POST("admin/updateRoom")
    Call<ResponseModel>updateRoomNotImage(
            @PartMap Map<String, RequestBody>textData

    );

    @FormUrlEncoded
    @POST("admin/inserttipe")
    Call<ResponseModel>insertTipe(
            @Field("kode_tipe") String kodeTipe,
            @Field("nama_tipe") String namaTipe
    );

    @FormUrlEncoded
    @POST("admin/deletetipe")
    Call<ResponseModel>deleteTipe(
            @Field("id") Integer id
    );
}
