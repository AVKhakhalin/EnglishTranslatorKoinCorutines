package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource

interface DataSource<T> {

    suspend fun getData(word: String): T
}