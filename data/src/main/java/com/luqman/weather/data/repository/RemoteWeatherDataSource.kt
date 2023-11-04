package com.luqman.weather.data.repository

import com.luqman.weather.core.network.exception.ImplementationShouldBeNotCalledException
import com.luqman.weather.core.network.extension.runCatchingResponse
import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.model.Weather
import com.luqman.weather.data.services.WeatherService
import com.luqman.weather.data.services.dto.WeatherHttpResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteWeatherDataSource(
    private val weatherService: WeatherService,
    private val dispatcher: CoroutineDispatcher
) : WeatherDataSource {

    override suspend fun search(city: String): Flow<Resource<List<Weather>>> = flow {
        throw ImplementationShouldBeNotCalledException()
    }

    override suspend fun fetch(city: String?): Resource<List<Weather>> {
        return runCatchingResponse(dispatcher) {
            val response = weatherService.search(city.orEmpty())
            val list = response.data.toModel()
            Resource.Success(list)
        }
    }

    override suspend fun save(weathers: List<Weather>) {
        throw ImplementationShouldBeNotCalledException()
    }

    override suspend fun deleteAllData() {
        throw ImplementationShouldBeNotCalledException()
    }

    private fun WeatherHttpResponse.toModel(): List<Weather> {
        return this.list?.map {
            val weather = it.weather?.firstOrNull()
            val dateTime = it.dtTxt.orEmpty().split(" ", ignoreCase = false)
            Weather(
                city = city?.name.orEmpty(),
                icon = weather?.icon.orEmpty(),
                weather = weather?.main.orEmpty(),
                description = weather?.description.orEmpty(),
                humidity = it.main?.humidity ?: 0,
                windSpeed = it.wind?.speed ?: 0.0,
                windDeg = it.wind?.deg ?: 0,
                windGust = it.wind?.gust ?: 0.0,
                temp = it.main?.temp ?: 0.0,
                tempFeelsLike = it.main?.feelsLike ?: 0.0,
                tempMin = it.main?.tempMin ?: 0.0,
                tempMax = it.main?.tempMax ?: 0.0,
                date = dateTime.getOrNull(0).orEmpty(),
                time = dateTime.getOrNull(1).orEmpty(),
            )
        } ?: emptyList()
    }
}