package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.themecolors.ThemeColorsImpl
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments.ShowDatabaseInteractor
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments.ShowDatabaseViewModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.MainInteractor
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.MainViewModel
import ru.geekbrains.popular.libraries.model.Constants
import ru.geekbrains.popular.libraries.model.Settings.Settings
import ru.geekbrains.popular.libraries.model.data.DataModel
import ru.geekbrains.popular.libraries.model.datasource.RetrofitImplementation
import ru.geekbrains.popular.libraries.model.datasource.RoomDataBaseImplementation
import ru.geekbrains.popular.libraries.model.repository.Repository
import ru.geekbrains.popular.libraries.model.repository.RepositoryImplementation
import ru.geekbrains.popular.libraries.model.repository.RepositoryImplementationLocal
import ru.geekbrains.popular.libraries.model.repository.RepositoryLocal
import ru.geekbrains.popular.libraries.model.room.HistoryDataBase
import ru.geekbrains.popular.libraries.utils.imageloader.GlideImageLoaderImpl
import ru.geekbrains.popular.libraries.utils.network.NetworkStatus
import ru.geekbrains.popular.libraries.utils.resources.ResourcesProviderImpl

val application = module {
    // Локальная база данных
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java,
        Constants.NAME_DATABASE).build() }
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
    // Сохранение настроек приложения во ViewModel
    single<Settings> { Settings() }
}

val mainScreen = module {
    scope(named(Constants.MAIN_ACTIVITY_SCOPE)) {
        scoped {
            MainInteractor(
                get(named(Constants.NAME_REMOTE)),
                get(named(Constants.NAME_LOCAL)),
                NetworkStatus(get())
            )
        }
        viewModel {
            MainViewModel(getScope(Constants.MAIN_ACTIVITY_SCOPE).get(), get())
        }
    }
}

val showDataBaseScreen = module {
    scope(named(Constants.SHOW_DATABASE_FRAGMENT_SCOPE)) {
        scoped { ShowDatabaseInteractor(get(named(Constants.NAME_LOCAL))) }
        viewModel {
            ShowDatabaseViewModel(getScope(Constants.SHOW_DATABASE_FRAGMENT_SCOPE).get(), get())
        }
    }
}