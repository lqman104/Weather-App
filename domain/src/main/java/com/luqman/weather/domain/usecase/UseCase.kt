package com.luqman.weather.domain.usecase

import com.luqman.weather.core.model.UiText
import com.luqman.weather.core.model.ValidationResult
import com.luqman.weather.domain.R

class UseCase {

    operator fun invoke(input: String): ValidationResult {
        return when {
            input.isEmpty() -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.data_empty_error)
            )
            else -> {
                ValidationResult(
                    successful = true
                )
            }
        }
    }
}