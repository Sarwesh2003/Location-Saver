package com.example.loacationsaver;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loacationsaver.View.LocationListView.LocationListView;
import com.example.loacationsaver.model.adapters.ShowLocationsAdapter;
import com.example.loacationsaver.model.db.DatabaseModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class ListFragment extends BottomSheetDialogFragment {

    RecyclerView list;
    ShowLocationsAdapter adapter;
    DatabaseModel model;
    ArrayList<String> address;
    ArrayList<LatLng> latlang;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocationListView bottomSheetView=new LocationListView(getContext(),view,savedInstanceState,this);
        bottomSheetView.InitiallizeUI();

    }

}