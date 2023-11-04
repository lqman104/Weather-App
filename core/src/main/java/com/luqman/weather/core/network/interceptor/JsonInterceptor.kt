package com.luqman.weather.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

private const val CONTENT_TYPE: String = "accept"
private const val APPLICATION_JSON_TYPE: String = "application/json"

class JsonContentTypeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request()
            .newBuilder()
            .addHeader(CONTENT_TYPE, APPLICATION_JSON_TYPE)
        return chain.proceed(requestBuilder.build())
    }
}