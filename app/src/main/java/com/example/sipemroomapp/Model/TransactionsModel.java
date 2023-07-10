package com.example.sipemroomapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransactionsModel implements Serializable {
    @SerializedName("id_sewa")
    String idRental;
    @SerializedName("id_customer")
    String idCustomer;
    @SerializedName("id_ruangan")
    String roomId;
    @SerializedName("tgl_sewa")
    String tglRental;
    @SerializedName("tgl_kembali")
    String tglKembali;
    @SerializedName("tgl_pengembalian")
    String tglPengembalian;
    @SerializedName("status_pengembalian")
    String statusPengembalian;
    @SerializedName("status_sewa")
    String statusRental;
    @SerializedName("bukti_apr")
    String buktiPembayaran;
    @SerializedName("status_apr")
    Integer statusPembayaran;
    @SerializedName("nama")
    String nama;

    @SerializedName("durasi")
    Integer durasi;

    @SerializedName("nama_ruangan")
    String roomName;
    @SerializedName("kapasitas")
    Integer totalPeople;
    public TransactionsModel(String idRental, String idCustomer, Integer durasi, String roomId, String tglRental, String tglKembali, Integer totalPeople, Double harga, Double denda, Double totalDenda, String tglPengembalian, String statusPengembalian, String statusRental, String buktiPembayaran, Integer statusPembayaran, String nama, String roomName) {
        this.idRental = idRental;
        this.idCustomer = idCustomer;
        this.roomId = roomId;
        this.tglRental = tglRental;
        this.tglKembali = tglKembali;
        this.totalPeople = totalPeople;
        this.tglPengembalian = tglPengembalian;
        this.statusPengembalian = statusPengembalian;
        this.statusRental = statusRental;
        this.buktiPembayaran = buktiPembayaran;
        this.statusPembayaran = statusPembayaran;
        this.nama = nama;
        this.durasi = durasi;
        this.roomName = roomName;
    }

    public String getIdRental() {
        return idRental;
    }

    public void setIdRental(String idRental) {
        this.idRental = idRental;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getTglRental() {
        return tglRental;
    }

    public void setTglRental(String tglRental) {
        this.tglRental = tglRental;
    }

    public String getTglKembali() {
        return tglKembali;
    }

    public void setTglKembali(String tglKembali) {
        this.tglKembali = tglKembali;
    }


    public String getTglPengembalian() {
        return tglPengembalian;
    }

    public void setTglPengembalian(String tglPengembalian) {
        this.tglPengembalian = tglPengembalian;
    }

    public String getStatusPengembalian() {
        return statusPengembalian;
    }

    public void setStatusPengembalian(String statusPengembalian) {
        this.statusPengembalian = statusPengembalian;
    }

    public String getStatusRental() {
        return statusRental;
    }

    public void setStatusRental(String statusRental) {
        this.statusRental = statusRental;
    }

    public String getBuktiPembayaran() {
        return buktiPembayaran;
    }

    public void setBuktiPembayaran(String buktiPembayaran) {
        this.buktiPembayaran = buktiPembayaran;
    }

    public Integer getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(Integer statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setDurasi(Integer durasi) {
        this.durasi = durasi;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setTotalPeople(Integer totalPeople) {
        this.totalPeople = totalPeople;
    }

    public Integer getTotalPeople() {
        return totalPeople;
    }

    public Integer getDurasi() {
        return durasi;
    }
}
