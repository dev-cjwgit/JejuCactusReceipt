package com.cjwgit.jejucactusreceipt

import android.app.Application
import com.cjwgit.jejucactusreceipt.di.modelModule
import com.cjwgit.jejucactusreceipt.di.repositoryModule
import com.cjwgit.jejucactusreceipt.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(viewModelModule, modelModule, repositoryModule))
        }
    }
}