package com.example.sipemroomapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {
    @SerializedName("nama")
    String nama;
    @SerializedName("user_id")
    String userId;
    @SerializedName("id_customer")
    String customerId;
    @SerializedName("username")
    String username;
    @SerializedName("role")
    String role;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("no_telepon")
    String noTelp;
    @SerializedName("no_ktp")
    String noKtp;
    @SerializedName("code")
    String code;
    @SerializedName("message")
    String message;

    public UserModel(String nama, String userId, String customerId, String alamat, String noTelp, String noKtp, String username, String role, String code, String message) {
        this.nama = nama;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.code = code;
        this.noKtp = noKtp;
        this.alamat = alamat;
        this.noTelp = noTelp;
        this.message = message;
        this.customerId = customerId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustomerId() {
        return customerId;
    }
}
