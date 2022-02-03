package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository

import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState

interface RepositoryLocal<T>: Repository<T> {

    suspend fun saveToDB(appState: AppState)
}