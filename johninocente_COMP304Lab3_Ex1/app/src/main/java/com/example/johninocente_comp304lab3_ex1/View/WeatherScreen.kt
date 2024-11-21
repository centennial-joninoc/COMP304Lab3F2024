package com.example.johninocente_comp304lab3_ex1.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.johninocente_comp304lab3_ex1.Model.Weather
import com.example.johninocente_comp304lab3_ex1.Navigation.NavDestinations
import com.example.johninocente_comp304lab3_ex1.R
import com.example.johninocente_comp304lab3_ex1.ViewModel.CityWeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.round


@Composable
fun WeatherScreen(navController: NavController, cityWeatherViewModel: CityWeatherViewModel){
    //Default City is in Toronto Canada ON
    if (cityWeatherViewModel.weather == null) {
        cityWeatherViewModel.getWeather("Toronto, ON, Canada")
    }

    MainUI(navController, cityWeatherViewModel)

}

@Composable
private fun MainUI(navController: NavController, cityWeatherViewModel : CityWeatherViewModel)
{
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBarUI(navController = navController) }
    ) { innerPadding ->
        ContentUI(innerPadding, cityWeatherViewModel)
    }
}

@Composable
private fun ContentUI(innerPaddingValues: PaddingValues, cityWeatherViewModel : CityWeatherViewModel)
{
    var wo = cityWeatherViewModel.weather

    Box(
        modifier = Modifier
            .wrapContentHeight(align = Alignment.CenterVertically)
            .fillMaxSize()
    )
    {
            BackgroundImage(wo)
    }
    Column(
        Modifier.padding(innerPaddingValues).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center) {
        if (wo != null) {
            if (wo.weather?.size != 0){

                Text( text = wo.name,
                    fontSize = 40.sp,)
                Text(
                    fontSize = 30.sp,
                    //text = wo.main?.temp.toString(),
                    text = round(wo.main.temp).toString() + "°C"
                )
                Text(
                    fontSize = 30.sp,
                    text = "Feels Like " + round(wo.main.feels_like).toString() + "°C"
                )

                Spacer(Modifier.fillMaxHeight(0.03f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                ) {
                    WeatherScreenCard(
                        modifier = Modifier.weight(1f),
                        weatherLabel = "Wind Speed",
                        weatherValue = wo.wind.speed.toString(),
                        weatherUnit = "km/H",
                        iconId = R.drawable.wind,
                    )
                    WeatherScreenCard(
                        modifier = Modifier.weight(1f),
                        weatherLabel = "Sunrise",
                        weatherValue = formatDate(wo.sys.sunrise),
                        weatherUnit = "AM",
                        iconId = R.drawable.sunrise,
                    )
                    WeatherScreenCard(
                        modifier = Modifier.weight(1f),
                        weatherLabel = "Sunset",
                        weatherValue = formatDate(wo.sys.sunset),
                        weatherUnit = "PM",
                        iconId = R.drawable.sunset,
                    )
                }
                Spacer(Modifier.fillMaxHeight(0.2f))
                wo.weather?.get(0)?.let {
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${it.icon}@2x.png",
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(0.4f)
                    )
                }
                Spacer(Modifier.fillMaxHeight(0.2f))
                wo.weather?.get(0)?.let {
                    Text(
                        fontSize = 30.sp,
                        text = it.main
                    )
                }
                Spacer(Modifier.fillMaxHeight(0.1f))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopAppBarUI(navController: NavController)
{
    TopAppBar(
        title = { Text(text = "Weather Screen 🌦️") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        actions = {
            IconButton(
                onClick = { GoBackToHomeScreen(navController) }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Fave City",
                )
            }
        }
    )
}

private fun GoBackToHomeScreen(navController: NavController)
{
    navController.navigate(NavDestinations.HomeScreen.route)
}

@Composable
private fun BackgroundImage(weatherData: Weather?)
{
    val backGroundImage = getBackgroundImage(weatherData)

    Image(
        painter = painterResource(id = backGroundImage),
        contentDescription = null,
        modifier = Modifier
            .scale(scaleX = 1.3f, scaleY = 1.3f)
            .wrapContentSize(align = Alignment.Center)
            .fillMaxSize()
//            .blur(
//                radiusX = 2.dp,
//                radiusY = 2.dp,
//                edgeTreatment = BlurredEdgeTreatment.Unbounded
//            )
    )
}

private fun getBackgroundImage(wObj: Weather?): Int
{
    if (wObj != null) {
        if (wObj.weather.get(0).id == 800 && wObj.weather.get(0).icon.contains("d", ignoreCase = true))
        {
            return R.drawable.sunny_bg
        }
        else if (wObj.weather.get(0).id == 800 && wObj.weather.get(0).icon.contains("n", ignoreCase = true))
        {
            return R.drawable.night_bg
        }
        else if (wObj.weather.get(0).id in 200..232)
        {
            return R.drawable.rainy_bg
        }
        else if (wObj.weather.get(0).id in 300..321)
        {
            return R.drawable.rainy_bg
        }
        else if (wObj.weather.get(0).id in 500..531)
        {
            return R.drawable.rainy_bg
        }
        else if (wObj.weather.get(0).id in 600..622)
        {
            return R.drawable.snow_bg
        }
        else if (wObj.weather.get(0).id in 701..781)
        {
            return R.drawable.haze_bg
        }
    }
    return R.drawable.sunny_bg
}

fun formatDate(timeInMillis: Long): String
{
    val date = Date(timeInMillis)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}


//@Composable
//fun WeatherScreenSample(){
//
//}
//
//@Composable
//@Preview
//fun WeatherScreenPreview() {
//    WeatherScreenSample()
//}
