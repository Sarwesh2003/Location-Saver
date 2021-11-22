package com.example.loacationsaver.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loacationsaver.MainActivity;
import com.example.loacationsaver.R;
import com.example.loacationsaver.controller.LocationController;
import com.example.loacationsaver.model.LocationData;
import com.example.loacationsaver.model.LocationModel;
import com.example.loacationsaver.model.db.LocationDBAdapter;

import java.util.Arrays;
import java.util.List;

public class ViewImplementor implements MainActivityView {

    View root;
    LocationController controller;

    ArrayAdapter<String> adapter;
    //List<String> list;

    Button add;
    ListView show;
    TextView tv_lat,tv_lang;
    private LocationModel model;

    public ViewImplementor(Context context, ViewGroup container){
        root= LayoutInflater.from(context).inflate(R.layout.activity_main,container);
        model=new LocationModel(LocationDBAdapter.getLocationInstance(context));
        controller=new LocationController(model,this);
    }

    @Override
    public void initView() {
        add=(Button) root.findViewById(R.id.savelocation);
        show=(ListView) root.findViewById(R.id.show);
        tv_lat=(TextView) root.findViewById(R.id.tv_lat);
        tv_lang=(TextView) root.findViewById(R.id.tv_lang);
        this.BindDataToView();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.OnClickSaveLocation(String.valueOf(tv_lat.getText()),String.valueOf(tv_lang.getText()));

            }
        });

        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] latlang=show.getItemAtPosition(position).toString().split(",");
                adapter.remove(show.getItemAtPosition(position).toString());
                controller.DeleteLocations(latlang[0],latlang[1]);
            }
        });
    }

    @Override
    public View getRootView() {
        return root;
    }

    @Override
    public void UpdateView(List<String> list) {
        this.ShowAllLocations(list);
    }

    @Override
    public void BindDataToView() {
        controller.onViewLoaded();
    }

    @Override
    public void ShowAllLocations(List<String> list) {
        try {
            adapter = new ArrayAdapter<String>(root.getContext(),R.layout.listview,list);
            show.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.d("Error From: ","ShowAllLocations");
            this.ShowError(e.getMessage());
        }

    }

    @Override
    public void ShowError(String error) {
        Toast.makeText(root.getContext(),error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowSuccess(String SuccessMsg) {
        Toast.makeText(root.getContext(),SuccessMsg,Toast.LENGTH_SHORT).show();
    }
}
