package com.example.johninocente_comp304lab3_ex1.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ViewModelFactory(private val repository: AppRepository):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityWeatherViewModel::class.java)){
            return CityWeatherViewModel(repository) as T
        }else {
            throw IllegalArgumentException("Error")
        }
    }
}