package com.luqman.weather.core.network.exception

import com.luqman.weather.core.model.ResourceText
import com.luqman.weather.core.R
import com.luqman.weather.core.network.exception.ApiException.NoConnectionError
import com.luqman.weather.core.network.exception.ApiException.TimeoutError
import java.io.IOException

/**
 * This sealed class is for storing the error type, so we can pass this to the
 * error callback, and provide a proper error message to the user, depending on the
 * error type.
 *
 * Due to Interceptor only pass through [IOException] derivatives,
 * this Exception will extend [IOException] instead of [Exception]
 *
 * e.g.
 * - [TimeoutError] will trigger timeout message
 * - [NoConnectionError] will trigger no connection message, etc.
 *
 */
sealed class ApiException : IOException() {

    abstract val errorMessage: ResourceText

    object TimeoutError : ApiException() {
        override val errorMessage: ResourceText
            get() = ResourceText.StringId(R.string.timeout_error_exception)
    }

    object NoConnectionError : ApiException() {
        override val errorMessage: ResourceText
            get() = ResourceText.StringId(R.string.no_connection_error_exception)
    }

    object JsonParsingException : ApiException() {
        override val errorMessage: ResourceText
            get() = ResourceText.StringId(R.string.json_parsing_error_exception)
    }

    data class UnknownError(
        val throwable: Throwable,
        override val errorMessage: ResourceText = ResourceText.StringId(R.string.unknown_error_exception)
    ) : ApiException()

    class HttpApiException(
        val code: Int,
        override val message: String,
    ) : ApiException() {

        override val errorMessage: ResourceText
            get() {
                return ResourceText.Plain(message)
            }

    }
}