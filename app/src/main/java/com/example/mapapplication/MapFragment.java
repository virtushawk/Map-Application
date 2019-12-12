package com.example.mapapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.mapapplication.MapsActivity.codes;
import static com.example.mapapplication.MapsActivity.extra_message;

public class MapFragment extends Fragment implements  GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private ArrayList<LatLng> coords;
    private GoogleMap map;
    private Button button;
    private  ArrayList <String> data;
    private int Latitude = 181;
    private int Longitude = 71;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        coordGenerator();
        data= new ArrayList<>();
        ((MapsActivity) Objects.requireNonNull(getActivity())).saveArrayList(data,codes);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapSecond);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        button = v.findViewById(R.id.RefreshButton);
        data= new ArrayList<>();
        ((MapsActivity) Objects.requireNonNull(getActivity())).saveArrayList(data,codes);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                coordGenerator();
                map.clear();
                onMapReady(map);
            }
        });

        return v;

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        data.add(marker.getPosition().toString());
        Intent intent = new Intent(Objects.requireNonNull(getActivity()).getBaseContext(),
                    WeatherInfoActivity.class);
        String message =marker.getPosition().toString();
        intent.putExtra(extra_message, message);
        startActivity(intent);
        ((MapsActivity)getActivity()).saveArrayList(data,codes);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        for (int i = 0; i<15;i++)
        {
            map.addMarker(new MarkerOptions().position(coords.get(i)).title(getString(R.string.weather_information)));
        }
        map.setOnInfoWindowClickListener(this);
    }


    private void coordGenerator()
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
