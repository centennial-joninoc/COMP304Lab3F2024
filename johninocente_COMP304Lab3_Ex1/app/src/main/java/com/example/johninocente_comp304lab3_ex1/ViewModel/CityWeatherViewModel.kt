package com.example.johninocente_comp304lab3_ex1.ViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.johninocente_comp304lab3_ex1.MainActivity.Companion.weatherDataList//
import com.example.johninocente_comp304lab3_ex1.Model.Weather
import com.example.johninocente_comp304lab3_ex1.RoomDB.City
import kotlinx.coroutines.launch

class CityWeatherViewModel(private val repository: AppRepository) : ViewModel() {

    var weatherVMList = mutableStateListOf<Weather>()
    private set

    var weather by mutableStateOf<Weather?>(null)
        private set

    var cities by mutableStateOf<List<String>>(emptyList())
        //var cities by mutableListOf<String>()
        private set

    var dbcities by mutableStateOf<List<City>>(emptyList())
        private set

    var city by mutableStateOf<City?>(null)

    fun getWeather(city: String) {
        viewModelScope.launch {
            try {
                var weatherObj = repository.getWeather(city)
                if (weatherObj != null) {
                    weather = weatherObj
                }
                // else making it loading
            } catch (e: Exception) {
                Log.d("error", e.toString())
            }
        }
    }

    fun getWeatherList(cityList: List<City>) {
        viewModelScope.launch {
            try {
                var weatherList = repository.fetchWeatherList(cityList)
                if (weatherList != null) {
                    weatherVMList = weatherList.toMutableStateList()

                }
                // else making it loading
            } catch (e: Exception) {
                Log.d("error", e.toString())
            }
        }
    }

    init {
        viewModelScope.launch {
            val fetchCities = repository.getCities("")
            cities = fetchCities

            val citiesFromDB = repository.getCitiesFromDB()
            dbcities = citiesFromDB
        }
    }

    fun getCities(userTerm: String): List<String> {
        viewModelScope.launch {
            val fetchCities = repository.getCities(userTerm)
            cities = fetchCities
        }
        return cities
    }

    fun getDBCities(): List<City> {
        viewModelScope.launch {
            val fetchCities = repository.getCitiesFromDB()
            dbcities = fetchCities
        }
        return dbcities
    }

    fun insertToDB(c: City) {
        viewModelScope.launch {
            repository.insertCity(c)
            val dbfetchCities = repository.getCitiesFromDB()
            dbcities = dbfetchCities
        }
    }

    fun update(newCity: City) {
        viewModelScope.launch {
            repository.update(newCity)
            val dbfetchCities = repository.getCitiesFromDB()
            dbcities = dbfetchCities
        }
    }

    fun deleteOneCity(c: City) {
        viewModelScope.launch {
            repository.deleteCity(c)
            val dbfetchCities = repository.getCitiesFromDB()
            dbcities = dbfetchCities
        }
    }

}