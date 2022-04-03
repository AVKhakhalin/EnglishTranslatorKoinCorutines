package ru.geekbrains.popular.libraries.model.repository

import ru.geekbrains.popular.libraries.model.data.AppState

interface RepositoryLocal<T>: Repository<T> {

    suspend fun saveToDB(appState: AppState)

    suspend fun deleteDataByWord(word: String)
}