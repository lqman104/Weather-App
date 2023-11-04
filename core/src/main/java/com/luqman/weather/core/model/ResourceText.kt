package com.luqman.weather.core.model

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class ResourceText {

    data class Plain(
        val message: String
    ): ResourceText()

    data class StringId(
        @StringRes val resId: Int
    ): ResourceText()

}

@Composable
fun ResourceText.asString(): String {
    return when(this) {
        is ResourceText.Plain -> message
        is ResourceText.StringId -> stringResource(id = resId)
    }
}

fun ResourceText.asString(context: Context): String {
    return when(this) {
        is ResourceText.Plain -> message
        is ResourceText.StringId -> context.getString(resId)
    }
}