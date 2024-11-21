package com.example.johninocente_comp304lab3_ex1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.johninocente_comp304lab3_ex1.Model.Weather
import com.example.johninocente_comp304lab3_ex1.Navigation.NavGraph
import com.example.johninocente_comp304lab3_ex1.RoomDB.CityDB
import com.example.johninocente_comp304lab3_ex1.ViewModel.AppRepository
import com.example.johninocente_comp304lab3_ex1.ViewModel.ViewModelFactory
import com.example.johninocente_comp304lab3_ex1.ViewModel.CityWeatherViewModel
import com.example.johninocente_comp304lab3_ex1.ui.theme.Johninocente_COMP304Lab3_Ex1Theme


class MainActivity : ComponentActivity()
{
    companion object
    {
        var weatherDataList =  mutableStateListOf<Weather>()
        var deletedCard = mutableStateOf(false)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val database = CityDB.getInstance(applicationContext)
        val repository = AppRepository(database.getCityDao())
        val viewModelFactory = ViewModelFactory(repository)
        val cityWeatherViewModel = ViewModelProvider(this, viewModelFactory)[CityWeatherViewModel::class.java]

        setContent {
            Johninocente_COMP304Lab3_Ex1Theme {
                val navController = rememberNavController()
                NavGraph(navController, cityWeatherViewModel)
            }
        }
    }

}

