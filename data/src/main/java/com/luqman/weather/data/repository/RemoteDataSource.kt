package com.luqman.weather.data.repository

import com.luqman.weather.data.repository.model.Response
import com.luqman.weather.data.services.SomeService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteDataSource(
    private val someService: SomeService,
    private val dispatcher: CoroutineDispatcher
) : DataSource {

    override suspend fun fetch(): List<Response> {
        // TODO:: change with the real data
        return withContext(dispatcher) {
            someService.fetch().map {
                Response(1, it)
            }
        }
    }
}