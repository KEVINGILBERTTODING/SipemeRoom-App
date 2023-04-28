package com.example.sipemroomapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TipeModel implements Serializable {
    @SerializedName("id_tipe")
    String idTipe;
    @SerializedName("kode_tipe")
    String kodeTipe;
    @SerializedName("nama_tipe")
    String namaTipe;

    public TipeModel(String idTipe, String kodeTipe, String namaTipe) {
        this.idTipe = idTipe;
        this.kodeTipe = kodeTipe;
        this.namaTipe = namaTipe;
    }

    public String getIdTipe() {
        return idTipe;
    }

    public void setIdTipe(String idTipe) {
        this.idTipe = idTipe;
    }

    public String getKodeTipe() {
        return kodeTipe;
    }

    public void setKodeTipe(String kodeTipe) {
        this.kodeTipe = kodeTipe;
    }

    public String getNamaTipe() {
        return namaTipe;
    }

    public void setNamaTipe(String namaTipe) {
        this.namaTipe = namaTipe;
    }
}
