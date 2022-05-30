package com.parkingsolutions.parkifyapp.data;

import android.os.StrictMode;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koushikdutta.async.http.BasicNameValuePair;
import com.koushikdutta.async.http.NameValuePair;
import com.parkingsolutions.parkifyapp.common.JSONMapper;
import com.parkingsolutions.parkifyapp.data.model.AuthorizedUser;
import com.parkingsolutions.parkifyapp.data.model.LoggedInUser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    public LoginDataSource() {
        StrictMode.setThreadPolicy(policy);
    }

    public Result<AuthorizedUser> login(String username, String password) {

        String json_req = "http://localhost:8080/login/user";
        StringBuilder response = new StringBuilder();
        try {
            URL url;
            url = new URL("http://10.0.2.2:8080/login/user");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", username));
            params.add(new BasicNameValuePair("password", password));

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", username);
            jsonObject.put("password", password);

            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();
            //conn.connect();
            int responseCode = conn.getResponseCode();
            Log.d("Response code", "" + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            } else {
                response = new StringBuilder();

            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //conn.connect();

        String resp = response.toString();

        if (resp.equals("")) {
            return new Result.Error(null);
        }
        // TODO: handle loggedInUser authentication
        ObjectMapper mapper = new ObjectMapper();
        AuthorizedUser user;
        try {
            user = mapper.readValue(resp, AuthorizedUser.class);
            return new Result.Success<>(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(null);
        }

    }



    public void logout() {
        // TODO: revoke authentication
    }
}