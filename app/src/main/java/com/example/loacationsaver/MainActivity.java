package com.example.loacationsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loacationsaver.View.ViewImplementor;
import com.example.loacationsaver.View.ViewInterface;
import com.example.loacationsaver.model.LocationModel;
import com.example.loacationsaver.model.db.LocationDBAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewImplementor view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=new ViewImplementor(MainActivity.this,null);
        setContentView(view.getRootView());
        view.initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.BindDataToView();
    }
}