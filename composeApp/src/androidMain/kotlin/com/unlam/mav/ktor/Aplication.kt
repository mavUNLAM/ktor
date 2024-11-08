package com.unlam.mav.ktor

import android.app.Application

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        /*
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }

         */
    }
}
