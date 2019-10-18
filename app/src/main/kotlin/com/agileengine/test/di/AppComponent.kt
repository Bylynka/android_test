package com.agileengine.test.di

import com.agileengine.test.features.repos.ReposActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, ProvidersModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: ReposActivity)
}