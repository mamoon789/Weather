package com.example.weather.presentation.weather_list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.common.Resource
import com.example.weather.domain.model.Weather
import com.example.weather.domain.usecase.get_weatherInfo.GetWeatherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase
) : ViewModel()
{
    val location = savedStateHandle.getStateFlow("location", "")
    val weatherList = savedStateHandle.getStateFlow("weatherList", emptyList<Weather>())
    val errorMessage = savedStateHandle.getStateFlow("errorMessage", "")
    val isLoading = savedStateHandle.getStateFlow("isLoading", false)

    fun updateLocation(location: String)
    {
        savedStateHandle["location"] = location
    }

    fun resetErrorMessage()
    {
        savedStateHandle["errorMessage"] = ""
    }

    fun getWeatherInfo(location: String)
    {
        getWeatherInfoUseCase(location).onEach { resource ->
            when (resource)
            {
                is Resource.Success ->
                {
                    weatherList.value.toMutableList().apply {
                        add(resource.data!!)
                        savedStateHandle["weatherList"] = this
                    }
                    savedStateHandle["errorMessage"] = ""
                    savedStateHandle["isLoading"] = false
                }

                is Resource.Error ->
                {
                    savedStateHandle["errorMessage"] = resource.message
                    savedStateHandle["isLoading"] = false
                }

                is Resource.Loading ->
                {
                    savedStateHandle["errorMessage"] = ""
                    savedStateHandle["isLoading"] = true
                }
            }
        }.launchIn(viewModelScope)
    }
}