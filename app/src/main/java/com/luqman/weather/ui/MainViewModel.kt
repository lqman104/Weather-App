package com.luqman.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.WeatherDataSource
import com.luqman.weather.data.repository.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherDataSource: WeatherDataSource
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<Weather>>>(Resource.Loading())
    val state = _state.asStateFlow()

    fun search(city: String) {
        viewModelScope.launch {
            weatherDataSource.search(city).collect {
                _state.value = it
            }
        }
    }
}