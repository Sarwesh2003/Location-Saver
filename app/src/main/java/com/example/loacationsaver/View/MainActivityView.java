package com.example.loacationsaver.View;

import com.example.loacationsaver.controller.LocationControllerInterface;
import com.example.loacationsaver.model.LocationData;

import java.util.List;

public interface MainActivityView extends ViewInterface{
    void UpdateView(List<String> list);
    void BindDataToView();
    void ShowAllLocations(List<String> list);
    void ShowError(String error);
    void ShowSuccess(String SuccessMsg);
}
