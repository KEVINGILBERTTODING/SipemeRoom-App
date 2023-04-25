package com.example.sipemroomapp.Model;


import com.example.sipemroomapp.util.DataApi;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RuanganModel implements Serializable {

    @SerializedName("id_mobil")
    Integer idMobil;
    @SerializedName("kode_tipe")
    String kodeTipe;
    @SerializedName("merek")
    String merk;
    @SerializedName("warna")
    String warna;
    @SerializedName("tahun")
    String tahun;
    @SerializedName("status")
    Integer status;
    @SerializedName("harga")
    String harga;
    @SerializedName("denda")
    String denda;
    @SerializedName("ac")
    Integer ac;
    @SerializedName("mp3_player")
    Integer mp3Player;
    @SerializedName("central_lock")
    String centalLock;
    @SerializedName("gambar")
    String gambar;

    public RuanganModel(Integer idMobil, String kodeTipe, String merk, String warna, String tahun, Integer status, String harga, String denda, Integer ac, Integer mp3Player, String centalLock, String gambar) {
        this.idMobil = idMobil;
        this.kodeTipe = kodeTipe;
        this.merk = merk;
        this.warna = warna;
        this.tahun = tahun;
        this.status = status;
        this.harga = harga;
        this.denda = denda;
        this.ac = ac;
        this.mp3Player = mp3Player;
        this.centalLock = centalLock;
        this.gambar = gambar;
    }

    public Integer getIdMobil() {
        return idMobil;
    }

    public void setIdMobil(Integer idMobil) {
        this.idMobil = idMobil;
    }

    public String getKodeTipe() {
        return kodeTipe;
    }

    public void setKodeTipe(String kodeTipe) {
        this.kodeTipe = kodeTipe;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getDenda() {
        return denda;
    }

    public void setDenda(String denda) {
        this.denda = denda;
    }

    public Integer getAc() {
        return ac;
    }

    public void setAc(Integer ac) {
        this.ac = ac;
    }

    public Integer getMp3Player() {
        return mp3Player;
    }

    public void setMp3Player(Integer mp3Player) {
        this.mp3Player = mp3Player;
    }

    public String getCentalLock() {
        return centalLock;
    }

    public void setCentalLock(String centalLock) {
        this.centalLock = centalLock;
    }

    public String getGambar() {
        return DataApi.IMAGE_URL + gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
