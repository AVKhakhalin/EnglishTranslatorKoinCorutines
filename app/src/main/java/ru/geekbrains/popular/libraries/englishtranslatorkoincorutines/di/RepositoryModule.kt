package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.di

import dagger.Module
import dagger.Provides
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application.Constants
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource.DataSource
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource.RetrofitImplementation
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource.RoomDataBaseImplementation
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.Repository
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.RepositoryImplementation
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(Constants.NAME_REMOTE)
    internal fun provideRepositoryRemote(
        @Named(Constants.NAME_REMOTE) dataSourceRemote:
        DataSource<List<DataModel>>
    ):
            Repository<List<DataModel>> = RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(Constants.NAME_LOCAL)
    internal fun provideRepositoryLocal(
        @Named(Constants.NAME_LOCAL) dataSourceLocal:
        DataSource<List<DataModel>>
    ):
            Repository<List<DataModel>> = RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(Constants.NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> = RetrofitImplementation()

    @Provides
    @Singleton
    @Named(Constants.NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> =
        RoomDataBaseImplementation()
}