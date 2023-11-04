package com.luqman.weather.data.services

import com.luqman.weather.data.services.dto.WeatherHttpResponse
import retrofit2.http.GET
import retrofit2.http.Query

// https://openweathermap.org/forecast5#data
enum class WeatherUnits(val value: String) {
    METRIC("metric"), STANDARD("standard"), IMPERIAL("imperial")
}

interface WeatherService {
    @GET("data/2.5/forecast")
    fun fetch(
        @Query("lat") lat: String,
        @Query("long") long: String,
        @Query("units") units: String = WeatherUnits.METRIC.value
    ): List<WeatherHttpResponse>

    @GET("data/2.5/forecast")
    fun search(
        @Query("q") city: String,
    ): List<WeatherHttpResponse>
}