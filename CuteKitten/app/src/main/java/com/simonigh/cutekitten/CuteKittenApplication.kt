package com.simonigh.cutekitten

import android.app.Application
import com.simonigh.cutekitten.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class CuteKittenApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
    
        Timber.plant(DebugTree())
    
        startKoin {
            androidLogger()
            androidContext(this@CuteKittenApplication)
            modules(appModule)
        }
    }
}