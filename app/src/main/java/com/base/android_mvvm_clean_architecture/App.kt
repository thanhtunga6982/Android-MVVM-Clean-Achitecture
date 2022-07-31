package com.base.android_mvvm_clean_architecture

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class App: Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
    }
}
