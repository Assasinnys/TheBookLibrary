package com.example.thebooklibrary

import android.app.Application
import com.example.thebooklibrary.di.AppComponent
import com.example.thebooklibrary.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}