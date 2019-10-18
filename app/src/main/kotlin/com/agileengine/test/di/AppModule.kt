package com.agileengine.test.di

import android.content.Context
import com.agileengine.test.TestApp
import com.agileengine.test.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun context(): Context = TestApp.instance

    @Provides
    @Singleton
    fun database(context: Context) = AppDatabase.getInstance(context)
}