package com.example.loacationsaver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.loacationsaver.View.MainAcitivtyView.ViewImplementor;
import com.example.loacationsaver.View.SplashScreenView.SplashView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ViewImplementor view;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=new ViewImplementor(MainActivity.this,null);
        setContentView(view.getRootView());
        view.initView();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
                view.initView();
            } else {
                Toast.makeText(MainActivity.this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        view.BindDataToView();
    }
}