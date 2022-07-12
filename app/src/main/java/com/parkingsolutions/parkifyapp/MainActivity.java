package com.parkingsolutions.parkifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.parkingsolutions.parkifyapp.data.LoginDataSource;
import com.parkingsolutions.parkifyapp.data.LoginRepository;
import com.parkingsolutions.parkifyapp.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);

        if (prefs.getBoolean("isLogin", false)) {

            Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);
        }
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}