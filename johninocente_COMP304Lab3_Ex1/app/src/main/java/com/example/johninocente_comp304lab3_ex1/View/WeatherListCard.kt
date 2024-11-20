package com.example.johninocente_comp304lab3_ex1.View

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.johninocente_comp304lab3_ex1.Model.Weather
import com.example.johninocente_comp304lab3_ex1.Navigation.NavDestinations
import com.example.johninocente_comp304lab3_ex1.R
import com.example.johninocente_comp304lab3_ex1.RoomDB.City
import com.example.johninocente_comp304lab3_ex1.ViewModel.CityWeatherViewModel
import kotlin.math.round


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherListCard(
    navController: NavController,
    cityWeatherViewModel: CityWeatherViewModel,
    weather: Weather?,
    onDelete: ()->Unit
)
{

    var showAlert by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    if (weather != null)
    {
        ElevatedCard(
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp),

            modifier = Modifier .padding(5.dp) .fillMaxWidth().wrapContentHeight(align = Alignment.Top)
                .combinedClickable (
                    onLongClick = {
                        Log.d(Log.INFO.toString(), weather.name)
                        showAlert = true
                      },
                    onClick = { GoToWeatherScreenUI(navController, cityWeatherViewModel, weather) }
                )
        ){
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .size(width = 500.dp, height = 120.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            )
            {
                BackgroundImage(weather)
                WeatherDetails(weather)
            }

        }
        if (showAlert)
        {
            ShowDeleteDialog(
                title = "Delete City",
                details = "Do you want to remove the city?",
                confirmMsg = "Confirm",
                dismissMsg = "Cancel",
                onConfirm = {
                    showAlert = false
                    isSuccess = DeleteCity(cityWeatherViewModel, weather)
                },
                onDismiss = {
                    showAlert = false
                }
            )
        }

        if (isSuccess)
        {
            onDelete()
        }
    }
}

@Composable
private fun BackgroundImage(weatherData: Weather?)
{
    val backGroundImage = getBackgroundImage(weatherData)

    Image(
        painter = painterResource(id = backGroundImage),
        contentDescription = null,
        modifier = Modifier
            .scale(scaleX = 1.1f, scaleY = 1.1f)
            .wrapContentSize(unbounded = true, align = Alignment.Center)
            .fillMaxSize()
            .blur(
                radiusX = 2.dp,
                radiusY = 2.dp,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
    )
}

fun DeleteCity(cityWeatherViewModel: CityWeatherViewModel, weather: Weather?) : Boolean
{
    var isSuccess = false
    var cities = cityWeatherViewModel.getDBCities()
    //val name = weather?.name


    val cityName = weather?.name?.lowercase()
    val country = weather?.sys?.country?.lowercase()

    val city :City? = cities.find { it.name.lowercase() == cityName}

    if (city != null) {
        cityWeatherViewModel.deleteOneCity(city)
        isSuccess = true
    }
    return isSuccess
}

@Composable
fun ShowDeleteDialog(title: String, details: String, confirmMsg: String, dismissMsg: String, onConfirm: ()->Unit, onDismiss: ()->Unit) {
    AlertDialog(
        onDismissRequest = {  },
        title = { Text(text = title) },
        text = { Text(text = details) },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                }
            ) {
                Text(text = confirmMsg)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = dismissMsg)
            }
        }
    )
}

@Composable
fun WeatherDetails(wObj: Weather?)
{
    if (wObj == null)
    {
        return
    }

    Column(
        modifier = Modifier
            .padding(16.dp).fillMaxWidth()
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text( text = wObj.name, fontSize = 24.sp, fontWeight = FontWeight.Bold )
            Text( text = round(wObj.main.temp).toString() + "°C", fontSize = 30.sp, fontWeight = FontWeight.Bold )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            wObj.weather.get(0).let {
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${it.icon}@2x.png",
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }
            Row {
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = wObj.weather.get(0).description.uppercase(),
                    fontSize = 18.sp, fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(90.dp))
                Text(
                    text = "High:" + round(wObj.main.temp_max).toString() + "°C Low:"+ round(wObj.main.temp_min).toString() + "°C",
                    fontSize = 14.sp, fontWeight = FontWeight.Light
                )
            }
        }
    }
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

fun GoToWeatherScreenUI(navController: NavController, cityWeatherViewModel: CityWeatherViewModel, weather: Weather?)
{
    if (weather == null)
    {
        return
    }

    cityWeatherViewModel.getWeather(weather.name)

    navController.navigate(NavDestinations.WeatherScreen.route)
}



//@Composable
//fun WeatherListCardSample(
//    name: String,
//    temp: Double,
//    description: String,
//    temp_max: Double,
//    temp_min: Double
//) {
//    ElevatedCard(
//        shape = RoundedCornerShape(15.dp),
//        elevation = CardDefaults.elevatedCardElevation(10.dp),
//        modifier = Modifier .padding(5.dp) .fillMaxWidth().wrapContentHeight(align = Alignment.Top),
//    ){
//
//        Box(
//
//            modifier = Modifier
//                .padding(10.dp)
//                .wrapContentHeight(align = Alignment.CenterVertically)
//                .size(width = 500.dp, height = 120.dp)
//                .fillMaxWidth(),
//            contentAlignment = Alignment.TopStart
//        )
//        {
//            BackgroundImage()
//            WeatherDetails()
//        }
//
//    }
//}


//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun WeatherListCardPreview()
//{
//    Column()
//    {
//        WeatherListCardSample("Toronto", temp = 4.53, description = "Cloudy", temp_max = 10.21, temp_min = 1.01)
//        WeatherListCardSample("Toronto", temp = 4.53, description = "Cloudy", temp_max = 10.21, temp_min = 1.01)
//    }
//
//}

