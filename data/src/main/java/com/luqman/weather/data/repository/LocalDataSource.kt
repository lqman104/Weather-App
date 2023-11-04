package com.luqman.weather.data.repository

import com.luqman.weather.data.repository.model.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID

class LocalDataSource(
    private val dispatcher: CoroutineDispatcher
) : DataSource {

    override suspend fun fetch(): List<Response> {
        return withContext(dispatcher) {
            val list = buildList {
                for (i in 1..10) {
                    add(Response(i, UUID.randomUUID().toString()))
                }
            }

            list
        }
    }
}