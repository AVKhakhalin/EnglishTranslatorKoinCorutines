package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource

import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel

class DataSourceLocal(
    private val remoteProvider:
    RoomDataBaseImplementation = RoomDataBaseImplementation()
): DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}