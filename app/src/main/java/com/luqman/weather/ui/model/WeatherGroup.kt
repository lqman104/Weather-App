package com.luqman.weather.ui.model

import com.luqman.weather.data.repository.model.Weather

data class WeatherGroup(
    val date: String,
    val forecast: MutableList<Weather>
)