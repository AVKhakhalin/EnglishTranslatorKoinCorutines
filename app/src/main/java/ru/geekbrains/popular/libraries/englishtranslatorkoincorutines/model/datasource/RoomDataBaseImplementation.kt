package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource

import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.room.HistoryDao
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.convertDataModelSuccessToEntity
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(
    private val historyDao: HistoryDao
): DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(word, historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }

    override suspend fun deleteFromDB(word: String) {
        historyDao.deleteDataByWord(word)
    }
}