package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource

import io.reactivex.Observable
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        //To change body of created functions use File | Settings | File Templates.
        TODO("not implemented")
    }
}