package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource

import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        //To change body of created functions use File | Settings | File Templates.
        TODO("not implemented")
    }
}