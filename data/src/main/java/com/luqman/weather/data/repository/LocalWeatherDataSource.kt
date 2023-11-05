package com.luqman.weather.data.repository

import com.luqman.weather.core.model.ResourceText
import com.luqman.weather.core.network.exception.ImplementationShouldBeNotCalledException
import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.database.dao.WeatherDao
import com.luqman.weather.data.database.entity.WeatherEntity
import com.luqman.weather.data.repository.model.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalWeatherDataSource(
    private val weatherDao: WeatherDao,
    private val dispatcher: CoroutineDispatcher
) : WeatherDataSource {

    override suspend fun fetch(city: String?): Resource<List<Weather>> {
        return withContext(dispatcher) {
            try {
                val response = weatherDao.getAll()
                val list = response.map {
                    it.toModel()
                }
                Resource.Success(list)
            } catch (e: Exception) {
                Resource.Error(ResourceText.Plain(e.message.orEmpty()))
            }
        }
    }

    override suspend fun search(city: String): Flow<Resource<List<Weather>>> {
        throw ImplementationShouldBeNotCalledException()
    }

    override suspend fun save(weathers: List<Weather>) {
        val data = weathers.map { it.toEntities() }
        weatherDao.save(data)
    }

    override suspend fun deleteAllData() {
        weatherDao.deleteAll()
    }

    private fun WeatherEntity.toModel(): Weather {
        return Weather(
            icon = icon,
            weather = weather,
            humidity = humidity,
            windSpeed = windSpeed,
            windDeg = windDeg,
            windGust = windGust,
            city = city,
            description = description,
            temp = temp,
            tempFeelsLike = tempFeelsLike,
            tempMin = tempMin,
            tempMax = tempMax,
            date = date,
            time = time,
        )
    }

    private fun Weather.toEntities(): WeatherEntity {
        return WeatherEntity(
            icon = icon,
            weather = weather,
            humidity = humidity,
            windSpeed = windSpeed,
            windDeg = windDeg,
            windGust = windGust,
            city = city,
            description = description,
            temp = temp,
            tempFeelsLike = tempFeelsLike,
            tempMin = tempMin,
            tempMax = tempMax,
            date = date,
            time = time,
        )
    }
}