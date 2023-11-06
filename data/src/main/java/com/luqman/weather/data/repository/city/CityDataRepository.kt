package com.luqman.weather.data.repository.city

import com.luqman.weather.core.network.extension.runCatchingResponse
import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.database.dao.CityDao
import com.luqman.weather.data.database.entity.CityEntity
import com.luqman.weather.data.repository.model.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CityDataRepository(
    private val cityDao: CityDao,
    private val dispatcher: CoroutineDispatcher
) : CityDataSource {

    override suspend fun getAll(): Flow<Resource<List<City>>> = flow {
        val data = runCatchingResponse(dispatcher) {
            val cities = cityDao.getAll()
            val response = cities.map {
                it.toCity()
            }
            Resource.Success(response)
        }

        emit(data)
    }


    override suspend fun get(cityName: String): Resource<City> {
        return runCatchingResponse(dispatcher) {
            val cityEntity = cityDao.get(cityName)
            val response = cityEntity.toCity()
            Resource.Success(response)
        }
    }

    override suspend fun save(city: City): Long {
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

    private fun CityEntity.toCity(): City {
        return City(id, name)
    }
}