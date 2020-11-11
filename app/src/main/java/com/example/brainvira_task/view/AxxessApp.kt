package com.example.brainvira_task.view

import android.app.Application
import com.example.brainvira_task.di.app
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class AxxessApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AxxessApp)
            modules(app)
        }
    }
}