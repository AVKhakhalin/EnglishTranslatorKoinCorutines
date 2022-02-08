package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application.Constants
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource.RetrofitImplementation
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource.RoomDataBaseImplementation
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.Repository
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.RepositoryImplementation
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.RepositoryImplementationLocal
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.RepositoryLocal
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.room.HistoryDataBase
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.network.NetworkStatus
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.resources.ResourcesProviderImpl
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments.ShowDatabaseInteractor
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments.ShowDatabaseViewModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.MainInteractor
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.MainViewModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.ThemeColorsImpl
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.imageloader.GlideImageLoaderImpl

val application = module {
    // Локальная база данных
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao()}
    single<RepositoryLocal<List<DataModel>>>(named(Constants.NAME_LOCAL)) {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
    // Удалённый сервер переводчика (API)
    single<Repository<List<DataModel>>>(named(Constants.NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }
    // Вспомогательные классы:
    // Определение статуса сети
    single<NetworkStatus> { NetworkStatus(androidContext()) }
    // Получение доступа к ресурсам
    single<ResourcesProviderImpl> { ResourcesProviderImpl(androidContext()) }
    // Получение доступа к цветам темы
    single<ThemeColorsImpl> { ThemeColorsImpl() }
    // Загрузка изображений с помощью библиотеки Glide
    single<GlideImageLoaderImpl> { GlideImageLoaderImpl() }
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

val showDataBaseScreen = module {
    factory { ShowDatabaseViewModel(get()) }
    factory { ShowDatabaseInteractor(get(named(Constants.NAME_LOCAL)))}
}