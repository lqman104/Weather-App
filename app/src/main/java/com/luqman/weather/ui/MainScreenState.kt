package com.luqman.weather.ui

import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.model.Weather
import com.luqman.weather.ui.model.WeatherGroup

data class MainScreenState(
    val getDataState: Resource<List<Weather>>? = null,
    val allForecast: List<WeatherGroup> = listOf(),
    val todayForecast: Weather? = null,
    val isFavoriteCity: Boolean = false,
    val cityId: Int = 0,
    val query: String? = null,
)