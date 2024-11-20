package com.example.johninocente_comp304lab3_ex1.View

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.johninocente_comp304lab3_ex1.MainActivity.Companion.weatherDataList
import com.example.johninocente_comp304lab3_ex1.ViewModel.CityWeatherViewModel

@Composable
fun WeatherList(navController: NavController, cityWeatherViewModel: CityWeatherViewModel, innerPaddingValues: PaddingValues, onDeleteCard: ()->Unit) {
     weatherDataList = cityWeatherViewModel.weatherVMList.toMutableStateList()

    LazyColumn(
        modifier = Modifier.padding(innerPaddingValues)
    ) {
        items(weatherDataList) { weatherInfo ->
            WeatherListCard(navController, cityWeatherViewModel, weatherInfo, onDelete = onDeleteCard)
        }
    }
}

@Composable
@Preview
fun WeatherListPreview() {

}
