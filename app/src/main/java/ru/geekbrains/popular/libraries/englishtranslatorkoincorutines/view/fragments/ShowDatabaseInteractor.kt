package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments

import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.RepositoryLocal
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel.Interactor

class ShowDatabaseInteractor (
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
): Interactor<AppState> {
    override suspend fun getData(word: String): AppState {
        // TODO isEnglish настроить на вывод из базы данных только русских или только английских слов
        return AppState.Success(repositoryLocal.getData(word), true)
    }
}
