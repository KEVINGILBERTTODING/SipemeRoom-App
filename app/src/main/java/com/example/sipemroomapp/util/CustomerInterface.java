package com.example.sipemroomapp.util;

import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.RuanganModel;
import com.example.sipemroomapp.Model.TransactionsModel;
import com.example.sipemroomapp.Model.UserModel;

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
import retrofit2.http.Query;

public interface CustomerInterface {

    @GET("customer/getAllRuangan")
    Call<List<RuanganModel>>getALLRuangan();

    @Multipart
    @POST("customer/sewa")
    Call<ResponseModel>sewa(
            @PartMap Map<String, RequestBody>textData
            );

    @GET("customer/getMyTransactions")
    Call<List<TransactionsModel>>getMyTransactions(
            @Query("user_id") String userId
    );

    @FormUrlEncoded
    @POST("customer/orderCancel")
    Call<ResponseModel>cancelOrder(
            @Field("room_id") String roomId,
            @Field("trans_id") String transId
    );

    @FormUrlEncoded
    @POST("customer/detailTransaction")
    Call<TransactionsModel>getDetailTransaction(
            @Field("trans_id") Integer transId
    );
    @Multipart
    @POST("customer/uploadPersetujuan")
    Call<ResponseModel>uploadBuktiPersetujuan(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part buktiPersetujuan
            );

    @FormUrlEncoded
    @POST("customer/ubahPassword")
    Call<ResponseModel>ubahPassword(
            @Field("id_customer") String userId,
            @Field("old_pass") String oldPass,
            @Field("pass_new") String passNew
    );

    @Multipart
    @POST("customer/updateProfile")
    Call<ResponseModel>updateProfile(
            @PartMap Map<String, RequestBody>textData
    );

    @GET("customer/getUserById")
    Call<List<UserModel>>getUserById(
            @Query("id_customer") String idCustomer
    );

    @GET("customer/getUserOrder")
    Call<UserModel> getUserOrder(
            @Query("id") String id
    );

}
