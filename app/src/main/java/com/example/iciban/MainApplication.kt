package com.example.iciban

import android.app.Application
import com.example.iciban.di.apiModule
import com.example.iciban.di.preferenceModule
import com.example.iciban.di.repositoryModule
import com.example.iciban.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    // core
                    preferenceModule,
                    repositoryModule,
                    apiModule,
                    // app
                    vmModule,
                )
            )
        }
    }
}
