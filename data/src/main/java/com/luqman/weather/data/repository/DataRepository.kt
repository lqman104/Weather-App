package com.luqman.weather.data.repository

import com.luqman.weather.data.repository.model.Response

class DataRepository(
    private val localDataSource: DataSource,
    private val remoteDataSource: DataSource
): DataSource {

    override suspend fun fetch(): List<Response> {
        return try {
//            remoteDataSource.fetch()
            localDataSource.fetch()
        } catch (e: Exception) {
            localDataSource.fetch()
        }
    }

}