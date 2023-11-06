package com.luqman.weather.data.repository.city

import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.model.City

interface CityDataSource {

    suspend fun get(
        cityName: String
    ): Resource<City>

    suspend fun save(city: City)

    suspend fun delete(id: Int)
}