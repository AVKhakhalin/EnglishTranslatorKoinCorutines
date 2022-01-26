package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.MainActivity
import javax.inject.Singleton

@Component(
    modules = [
        ResourcesModule::class
    ]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setContext(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(context: Context)
}