package ru.geekbrains.popular.libraries.model.datasource

interface DataSource<T> {

    suspend fun getData(word: String): T
}