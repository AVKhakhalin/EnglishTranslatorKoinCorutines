package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di.application
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di.mainScreen

class TranslatorApp: Application() {
    /** Задание переменных */ //region
    companion object {
        lateinit var instance: TranslatorApp
    }
    // endregion

    override fun onCreate() {
        super.onCreate()
        // Инициализация класса TranslatorApp
        instance = this

        // Koin
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen))
        }
    }
}