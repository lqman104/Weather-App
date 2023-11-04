package com.luqman.weather.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "weather"
)
data class WeatherEntity(
    @PrimaryKey
    val id: Int = 0,
    val icon: String = "",
    var weather: String = "",
    var description: String = "",
    var temp: Double = 0.0,
    var tempFeelsLike: Double = 0.0,
    var tempMin: Double = 0.0,
    var tempMax: Double = 0.0,
    var date: String = "",
    var time: String = ""
)