package ru.geekbrains.popular.libraries.model.repository

import ru.geekbrains.popular.libraries.model.data.DataModel
import ru.geekbrains.popular.libraries.model.datasource.DataSource

class RepositoryImplementation(
    private val dataSource: DataSource<List<DataModel>>
): Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}