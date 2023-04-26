package com.example.sipemroomapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransactionsModel implements Serializable {
    @SerializedName("id_rental")
    String idRental;
    @SerializedName("id_customer")
    String idCustomer;
    @SerializedName("id_mobil")
    String roomId;
    @SerializedName("tgl_rental")
    String tglRental;
    @SerializedName("tgl_kembali")
    String tglKembali;
    @SerializedName("harga")
   Double harga;
    @SerializedName("denda")
   Double denda;
    @SerializedName("total_denda")
   Double totalDenda;
    @SerializedName("tgl_pengembalian")
    String tglPengembalian;
    @SerializedName("status_pengembalian")
    String statusPengembalian;
    @SerializedName("status_rental")
    String statusRental;
    @SerializedName("bukti_pembayaran")
    String buktiPembayaran;
    @SerializedName("status_pembayaran")
    Integer statusPembayaran;
    @SerializedName("nama")
    String nama;

    @SerializedName("merek")
    String roomName;
    @SerializedName("no_plat")
    Integer totalPeople;
    public TransactionsModel(String idRental, String idCustomer, String roomId, String tglRental, String tglKembali, Integer totalPeople, Double harga, Double denda, Double totalDenda, String tglPengembalian, String statusPengembalian, String statusRental, String buktiPembayaran, Integer statusPembayaran, String nama, String roomName) {
        this.idRental = idRental;
        this.idCustomer = idCustomer;
        this.roomId = roomId;
        this.tglRental = tglRental;
        this.tglKembali = tglKembali;
        this.harga = harga;
        this.totalPeople = totalPeople;
        this.denda = denda;
        this.totalDenda = totalDenda;
        this.tglPengembalian = tglPengembalian;
        this.statusPengembalian = statusPengembalian;
        this.statusRental = statusRental;
        this.buktiPembayaran = buktiPembayaran;
        this.statusPembayaran = statusPembayaran;
        this.nama = nama;
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

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public Double getDenda() {
        return denda;
    }

    public void setDenda(Double denda) {
        this.denda = denda;
    }

    public Double getTotalDenda() {
        return totalDenda;
    }

    public void setTotalDenda(Double totalDenda) {
        this.totalDenda = totalDenda;
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

    public String getRoomName() {
        return roomName;
    }

    public Integer getTotalPeople() {
        return totalPeople;
    }
}
