package com.luqman.weather.ui

import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.model.Weather

data class MainScreenState(
    val getDataState: Resource<List<Weather>> = Resource.Loading(),
    val allForecast: List<Weather> = listOf(),
    val todayForecast: Weather? = null,
)