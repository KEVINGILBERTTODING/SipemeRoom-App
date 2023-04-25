package com.example.sipemroomapp.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataApi {
    final  public static String IP_ADDRESS = "192.168.100.6";// ISI IP ADDRESS ANDA
    final public static String BASE_URL = "http://"+IP_ADDRESS+"/sewa/api/";
    final public static String IMAGE_URL = "http://"+IP_ADDRESS+"/sewa/assets/upload/";

    public static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}
