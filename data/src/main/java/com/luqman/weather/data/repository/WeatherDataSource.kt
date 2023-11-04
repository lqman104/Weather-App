package com.luqman.weather.data.repository

import com.luqman.weather.data.repository.model.Weather

interface WeatherDataSource {
    suspend fun fetch(
        lat: String,
        long: String
    ): Weather

    suspend fun search(): Weather

    suspend fun save(weather: Weather)
}