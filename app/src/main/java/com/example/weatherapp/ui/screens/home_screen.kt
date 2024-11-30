package com.example.weatherapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.WeatherViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    LaunchedEffect(Unit) {
        //   viewModel.fetchData("9d24d098c5d07f98c2ac3ab3aec96f47", "india")
        viewModel.fetchData("f637291a31c556c510dc54885576da32", "india")
    }
    Scaffold(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        DataSection(viewModel)

    }


}

@Composable
fun BackgroundImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
        )
    }
}

@Composable
fun DataSection(viewModel: WeatherViewModel) {
    val weatherData = viewModel.weatherData.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value
    var textState = remember { mutableStateOf("") }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Fetching....",
                style = TextStyle(color = Color.White, fontSize = 30.sp)
            )
        }
        return
    }

    if (errorMessage != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = errorMessage,
                style = TextStyle(color = Color.White, fontSize = 30.sp)
            )
        }
        return
    }

    if (weatherData?.current == null || weatherData.location == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Data Available",
                style = TextStyle(color = Color.White, fontSize = 20.sp)
            )
        }
        return
   }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        OutlinedTextField(


            value = textState.value,
            onValueChange = { newValue ->
                textState.value = newValue
            },
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp,end=15.dp),
            placeholder = { Text(text = "Enter Country Name ") },
            textStyle = TextStyle(color = Color.White, fontSize = 25.sp),

            trailingIcon = {
                IconButton(

                    onClick = {
                        if (textState.value.isNotEmpty()) {
                            viewModel.fetchData("f637291a31c556c510dc54885576da32", textState.value.trim())
                        }                    },

                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search", modifier = Modifier.size(40.dp), tint = Color.White
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(90.dp))

        Text(
            text = weatherData.location.name ?: "Unknown Location",
            style = TextStyle(color = Color.White, fontSize = 30.sp)
        )

        Text(
            text = "${weatherData.current.temperature ?: "--"}°C",
            style = TextStyle(color = Color.White, fontSize = 70.sp)
        )

        Text(
            text = weatherData.current.weather_descriptions?.joinToString(", ") ?: "No Description",
            style = TextStyle(color = Color.White, fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Humidity: ${weatherData.current.humidity ?: "--"}%",
            style = TextStyle(color = Color.White, fontSize = 18.sp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Pressure: ${weatherData.current.pressure ?: "--"} hPa",
            style = TextStyle(color = Color.White, fontSize = 18.sp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Wind: ${weatherData.current.wind_speed ?: "--"} km/h, ${weatherData.current.wind_dir ?: "--"}",
            style = TextStyle(color = Color.White, fontSize = 18.sp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Location: ${weatherData.location.name ?: "--"}, ${weatherData.location.region ?: "--"}, ${weatherData.location.country ?: "--"}",
            style = TextStyle(color = Color.White, fontSize = 18.sp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Time: ${weatherData.location.localtime ?: "--"}",
            style = TextStyle(color = Color.White, fontSize = 18.sp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HomeScreen()
}