package com.luqman.weather.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "weather"
)
data class WeatherEntity(
    @PrimaryKey
    val id: Int = 0,
    val city: String = "",
    val icon: String = "",
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