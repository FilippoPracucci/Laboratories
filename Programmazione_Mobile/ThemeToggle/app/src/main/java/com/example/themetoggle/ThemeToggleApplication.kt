package com.example.themetoggle

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ThemeToggleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Presa da documentazione
        startKoin {
            androidLogger()
            androidContext(this@ThemeToggleApplication) //@ per dire a cosa fa riferimento this
            modules(appModule)
        }
    }
}