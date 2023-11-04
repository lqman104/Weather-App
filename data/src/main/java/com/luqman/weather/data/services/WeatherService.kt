package com.luqman.weather.data.services

import com.luqman.weather.core.network.model.BaseResponse
import com.luqman.weather.data.services.dto.WeatherHttpResponse
import retrofit2.http.GET
import retrofit2.http.Query

// https://openweathermap.org/forecast5#data
enum class WeatherUnits(val value: String) {
    METRIC("metric"), STANDARD("standard"), IMPERIAL("imperial")
}

interface WeatherService {
    @GET("data/2.5/forecast")
    fun search(
        @Query("q") city: String,
        @Query("units") units: String = WeatherUnits.METRIC.value
    ): BaseResponse<WeatherHttpResponse>
}