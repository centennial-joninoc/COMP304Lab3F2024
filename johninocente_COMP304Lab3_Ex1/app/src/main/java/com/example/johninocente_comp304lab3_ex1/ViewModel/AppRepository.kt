package com.example.johninocente_comp304lab3_ex1.ViewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.johninocente_comp304lab3_ex1.Data.RetrofitClass
//import com.example.johninocente_comp304lab3_ex1.MainActivity.Companion.weatherDataList
import com.example.johninocente_comp304lab3_ex1.RoomDB.City
import com.example.johninocente_comp304lab3_ex1.Model.Weather
import com.example.johninocente_comp304lab3_ex1.RoomDB.CityDAO


private const val WEATHER_API_KEY = "26660e167276b6950d94f580ab39e6c4"
private const val WEATHER_API_METRIC = "metric"

class AppRepository(private val cityDao: CityDAO) {

    private val apiService = RetrofitClass.api
    private val weatherApiService = RetrofitClass.weatherApi

    suspend fun getCities(query: String): List<String>{
        return apiService.getCities(query)
    }

    suspend fun getWeather(city: String): Weather{
        return  weatherApiService.getWeather(city, WEATHER_API_KEY, WEATHER_API_METRIC)
    }

    suspend fun getCitiesFromDB(): List<City>{
        return cityDao.getAllCities()
    }

    suspend fun insertCity(c: City){
        cityDao.insertCityToDB(c)
    }

    suspend fun deleteCity(c: City){
        cityDao.deleteCity(c)
    }

    suspend fun searchForCityInDB(term:String) : List<City>{
        return cityDao.getCityNamed(term)
    }

    suspend fun update(newCity: City){
        return cityDao.updateCity(newCity)
    }

    suspend fun fetchWeatherList(cityList: List<City>) : List<Weather>
    {
        var list = mutableStateListOf<Weather>()

        cityList.forEach { city ->
            val weatherCity = getWeather(city.name)
            list.add(weatherCity)
        }
        return list
    }
}