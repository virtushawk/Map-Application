package com.example.mapapplication;

import android.view.View;

import java.util.ArrayList;

public class MapsActivityPresenter {

    private View view;
    private ArrayList <String> data;

    public MapsActivityPresenter(View view) {
        this.data = new ArrayList<>();
        this.view = view;
    }

    interface View{


    }
}
