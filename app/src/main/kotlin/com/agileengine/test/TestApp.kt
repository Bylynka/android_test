package com.agileengine.test

import android.app.Application
import com.agileengine.test.di.AppComponent
import com.agileengine.test.di.DaggerAppComponent

class TestApp : Application() {

    companion object {
        lateinit var instance: Application
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerAppComponent.create()
    }
}