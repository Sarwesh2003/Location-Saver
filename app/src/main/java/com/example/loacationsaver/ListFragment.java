package com.example.loacationsaver;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loacationsaver.View.LocationListView.LocationListView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ListFragment extends BottomSheetDialogFragment {
    LocationListView view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=new LocationListView(getContext(),null,savedInstanceState,this);
        view.InitiallizeUI();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view.getRootView();
    }
}