package com.crislozano.rickymortyfans

import android.app.Application
import com.crislozano.rickymortyfans.di.dataModule
import com.crislozano.rickymortyfans.di.domainModule
import com.crislozano.rickymortyfans.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RickyMortyFansApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RickyMortyFansApp)
            modules(listOf(dataModule, domainModule, presentationModule))
        }
    }
}