package com.example.lab13.retrofit

import com.example.lab13.model.ModelWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofitAPI {

    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(@Query("lat") lat:String,
    @Query("lon") lon:String,
    @Query("APPID") app_id:String): Call<ModelWeather>

}