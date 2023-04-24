package com.example.sipemroomapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {
    @SerializedName("nama")
    String nama;
    @SerializedName("user_id")
    String userId;
    @SerializedName("username")
    String username;
    @SerializedName("role")
    String role;
    @SerializedName("code")
    String code;
    @SerializedName("message")
    String message;

    public UserModel(String nama, String userId, String username, String role, String code, String message) {
        this.nama = nama;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.code = code;
        this.message = message;
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
}
