package com.luqman.weather.data.repository

import com.luqman.weather.core.network.exception.ImplementationShouldBeNotCalledException
import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class WeatherDataRepository(
    private val localWeatherDataSource: WeatherDataSource,
    private val remoteWeatherDataSource: WeatherDataSource
) : WeatherDataSource {

    override suspend fun fetch(city: String?): Resource<List<Weather>> {
        throw ImplementationShouldBeNotCalledException()
    }

    override suspend fun search(city: String): Flow<Resource<List<Weather>>> = flow {
        Timber.e("disini loading")
        emit(Resource.Loading())

        val localData = localWeatherDataSource.fetch()
        if (localData is Resource.Success && localData.data?.isNotEmpty() == true) {
            Timber.e("disini ada")
            emit(localData)
        }

        val response = remoteWeatherDataSource.fetch(city)

        if (response is Resource.Success) {
            Timber.e("disini remote success")
            localWeatherDataSource.deleteAllData()
            localWeatherDataSource.save(response.data.orEmpty())

            emit(response)
        }
    }

    override suspend fun save(weathers: List<Weather>) {
        throw ImplementationShouldBeNotCalledException()
    }

    override suspend fun deleteAllData() {
        throw ImplementationShouldBeNotCalledException()
    }
}