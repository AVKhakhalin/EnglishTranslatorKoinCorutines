package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel

interface Interactor<T> {

    suspend fun getData(word: String): T
}