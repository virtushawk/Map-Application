package com.example.mapapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mapapplication.data.models.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherInfoActivity extends AppCompatActivity {


   private final String api = "d64683325e6954bb5f7ea1aa330447ee";
   private final String units="metric";
    TextView addressTxt, updatedAtTxt, statusTxt, tempTxt, tempMinTxt, tempMaxTxt;
    String[] coordString;
    private  Double lat;
    private  Double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MapsActivity.extra_message);
        assert message != null;
        String temp = message.substring(10,message.length()-1);
        coordString = temp.split(",");
       lat=Double.parseDouble(coordString[0]);
       lon=Double.parseDouble(coordString[0]);

        final TextView textView= findViewById(R.id.coords);
        textView.setText(message);

        addressTxt = findViewById(R.id.address);
        updatedAtTxt = findViewById(R.id.updatedAt);
        statusTxt = findViewById(R.id.status);
        tempTxt = findViewById(R.id.temp);
        tempMinTxt = findViewById(R.id.tempMin);
        tempMaxTxt = findViewById(R.id.tempMax);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        NetworkService.getInstance()
                .getJSONApi()
                .getWeather(lat, lon,units,api)
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(@NonNull Call<Example> call, @NonNull Response<Example> response) {
                        Example example = response.body();
                        assert example != null;
                        long updatedAt = example.getDt()*1000;
                        addressTxt.setText(getString(R.string.addressText,example.getName(),example.getSys().getCountry()));
                        updatedAtTxt.setText(String.format("Updated at: %s", new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt))));
                        statusTxt.setText(getString(R.string.tempStatusText,example.getWeather().get(0).getDescription().toUpperCase()));
                        tempTxt.setText(getString(R.string.tempText,example.getTemperature().getTemp().toString()));
                        tempMinTxt.setText(getString(R.string.tempMinText,example.getTemperature().getTempMin().toString()));
                        tempMaxTxt.setText(getString(R.string.tempMaxText,example.getTemperature().getTempMax().toString()));

                    }

                    @Override
                    public void onFailure(@NonNull Call<Example> call, @NonNull Throwable t) {

                        textView.append(getString(R.string.internet_error));
                        t.printStackTrace();
                    }
                });

    }


}


