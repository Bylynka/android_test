package com.agileengine.test.di

import com.agileengine.test.api.IApiService
import com.agileengine.test.db.AppDatabase
import com.agileengine.test.providers.ReposProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ProvidersModule {


    @Provides
    @Singleton
    fun reposProvider(api: IApiService, db: AppDatabase) = ReposProvider(api, db.repoDao())
}