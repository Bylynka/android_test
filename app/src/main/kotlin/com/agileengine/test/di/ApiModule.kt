package com.agileengine.test.di

import com.agileengine.test.BuildConfig
import com.agileengine.test.api.IApiService
import com.agileengine.test.api.repos.IReposApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)


    @Provides
    @Singleton
    fun retrofit(okHttpClientBuilder: OkHttpClient.Builder): Retrofit {
        val moshi = Moshi.Builder().build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun restApi(retrofit: Retrofit): IApiService =
        ApiService(retrofit)


    private class ApiService(retrofit: Retrofit) :
        IApiService,
        IReposApi by retrofit.create(IReposApi::class.java)
}