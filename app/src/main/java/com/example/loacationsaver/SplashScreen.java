package com.example.loacationsaver;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.loacationsaver.View.MainAcitivtyView.ViewImplementor;
import com.example.loacationsaver.View.SplashScreenView.SplashView;

import java.util.List;
import java.util.Objects;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashScreen extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    SplashView view;
    LottieAnimationView lav;
    public static final int FINE_LOCATION=100;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=new SplashView(SplashScreen.this,null,SplashScreen.this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view.getRootView());
        CheckPermission(Manifest.permission.ACCESS_FINE_LOCATION,FINE_LOCATION);
    }

    public void CheckPermission(String permission, int requestCode) {
        if (EasyPermissions.hasPermissions(this, permission)) {
            view.SleepMethod();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.location_rationale),
                    requestCode, permission);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, "Returned", Toast.LENGTH_SHORT)
                    .show();
        }
        if (requestCode == FINE_LOCATION) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        view.SleepMethod();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        Toast.makeText(this, "Allow Location Permission from Settings", Toast.LENGTH_SHORT)
                .show();
        //CheckPermission(Manifest.permission.ACCESS_FINE_LOCATION,FINE_LOCATION);
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckPermission(Manifest.permission.ACCESS_FINE_LOCATION,FINE_LOCATION);
    }
}