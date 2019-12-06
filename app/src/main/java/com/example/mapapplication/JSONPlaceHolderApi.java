package com.example.mapapplication;

import com.example.mapapplication.WeatherDAO.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {


    @GET("/data/2.5/weather/")
    Call<Example> getWeather(@Query("lat") Double lat, @Query("lon") Double lon,
                             @Query("units") String units, @Query("appid") String api);

}
