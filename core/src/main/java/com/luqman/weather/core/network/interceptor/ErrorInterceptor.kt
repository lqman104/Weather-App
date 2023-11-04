package com.luqman.weather.core.network.interceptor

import com.luqman.weather.core.network.exception.ApiException
import com.luqman.weather.core.network.model.ErrorResponse
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException


/**
 * This class is used to intercept exceptions when calling the API. First, we will attempt to
 * proceed with the request, if there are no issues, then return the response. If there are
 * any issues, we will determine what the issue is, and then provide a proper exception to pass
 */

class ErrorInterceptor(
    private val moshi: Moshi
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return interceptError(chain)
    }

    /**
     * This function will try to proceed with the request, if there are no issues, return
     * the response. If the issue is related to the response, throw a [HttpApiException][ApiException.HttpApiException]
     * with the response code and response message from server. If the issue is related to
     * timeouts, no connections, etc., throw the appropriate exceptions.
     *
     * @param chain
     * @throws ApiException
     * @return [Response]
     */
    @Throws(ApiException::class)
    private fun interceptError(chain: Interceptor.Chain): Response {
        try {
            // try to get the response
            val response = chain.proceed(chain.request())

            if (response.isSuccessful) {
                // if response success, return response
                return response
            } else {
                // if response unsuccessful, return appropriate exception
                throw ApiException.HttpApiException(response.code, parseMessage(response.body))
            }
        } catch (throwable: Throwable) {
            // check the exception type
            when (throwable) {
                // SocketTimeOutException -> timeout
                // ConnectException -> failed to connect, most likely timeout as well
                is SocketTimeoutException, is ConnectException -> {
                    throw ApiException.TimeoutError
                }
                // API exception
                is ApiException.HttpApiException -> {
                    throw throwable
                }

                is JsonEncodingException -> {
                    throw ApiException.JsonParsingException
                }

                else -> {
                    if (throwable !is ApiException && throwable is IOException) {
                        // No connection
                        throw ApiException.NoConnectionError
                    } else {
                        // Unknown error
                        throw ApiException.UnknownError(throwable)
                    }
                }
            }
        }
    }

    private fun parseMessage(responseBody: ResponseBody?): String {
        return if (responseBody != null) {
            val adapter = moshi.adapter(ErrorResponse::class.java)
            val response = adapter.fromJson(responseBody.string())
            return response?.message ?: ""
        } else {
            ""
        }
    }
}