package com.example.loacationsaver.View.SplashScreenView;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.loacationsaver.MainActivity;
import com.example.loacationsaver.R;
import com.example.loacationsaver.SplashScreen;

public class SplashView extends AppCompatActivity implements SplashInterface {
    Activity activity;
    View root;

    public SplashView(Context context, ViewGroup container, Activity activity){
        root= LayoutInflater.from(context).inflate(R.layout.activity_splash_screen,container);
        this.activity=activity;
    }

    @Override
    public void SleepMethod() {
        new Handler().postDelayed(() -> {
            Intent newActivity = new Intent(root.getContext(), MainActivity.class);
            newActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            root.getContext().startActivity(newActivity);
        },5000);
    }
    @Override
    public View getRootView() {
        return root;
    }
}
