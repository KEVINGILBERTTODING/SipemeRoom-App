package com.example.sipemroomapp.util;

import com.example.sipemroomapp.Model.RuanganModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AdminInterface {
    @GET("admin/getAllRuangan")
    Call<List<RuanganModel>>getAllRoom();
}
