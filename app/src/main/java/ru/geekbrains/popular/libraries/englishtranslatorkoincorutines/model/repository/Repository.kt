package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository

interface Repository<T> {

    suspend fun getData(word: String): T
}