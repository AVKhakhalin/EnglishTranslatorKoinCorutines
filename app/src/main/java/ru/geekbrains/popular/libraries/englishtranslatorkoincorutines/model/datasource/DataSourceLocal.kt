package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource

import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState

interface DataSourceLocal<T>: DataSource<T> {

    suspend fun saveToDB(appState: AppState)
}