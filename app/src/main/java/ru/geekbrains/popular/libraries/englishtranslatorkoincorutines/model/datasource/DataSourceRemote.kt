package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource

import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel

class DataSourceRemote(
    private val remoteProvider:
    RetrofitImplementation = RetrofitImplementation()
): DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}