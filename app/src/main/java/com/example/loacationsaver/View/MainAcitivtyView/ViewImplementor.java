package com.example.loacationsaver.View.MainAcitivtyView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.loacationsaver.R;
import com.example.loacationsaver.controller.MainActivityController.ActivityController;
import com.example.loacationsaver.model.db.DatabaseModel;
import com.example.loacationsaver.model.db.adapters.LocationDBAdapter;
import com.example.loacationsaver.model.locations.LocationModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewImplementor implements MainActivityView {

    View root;
    FloatingActionButton refresh;
    ActivityController controller;
    Toolbar toolbar;
    ImageButton menu_btn,widget_btn;

    ArrayAdapter<String> adapter;

    public ViewImplementor(Context context, ViewGroup container) {
        root = LayoutInflater.from(context).inflate(R.layout.activity_main, container);
        //List<String> list;
        DatabaseModel model = new DatabaseModel(LocationDBAdapter.getLocationInstance(context));
        LocationModel locationModel = new LocationModel(root.getContext());
        controller = new ActivityController(model, this, locationModel);
    }

    @Override
    public void initView() {
        UI();
        controller.onViewLoaded();
        menu_btn = (ImageButton) root.findViewById(R.id.menu_btn);
        widget_btn=(ImageButton) root.findViewById(R.id.widget);
        refresh = (FloatingActionButton) root.findViewById(R.id.recenter);

        //Handle Click Events
        menu_btn.setOnClickListener(v -> CreatPopUpMenu());
        refresh.setOnClickListener(v -> controller.GetLocationUpdates());
        widget_btn.setOnClickListener(v -> {ShowDialogue("The Feature is coming soon");});
    }

    public void ShowDialogue(String msg) {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(root.getContext());
        builder.setTitle(msg);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void UI() {
        RelativeLayout rl = root.findViewById(R.id.layout_map);
        rl.setClipToOutline(true);
    }

    @Override
    public Toolbar getToolbar() {
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        return toolbar;
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
            adapter = new ArrayAdapter<>(root.getContext(), R.layout.listview, list);
            //show.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.d("Error From: ", "ShowAllLocations");
            this.ShowError(e.getMessage());
        }

    }

    @Override
    public void ShowError(String error) {
        Toast.makeText(root.getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowSuccess(String SuccessMsg) {
        Toast.makeText(root.getContext(), SuccessMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShareApp() {
    }

    @Override
    public void AboutUs() {
    }

    @Override
    public void CreatPopUpMenu() {
        ImageButton btn = (ImageButton) root.findViewById(R.id.menu_btn);
        PopupMenu popup = new PopupMenu(root.getContext(), btn);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_items_custom, popup.getMenu());
        popup.show();
    }
}
