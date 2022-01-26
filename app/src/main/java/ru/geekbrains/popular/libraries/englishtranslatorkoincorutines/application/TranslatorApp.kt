package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application

import android.app.Application
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di.AppComponent
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di.DaggerAppComponent

class TranslatorApp : Application() {
    // Рабочий вариант
    companion object {
        lateinit var instance: TranslatorApp
    }

    lateinit var component: AppComponent
    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerAppComponent.builder()
            .setContext(this)
            .build()
    }
}