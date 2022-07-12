package com.parkingsolutions.parkifyapp.data.request;


import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import com.koushikdutta.ion.builder.Builders;
import com.parkingsolutions.parkifyapp.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

    String address = "http://10.0.2.2:8080/";
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    public HttpRequest() {
        StrictMode.setThreadPolicy(policy);
    }

    public String get(String path) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);

        try {
            URL obj = new URL(address+"path");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
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
}
