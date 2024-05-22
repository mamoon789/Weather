package com.example.weather.presentation.weather_list

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weather.domain.model.Weather

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherListScreen(viewModel: WeatherListViewModel)
{
    val location = viewModel.location.collectAsState()
    val weatherList = viewModel.weatherList.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = "Enter Location",
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value = location.value,
            onValueChange = { viewModel.updateLocation(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            placeholder = { Text(text = "E.g: Lahore or 31.5204,74.3587") },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            ),
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            onClick = { viewModel.getWeatherInfo(location.value) }) {
            Text(
                text = "Search",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = {
                weatherList.value.map {
                    item {
                        Card(
                            shape = RoundedCornerShape(3.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                            ),
                        ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column {
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 20.dp, top = 10.dp),
                                        text = "${it.current.temp_c}\u2103 - ${it.current.temp_f}\u2109",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = TextUnit(24f, TextUnitType.Sp),
                                        minLines = 1,
                                        maxLines = 1,
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 20.dp, top = 0.dp),
                                        text = it.current.condition.text,
                                        fontSize = TextUnit(16f, TextUnitType.Sp),
                                        minLines = 1,
                                        maxLines = 1
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 20.dp, top = 10.dp),
                                        text = it.location.name,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = TextUnit(16f, TextUnitType.Sp),
                                        minLines = 1,
                                        maxLines = 1
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 20.dp),
                                        text = "Feels like ${it.current.feelslike_c}\u2103 - ${it.current.feelslike_f}\u2109",
                                        fontSize = TextUnit(14f, TextUnitType.Sp),
                                        minLines = 1,
                                        maxLines = 1,
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 20.dp, bottom = 10.dp),
                                        text = "Humidity ${it.current.humidity}% - Wind ${it.current.wind_kph} km/h",
                                        fontSize = TextUnit(14f, TextUnitType.Sp),
                                        minLines = 1,
                                        maxLines = 1
                                    )
                                }
                                AsyncImage(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .align(Alignment.Top),
                                    contentScale = ContentScale.FillWidth,
                                    model = "http:${it.current.condition.icon}",
                                    alignment = Alignment.TopStart,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }
            })

        if(isLoading.value){
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
        }

        if(errorMessage.value != ""){
            AlertDialog(onDismissRequest = {viewModel.resetErrorMessage()},
                title = { Text("Error") },
                text = { Text(errorMessage.value) },
                confirmButton = {
                    TextButton(onClick = {viewModel.resetErrorMessage()}) {
                        Text("Ok")
                    }
                })
        }
    }
}