package ru.geekbrains.popular.libraries.model.datasource

import ru.geekbrains.popular.libraries.model.data.AppState

interface DataSourceLocal<T>: DataSource<T> {

    suspend fun saveToDB(appState: AppState)

    suspend fun deleteFromDB(word: String)
}