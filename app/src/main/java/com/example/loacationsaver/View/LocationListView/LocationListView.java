package com.example.loacationsaver.View.LocationListView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loacationsaver.ListFragment;
import com.example.loacationsaver.R;
import com.example.loacationsaver.controller.LocationList.ListController;
import com.example.loacationsaver.model.adapters.ShowLocationsAdapter;
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

    public LocationListView(Context mContext, ViewGroup container, Bundle savedInstanceState, ListFragment bottomSheet) {
        this.mContext = mContext;
        this.root= LayoutInflater.from(mContext).inflate(R.layout.fragment_bottom_sheet, container);
        this.savedInstanceState=savedInstanceState;
        this.bottomSheetFragment=bottomSheet;
        controller=new ListController(mContext);
    }

    public void InitiallizeUI(){
        RecylerView();
        if(adapter.getItemCount()<=0){
            Toast.makeText(mContext,"No Locations Found",Toast.LENGTH_SHORT).show();
        }
        HandleSearch();

    }

    private void RecylerView(){
        list=root.findViewById(R.id.locationList);
        LinearLayoutManager manager=new LinearLayoutManager(mContext);
        list.setLayoutManager(manager);
        GetData();
        adapter=new ShowLocationsAdapter(mContext,address,latlang,this,savedInstanceState);
        list.setAdapter(adapter);
        list.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
    }

    private void GetData(){
        address=controller.GetAddressInList();
        latlang=controller.GetLatLngInList();
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
//        bottomSheetFragment.dismiss();
    }
    public View getRootView(){
        return root;
    }
}
