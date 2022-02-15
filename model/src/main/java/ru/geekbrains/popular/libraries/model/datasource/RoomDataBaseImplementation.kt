package ru.geekbrains.popular.libraries.model.datasource

import org.koin.java.KoinJavaComponent.getKoin
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.convertDataModelSuccessToEntity
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.mapHistoryEntityToSearchResult
import ru.geekbrains.popular.libraries.model.R
import ru.geekbrains.popular.libraries.model.data.AppState
import ru.geekbrains.popular.libraries.model.data.DataModel
import ru.geekbrains.popular.libraries.model.room.HistoryDao
import ru.geekbrains.popular.libraries.utils.resources.ResourcesProviderImpl

class RoomDataBaseImplementation(
    private val historyDao: HistoryDao
): DataSourceLocal<List<DataModel>> {
    /** Задание переменных */ //region
    private val resourcesProviderImpl: ResourcesProviderImpl = getKoin().get()
    //endregion

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(word, historyDao.all(),
            "${resourcesProviderImpl.getString(
            R.string.error_textview_stub)}: ${
            resourcesProviderImpl.getString(R.string.empty_request_error)}",
            resourcesProviderImpl.getString(
                R.string.search_info_begin),
            resourcesProviderImpl.getString(
                R.string.search_info_end)
        )
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