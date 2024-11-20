package com.example.johninocente_comp304lab3_ex1.View

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.johninocente_comp304lab3_ex1.Navigation.NavDestinations
import com.example.johninocente_comp304lab3_ex1.RoomDB.City
import com.example.johninocente_comp304lab3_ex1.ViewModel.CityWeatherViewModel

@Composable
fun SearchScreen(navController: NavController, viewModel: CityWeatherViewModel)
{
    var searchedCityText by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBarUI(navController, searchedCityText, { searchedCityText = it }) },
    ) { innerPadding ->
        if (searchedCityText.length >= 3)
            ContentUI(innerPaddingValues = innerPadding, searchedCityText, navController = navController, viewModel = viewModel)
    }
}

@Composable
private fun TopAppBarUI(navController: NavController, searchText: String, onSearchQueryChanged: (String) -> Unit)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    )
    {
        IconButton(
            modifier = Modifier
                .padding(end = 10.dp)
                .align(Alignment.CenterVertically)
                .offset(x = 7.5.dp),
            onClick = { GoBackToHomeScreen(navController = navController) }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Search City",
            )
        }
        OutlinedTextField(
            value = searchText,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier
                .fillMaxWidth(0.9f),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(24.dp)
                )
            },
            placeholder = { Text(text = "Enter city name (at least 3 letters)") },
            singleLine = true,
            label = { Text(text = "Search") },
            isError = searchText.length < 3,
        )
    }
}

private fun GoBackToHomeScreen(navController: NavController)
{
    navController.navigateUp()
}

@Composable
private fun ContentUI(innerPaddingValues: PaddingValues, cityText: String, navController: NavController, viewModel: CityWeatherViewModel) {
    viewModel.getCities(cityText)
    val cityList = viewModel.cities

    var selectedIndex = -1

    LazyColumn(
        modifier = Modifier
            .padding(innerPaddingValues)
            .fillMaxSize(),
        contentPadding = PaddingValues(15.dp)
    ) {
        items(cityList.size) { id ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp).selectable(
//                        selected = id == selectedIndex,
                        selected = id == selectedIndex,
                        onClick = {
                            val city = CreateCityForDB(cityList[id])
                            viewModel.insertToDB(city)
                            navController.popBackStack()

                        }
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = cityList.get(id))
            }

        }
    }
}

private fun CreateCityForDB(name: String) : City
{
    var cityData = name.split(", ")
    val city = City(Math.random().toInt(), cityData[0], cityData[1], cityData[2] )
    return city
}
