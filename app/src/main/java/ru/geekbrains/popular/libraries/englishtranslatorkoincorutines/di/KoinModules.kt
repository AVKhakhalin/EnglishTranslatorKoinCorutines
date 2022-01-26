package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application.Constants
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource.RetrofitImplementation
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource.RoomDataBaseImplementation
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.Repository
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.RepositoryImplementation
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.network.NetworkStatus
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.resources.ResourcesProviderImpl
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.MainInteractor
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.MainViewModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.ThemeColorsImpl

val application = module {
    single<Repository<List<DataModel>>>(named(Constants.NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }
    single<Repository<List<DataModel>>>(named(Constants.NAME_LOCAL)) {
        RepositoryImplementation(RoomDataBaseImplementation())
    }
    single<NetworkStatus> {
        NetworkStatus(androidContext())
    }
    single<ResourcesProviderImpl> {
        ResourcesProviderImpl(androidContext())
    }
    single<ThemeColorsImpl> {
        ThemeColorsImpl()
    }
}

val mainScreen = module {
    factory {
        MainInteractor(
            get(named(Constants.NAME_REMOTE)),
            get(named(Constants.NAME_LOCAL)),
            NetworkStatus(get())
            )
    }
    factory { MainViewModel(get()) }
}