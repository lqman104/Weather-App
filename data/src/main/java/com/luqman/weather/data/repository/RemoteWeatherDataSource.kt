package com.luqman.weather.data.repository

import com.luqman.weather.data.repository.model.Weather
import com.luqman.weather.data.services.WeatherService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteWeatherDataSource(
    private val weatherService: WeatherService,
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