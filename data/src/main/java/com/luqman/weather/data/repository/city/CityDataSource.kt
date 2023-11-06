package com.luqman.weather.data.repository.city

import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.model.City
import kotlinx.coroutines.flow.Flow

interface CityDataSource {

    suspend fun getAll(): Flow<Resource<List<City>>>

    suspend fun get(
        cityName: String
    ): Resource<City>

    suspend fun save(city: City): Long

    suspend fun delete(id: Int)
}