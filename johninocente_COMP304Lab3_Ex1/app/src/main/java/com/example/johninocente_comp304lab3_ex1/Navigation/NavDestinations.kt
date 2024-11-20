package com.example.johninocente_comp304lab3_ex1.Navigation

import com.example.johninocente_comp304lab3_ex1.Model.Weather

sealed class NavDestinations(var route: String) {

    object HomeScreen : NavDestinations("HomeScreen")
    object WeatherScreen : NavDestinations("WeatherScreen")
    object SearchScreen : NavDestinations("SearchScreen"){

        fun createRoute(valueToPass: String): String {
            return "SearchScreen/$valueToPass"
        }
    }

}