package com.codeplayon.framework.mvvm

import android.app.Application
import com.codeplayon.framework.mvvm.di.module.appModule
import com.codeplayon.framework.mvvm.di.module.repoModule
import com.codeplayon.framework.mvvm.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}
