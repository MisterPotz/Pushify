package com.gornostaevas.pushify

import android.app.Application
import com.gornostaevas.pushify.di.ApplicationComponent
import com.gornostaevas.pushify.di.DaggerApplicationComponent
import com.gornostaevas.pushify.di.ModelModule
import com.vk.api.sdk.VK
import timber.log.Timber

class MyApplication : Application() {
    lateinit var appComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("App created")
        appComponent = DaggerApplicationComponent
            .builder()
            .modelModule(ModelModule(applicationContext))
            .build()

        VK.initialize(applicationContext)
    }
}