package com.luqman.weather.data.repository

import com.luqman.weather.data.repository.model.Response

interface DataSource {
    suspend fun fetch(): List<Response>
}