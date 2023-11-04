package com.luqman.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.weather.data.repository.WeatherDataSource
import com.luqman.weather.data.repository.model.Weather
import com.luqman.weather.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase,
    private val weatherDataSource: WeatherDataSource
) : ViewModel() {

    private val _response = MutableStateFlow<List<Weather>>(listOf())
    val response = _response.asStateFlow()

    fun search(query: String) {
        // TODO: search the city
    }
}