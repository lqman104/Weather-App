package com.luqman.weather.data.repository

import com.luqman.weather.data.repository.model.Weather

class WeatherDataRepository(
    private val localWeatherDataSource: WeatherDataSource,
    private val remoteWeatherDataSource: WeatherDataSource
): WeatherDataSource {

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