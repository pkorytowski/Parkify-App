package com.parkingsolutions.parkifyapp.data.request;


import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koushikdutta.async.http.BasicNameValuePair;
import com.koushikdutta.async.http.NameValuePair;
import com.koushikdutta.ion.builder.Builders;
import com.parkingsolutions.parkifyapp.MainActivity;
import com.parkingsolutions.parkifyapp.data.Result;
import com.parkingsolutions.parkifyapp.data.model.AuthorizedUser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpRequest {

    String address = "http://10.0.2.2:8080/";
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    public HttpRequest() {
        StrictMode.setThreadPolicy(policy);
    }

    public String get(String path) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);

        try {
            URL obj = new URL(address + path);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", prefs.getString("token", ""));
            //con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                System.out.println("GET request not worked");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean put(String path, JSONObject content) {

        StringBuilder response = new StringBuilder();
        try {
            URL obj = new URL(address + path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));

            writer.write(content.toString());
            writer.flush();
            writer.close();
            os.close();
            //conn.connect();
            int responseCode = conn.getResponseCode();
            Log.d("Response code", "" + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                conn.disconnect();
                return true;
            } else {
                conn.disconnect();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //conn.connect();
        return false;
    }
}
