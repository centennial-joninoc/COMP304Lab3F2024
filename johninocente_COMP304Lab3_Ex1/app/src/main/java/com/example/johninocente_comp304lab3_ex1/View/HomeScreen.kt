package com.example.johninocente_comp304lab3_ex1.View

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.johninocente_comp304lab3_ex1.MainActivity.Companion.deletedCard
import com.example.johninocente_comp304lab3_ex1.Navigation.NavDestinations
import com.example.johninocente_comp304lab3_ex1.ViewModel.CityWeatherViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    cityWeatherViewModel: CityWeatherViewModel
) {
    cityWeatherViewModel.getDBCities()
    var cityDataList = cityWeatherViewModel.dbcities

    if (cityDataList.isEmpty()) {
        Text(text = "Loading...")
    } else {

        //make this a suspend function, make a mutable array

        cityWeatherViewModel.getWeatherList(cityDataList)
    }

    MainUI(
        navController = navController,
        cityWeatherViewModel
    )
}

@Composable
private fun MainUI(
    navController: NavController,
    cityWeatherViewModel: CityWeatherViewModel,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBarUI(navController = navController) }
    ) { innerPadding ->
        ContentUI(navController, innerPadding, cityWeatherViewModel)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopAppBarUI(navController: NavController) {
    TopAppBar(
        title = { Text(text = "Favorite Cities 🌦️") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        actions = {
            IconButton(
                onClick = { searchBarUI(navController = navController) }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search City",
                )
            }
        }
    )
}

//TODO: Custom City
@Composable
private fun ContentUI(
    navController: NavController,
    innerPaddingValues: PaddingValues,
    cityWeatherViewModel: CityWeatherViewModel
) {
    WeatherList(navController, cityWeatherViewModel, innerPaddingValues,
        onDeleteCard = {
            deletedCard.value = true
    })


    if (deletedCard.value)
    {
        AlertDialog(
            text = {
                Text(text = "City Deleted")
            },
            onDismissRequest = {
                cityWeatherViewModel.getDBCities()
                deletedCard.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        cityWeatherViewModel.getDBCities()
                        deletedCard.value = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

private fun searchBarUI(navController: NavController) {
    navController.navigate(NavDestinations.SearchScreen.createRoute("Toronto"))
}

