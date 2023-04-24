package com.example.sipemroomapp.util;

import com.example.sipemroomapp.Model.ResponseModel;
import com.example.sipemroomapp.Model.UserModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface AuthInterface {

    @FormUrlEncoded
    @POST("auth/login")
    Call<UserModel>login(
            @Field("username")String username,
            @Field("password") String password
    );

    @Multipart
    @POST("auth/register")
    Call<ResponseModel>register(
            @PartMap Map<String, RequestBody>textData
            );
}
