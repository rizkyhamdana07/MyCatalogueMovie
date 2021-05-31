package com.rizkyhamdana.mycataloguemovie

import android.app.Application
import com.rizkyhamdana.mycataloguemovie.core.di.databaseModule
import com.rizkyhamdana.mycataloguemovie.core.di.networkModule
import com.rizkyhamdana.mycataloguemovie.core.di.repositoryModule
import com.rizkyhamdana.mycataloguemovie.di.useCaseModule
import com.rizkyhamdana.mycataloguemovie.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}