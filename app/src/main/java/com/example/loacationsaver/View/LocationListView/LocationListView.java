package com.example.loacationsaver.View.LocationListView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loacationsaver.ListFragment;
import com.example.loacationsaver.R;
import com.example.loacationsaver.controller.LocationList.ListController;
import com.example.loacationsaver.model.adapters.LocationDBAdapter;
import com.example.loacationsaver.model.adapters.ShowLocationsAdapter;
import com.example.loacationsaver.model.db.DatabaseModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LocationListView implements ShowLocationsAdapter.OnLocationClickListener {
    Context mContext;
    RecyclerView list;
    View root;
    ShowLocationsAdapter adapter;
    Bundle savedInstanceState;
    ArrayList<String> address;
    ArrayList<LatLng> latlang;
    ListController controller;
    SearchView searchView;
    ListFragment bottomSheetFragment;
    public LocationListView(Context mContext, View root, Bundle savedInstanceState, ListFragment bottomSheet) {
        this.mContext = mContext;
        this.root=root;
        this.savedInstanceState=savedInstanceState;
        this.bottomSheetFragment=bottomSheet;
        controller=new ListController(mContext,new DatabaseModel(new LocationDBAdapter(mContext)));
    }

    public void InitiallizeUI(){
        list=root.findViewById(R.id.locationList);
        LinearLayoutManager manager=new LinearLayoutManager(mContext);
        list.setLayoutManager(manager);
        address=controller.GetAddressInList();
        latlang=controller.GetLatLngInList();
        adapter=new ShowLocationsAdapter(mContext,address,latlang,this,savedInstanceState);
        list.setAdapter(adapter);
        list.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        HandleSearch();
    }

    private void HandleSearch() {
        searchView=root.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;

            }
        });
    }

    @Override
    public void onLocationClick(int position) {
        bottomSheetFragment.dismiss();
        Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
    }
}
