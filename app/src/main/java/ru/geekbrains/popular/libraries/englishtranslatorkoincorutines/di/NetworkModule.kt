package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.network.NetworkStatus
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun networkStatus(context: Context): NetworkStatus {
        return NetworkStatus(context)
    }
}