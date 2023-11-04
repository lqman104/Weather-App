package com.luqman.weather.core.network.model

import com.luqman.weather.core.model.ResourceText

sealed class Resource<T>(
    open val data: T? = null,
    open val error: ResourceText? = null
) {
    data class Success<T>(
        override val data: T?,
    ) : Resource<T>(data = data)

    data class Error<T>(
        override val error: ResourceText?
    ) : Resource<T>(error = error)

    class Loading<T> : Resource<T>()
}
