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

    public UserModel(String nama, String userId, String username, String role) {
        this.nama = nama;
        this.userId = userId;
        this.username = username;
        this.role = role;
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
}
