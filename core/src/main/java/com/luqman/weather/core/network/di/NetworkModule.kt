package com.luqman.weather.core.network.di

import com.luqman.weather.core.BuildConfig
import com.luqman.weather.core.network.ApiEndpoint
import com.luqman.weather.core.network.interceptor.ErrorInterceptor
import com.luqman.weather.core.network.interceptor.ApiKeyInterceptor
import com.luqman.weather.core.network.interceptor.JsonContentTypeInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideDefaultOkHttpClient(
        moshi: Moshi
    ): OkHttpClient {
        val logging: HttpLoggingInterceptor = HttpLoggingInterceptor {
            Timber.tag("Api log: ").d(it)
        }.apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val builder = OkHttpClient.Builder()
            .connectTimeout(30000, TimeUnit.SECONDS)
            .readTimeout(60000, TimeUnit.SECONDS)
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(ErrorInterceptor(moshi))
            .addInterceptor(JsonContentTypeInterceptor())

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging)
        }

        return builder.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiEndpoint.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}