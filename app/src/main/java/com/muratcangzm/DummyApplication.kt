package com.muratcangzm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DummyApplication: Application() {

    override fun onCreate() {
        super.onCreate()


        if(Constants.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(Timber.asTree())

    }
}