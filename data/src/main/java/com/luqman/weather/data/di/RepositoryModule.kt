package com.luqman.weather.data.di

import com.luqman.weather.data.repository.DataSource
import com.luqman.weather.data.repository.LocalDataSource
import com.luqman.weather.data.repository.DataRepository
import com.luqman.weather.data.repository.RemoteDataSource
import com.luqman.weather.data.services.SomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProvinceApiService(
        retrofit: Retrofit
    ): SomeService = retrofit.create(SomeService::class.java)

    @Provides
    @LocalSource
    fun provideProvinceLocalDataSource(): DataSource = LocalDataSource(
        Dispatchers.IO
    )

    @Provides
    @RemoteSource
    fun provideProvinceRemoteDataSource(
        someService: SomeService
    ): DataSource = RemoteDataSource(
        someService,
        Dispatchers.IO
    )

    @Provides
    fun provideProvinceRepository(
        @RemoteSource remoteDataSource: DataSource,
        @LocalSource localDataSource: DataSource
    ): DataSource = DataRepository(
        remoteDataSource = remoteDataSource,
        localDataSource = localDataSource
    )
}