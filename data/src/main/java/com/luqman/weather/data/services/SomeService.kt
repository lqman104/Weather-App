package com.luqman.weather.data.services

import retrofit2.http.GET

interface SomeService {
    @GET("someservice")
    fun fetch(): List<String>
}