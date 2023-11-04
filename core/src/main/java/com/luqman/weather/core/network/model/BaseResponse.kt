package com.luqman.weather.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*
* This model depend on your basic API structure
* */
@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    val data: T,
)

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @field:Json(name="message")
    val message: String? = null,

    @field:Json(name="code")
    val code: Int? = null
)