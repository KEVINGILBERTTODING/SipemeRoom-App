package com.example.sipemroomapp.util;

import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.RuanganModel;
import com.example.sipemroomapp.Model.TransactionsModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
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

}
