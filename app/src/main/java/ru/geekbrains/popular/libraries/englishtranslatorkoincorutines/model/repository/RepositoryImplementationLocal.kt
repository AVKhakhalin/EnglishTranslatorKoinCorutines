package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository

import ru.geekbrains.popular.libraries.model.data.AppState
import ru.geekbrains.popular.libraries.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource.DataSourceLocal

class RepositoryImplementationLocal(
    private val dataSource: DataSourceLocal<List<DataModel>>
): RepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }

    override suspend fun deleteDataByWord(word: String) {
        dataSource.deleteFromDB(word)
    }
}