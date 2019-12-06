package com.example.mapapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback {

    public static final String extra_message = "message" ;
    public static final String code = "valuesArray";
    private GoogleMap map;
    private Fragment historyfragment = new HistoryFragment();
    private  ArrayList <String> data;
    private ArrayList <LatLng> coords;
    private Button button;
    private int Latitude = 181;
    private int Longitude = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        BottomNavigationView bottomNav =findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        data = new ArrayList<>();

        coordGenerator();

       button = findViewById(R.id.RefreshButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                coordGenerator();
                map.clear();
                onMapReady(map);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(code, data);
                    historyfragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            historyfragment).hide(historyfragment).commit();
                    switch (menuItem.getItemId())
                    {
                        case R.id.nav_map:
                            data = new ArrayList<>();
                            button.setVisibility(View.VISIBLE);
                            break;
                        case R.id.nav_history:
                            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.detach(historyfragment);
                            ft.attach(historyfragment);
                            button.setVisibility(View.GONE);
                            ft.show(historyfragment);
                            ft.commit();
                            break;
                    }

                    return true;
                }
            };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        for (int i = 0; i<15;i++)
        {
            map.addMarker(new MarkerOptions().position(coords.get(i)).title(getString(R.string.weather_information)));
        }
        map.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        data.add(marker.getPosition().toString());
        Intent intent = new Intent(this, WeatherInfoActivity.class);
        String message =marker.getPosition().toString();
        intent.putExtra(extra_message, message);
        startActivity(intent);
    }

    public void coordGenerator()
    {
        ArrayList <LatLng> temp = new ArrayList<>();

        for(int i =0 ; i<15;i++)
        {
            LatLng latlng = new LatLng(Math.random()* Latitude,Math.random()* Longitude);
            temp.add(latlng);
        }
        coords = temp;
    }

}
