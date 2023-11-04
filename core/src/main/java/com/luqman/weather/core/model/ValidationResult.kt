package com.luqman.weather.core.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)