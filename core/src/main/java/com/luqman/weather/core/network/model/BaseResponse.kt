package com.luqman.weather.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*
* This model depend on your basic API structure
* */
@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @field:Json(name="data")
    val data: T,

    @field:Json(name="message")
    val message: String? = null,

    @field:Json(name="status")
    val status: String? = null
)

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @field:Json(name="message")
    val message: String? = null,

    @field:Json(name="status")
    val status: String? = null
)