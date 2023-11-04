package com.luqman.weather.data.repository.model

data class Weather(
    val icon: String = "",
    val city: String = "",
    val weather: String = "",
    val description: String = "",
    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    val windDeg: Int = 0,
    val windGust: Double = 0.0,
    val temp: Double = 0.0,
    val tempFeelsLike: Double = 0.0,
    val tempMin: Double = 0.0,
    val tempMax: Double = 0.0,
    val date: String = "",
    val time: String = ""
)
