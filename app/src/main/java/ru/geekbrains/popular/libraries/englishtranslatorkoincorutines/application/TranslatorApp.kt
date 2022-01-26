package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di.AppComponent
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di.DaggerAppComponent
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di.application
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di.mainScreen

class TranslatorApp : Application() {
    companion object {
        lateinit var instance: TranslatorApp
    }
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        // Dagger
        instance = this
        component = DaggerAppComponent.builder()
            .setContext(this)
            .build()

        // Koin
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen))
        }
    }
}