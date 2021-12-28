package com.example.loacationsaver.View.MainAcitivtyView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import com.example.loacationsaver.R;
import com.example.loacationsaver.ListFragment;
import com.example.loacationsaver.controller.MainActivityController.MainActivityController;
import com.example.loacationsaver.model.db.DatabaseModel;
import com.example.loacationsaver.model.adapters.LocationDBAdapter;
import com.example.loacationsaver.model.locations.LocationModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewImplementor implements MainActivityView {

    View root;
    ExtendedFloatingActionButton btn_view_location,btn_save_location;
    FloatingActionButton refresh;
    MainActivityController controller;
    Toolbar toolbar;
    ImageButton menu_btn,widget_btn;

    ArrayAdapter<String> adapter;

    public ViewImplementor(Context context, ViewGroup container) {
        root = LayoutInflater.from(context).inflate(R.layout.activity_main, container);
        //List<String> list;
        DatabaseModel model = new DatabaseModel(LocationDBAdapter.getLocationInstance(context));
        LocationModel locationModel = new LocationModel(root.getContext());
        controller = new MainActivityController(model, this, locationModel);
    }

    @Override
    public void initView() {
        UI();
        controller.onViewLoaded();
        menu_btn =  root.findViewById(R.id.menu_btn);
        widget_btn= root.findViewById(R.id.widget);
        refresh =  root.findViewById(R.id.recenter);
        btn_save_location= root.findViewById(R.id.save_location);
        btn_view_location= root.findViewById(R.id.view_location);
        //Handle Click Events
        menu_btn.setOnClickListener(v -> CreatePopUpMenu());
        refresh.setOnClickListener(v -> controller.GetLocationUpdates());
        widget_btn.setOnClickListener(v -> ShowDialogue("The Feature is coming soon"));
        btn_save_location.setOnClickListener(v -> controller.OnClickSaveLocation(controller.GetLatLng()));
        btn_view_location.setOnClickListener(v -> {
            controller.GetAddress(controller.GetLatLng());
            ShowBottomSheetDialogue(root.getContext());
        });
    }

    private void ShowBottomSheetDialogue(Context context) {
        ListFragment bottomSheet=new ListFragment();
        bottomSheet.show(((FragmentActivity) context).getSupportFragmentManager(), bottomSheet.getTag());
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
        toolbar = root.findViewById(R.id.toolbar);
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
    public void ShareLocation() {
    }

    @Override
    public void AboutUs() {
    }

    @Override
    public void CreatePopUpMenu() {
        PopupMenu popup = new PopupMenu(root.getContext(), menu_btn);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_items_custom, popup.getMenu());
        popup.show();
    }
}
