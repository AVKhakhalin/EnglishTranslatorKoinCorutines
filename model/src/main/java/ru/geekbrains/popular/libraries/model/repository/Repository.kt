package ru.geekbrains.popular.libraries.model.repository

interface Repository<T> {

    suspend fun getData(word: String): T
}