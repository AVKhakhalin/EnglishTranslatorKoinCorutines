package ru.geekbrains.popular.libraries.core.viewmodel

import ru.geekbrains.popular.libraries.model.data.DataModel

interface Interactor<T> {

    suspend fun getData(word: String): T

    suspend fun deleteDataByWord(word: String)

    suspend fun saveDataToDB(data: List<DataModel>, isEnglish: Boolean)
}