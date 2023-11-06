package com.luqman.weather.data.repository.city

import com.luqman.weather.core.network.extension.runCatchingResponse
import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.database.dao.CityDao
import com.luqman.weather.data.database.entity.CityEntity
import com.luqman.weather.data.repository.model.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CityDataRepository(
    private val cityDao: CityDao,
    private val dispatcher: CoroutineDispatcher
) : CityDataSource {

    override suspend fun get(cityName: String): Resource<City> {
        return runCatchingResponse(dispatcher) {
            val city = cityDao.get(cityName)
            val response = City(
                city.id,
                city.name
            )
            Resource.Success(response)
        }
    }

    override suspend fun save(city: City) {
        return withContext(dispatcher) {
            val save = CityEntity(
                city.id,
                city.name
            )
            cityDao.save(save)
        }
    }

    override suspend fun delete(id: Int) {
        return withContext(dispatcher) {
            cityDao.delete(id)
        }
    }
}