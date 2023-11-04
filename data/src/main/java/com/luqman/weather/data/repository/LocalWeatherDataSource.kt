package com.luqman.weather.data.repository

import com.luqman.weather.data.database.WeatherDatabase
import com.luqman.weather.data.repository.model.Weather
import kotlinx.coroutines.CoroutineDispatcher

class LocalWeatherDataSource(
    private val database: WeatherDatabase,
    private val dispatcher: CoroutineDispatcher
) : WeatherDataSource {

    override suspend fun fetch(lat: String, long: String): Weather {
        TODO("Not yet implemented")
    }

    override suspend fun search(): Weather {
        TODO("Not yet implemented")
    }

    override suspend fun save(weather: Weather) {
        TODO("Not yet implemented")
    }
}