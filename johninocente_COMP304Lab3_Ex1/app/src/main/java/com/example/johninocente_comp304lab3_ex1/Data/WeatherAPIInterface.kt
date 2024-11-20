package com.example.johninocente_comp304lab3_ex1.Data

import com.example.johninocente_comp304lab3_ex1.Model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPIInterface {

    @GET("AutoCompleteCity")
    suspend fun getCities(@Query("q") q: String): List<String>

    @GET("weather?")
    suspend fun getWeather(@Query("q") q: String,
                           @Query("appid") key: String,
                           @Query("units") units: String): Weather
}