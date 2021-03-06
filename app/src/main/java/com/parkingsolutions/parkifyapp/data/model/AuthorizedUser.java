package com.parkingsolutions.parkifyapp.data.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AuthorizedUser {

    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private int rank;
    private String token;

    public void saveSharedPrefs(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("id", id).commit();
        prefs.edit().putString("name", name).commit();
        prefs.edit().putString("surname", surname).commit();
        prefs.edit().putString("email", email).commit();
        prefs.edit().putInt("rank", rank).commit();
        prefs.edit().putString("token", token).commit();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
