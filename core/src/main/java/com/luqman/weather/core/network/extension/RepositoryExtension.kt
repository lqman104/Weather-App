package com.luqman.weather.core.network.extension

import com.luqman.weather.core.R
import com.luqman.weather.core.model.ResourceText
import com.luqman.weather.core.network.exception.ApiException
import com.luqman.weather.core.network.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> runCatchingResponse(
    dispatcher: CoroutineDispatcher,
    action: suspend () -> Resource<T>
): Resource<T> {
    return withContext(dispatcher) {
        try {
            action()
        } catch (e: Exception) {
            if (e is ApiException) {
                Resource.Error(
                    error = e.errorMessage
                )
            } else {
                e.message?.let {
                    Resource.Error(error = ResourceText.Plain(message = it))
                }?: Resource.Error(error = ResourceText.StringId(resId = R.string.network_unknown_error_exception))

            }
        }
    }
}