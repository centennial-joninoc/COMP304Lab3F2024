package com.example.johninocente_comp304lab3_ex1.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.johninocente_comp304lab3_ex1.View.HomeScreen
import com.example.johninocente_comp304lab3_ex1.View.SearchScreen
import com.example.johninocente_comp304lab3_ex1.View.WeatherScreen
import com.example.johninocente_comp304lab3_ex1.ViewModel.CityWeatherViewModel


@Composable
fun NavGraph(navController: NavHostController, cityWeatherViewModel: CityWeatherViewModel){

    NavHost(navController = navController, startDestination = NavDestinations.WeatherScreen.route)
    {
        composable(NavDestinations.HomeScreen.route) {
            HomeScreen(navController, cityWeatherViewModel)
        }

        composable (NavDestinations.WeatherScreen.route)
        {
            WeatherScreen(navController, cityWeatherViewModel)
        }

        composable (NavDestinations.SearchScreen.route.plus("/{value}"),
            arguments = listOf(navArgument("value"){
                type = NavType.StringType
            })
        )
        {
            SearchScreen(navController, cityWeatherViewModel)
        }
    }
}