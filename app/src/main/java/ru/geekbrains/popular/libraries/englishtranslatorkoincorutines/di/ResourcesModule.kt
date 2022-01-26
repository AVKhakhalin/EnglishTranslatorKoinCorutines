package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di

import dagger.Module
import dagger.Provides
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.resources.ResourcesProvider
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.resources.ResourcesProviderImpl
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.ThemeColors
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.ThemeColorsImpl
import javax.inject.Singleton

@Module
class ResourcesModule {

    @Singleton
    @Provides
    fun resProvider(resourcesProviderImpl: ResourcesProviderImpl): ResourcesProvider {
        return resourcesProviderImpl
    }

    @Singleton
    @Provides
    fun themeColorsProvider(themeColorsImpl: ThemeColorsImpl): ThemeColors {
        return themeColorsImpl
    }
}