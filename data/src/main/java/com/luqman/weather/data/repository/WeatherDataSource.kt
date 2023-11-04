package com.luqman.weather.data.repository

import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherDataSource {

    suspend fun search(
        city: String
    ): Flow<Resource<List<Weather>>>

    suspend fun fetch(city: String? = null): Resource<List<Weather>>

    suspend fun save(weathers: List<Weather>)

    suspend fun deleteAllData()
}