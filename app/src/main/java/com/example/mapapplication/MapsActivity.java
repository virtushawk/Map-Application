package com.example.mapapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MapsActivity extends FragmentActivity {

    public static final String extra_message = "message" ;
    public static final String codes = "valuesArrays";
    private Fragment historyfragment = new HistoryFragment();
    private Fragment mapfragment = new MapFragment();
    private  ArrayList <String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        data = new ArrayList<>();
        saveArrayList(data,codes);
        BottomNavigationView bottomNav =findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,historyfragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,mapfragment).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId())
                    {
                        case R.id.nav_map:
                            getSupportFragmentManager().beginTransaction().
                                    hide(historyfragment).show(mapfragment).commit();
                            break;
                        case R.id.nav_history:
                            getSupportFragmentManager().beginTransaction().
                                    detach(historyfragment).attach(historyfragment).hide(mapfragment)
                                    .show(historyfragment).commit();
                            break;
                    }

                    return true;
                }
            };

    public void saveArrayList(ArrayList<String> coords, String codes){
        Context context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(coords);
        editor.putString(codes, json);
        editor.apply();
    }

    public ArrayList<String> getArrayList(String key){
       Context context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
