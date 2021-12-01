package com.example.loacationsaver.View.MainAcitivtyView;

import androidx.appcompat.widget.Toolbar;

import java.util.List;

public interface MainActivityView extends ViewInterface {
    void UpdateView(List<String> list);
    void BindDataToView();
    void ShowAllLocations(List<String> list);
    void ShowError(String error);
    void ShowSuccess(String SuccessMsg);
    void UI();
    void CreatPopUpMenu();
    void ShareApp();
    void AboutUs();
    Toolbar getToolbar();
}
