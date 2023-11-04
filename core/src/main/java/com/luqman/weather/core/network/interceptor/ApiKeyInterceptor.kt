package com.luqman.weather.core.network.interceptor

import com.luqman.weather.core.network.ApiEndpoint
import com.luqman.weather.core.network.ApiEndpoint.API_KEY_LABEL
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response  {
        val httpUrl = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(API_KEY_LABEL, ApiEndpoint.API_KEY)
            .build()

        val request = chain
            .request()
            .newBuilder()
            .url(httpUrl)
            .build()

        return chain.proceed(request)
    }
}