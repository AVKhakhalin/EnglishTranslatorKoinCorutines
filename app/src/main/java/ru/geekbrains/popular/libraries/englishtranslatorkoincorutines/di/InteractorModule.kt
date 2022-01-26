package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di

import dagger.Module
import dagger.Provides
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application.Constants
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.Repository
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.network.NetworkStatus
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.MainInteractor
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(Constants.NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(Constants.NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>,
        networkStatus: NetworkStatus
    ) = MainInteractor(repositoryRemote, repositoryLocal, networkStatus)
}