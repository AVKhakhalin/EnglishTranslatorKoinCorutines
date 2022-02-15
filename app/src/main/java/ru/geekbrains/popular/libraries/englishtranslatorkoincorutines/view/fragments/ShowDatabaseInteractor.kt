package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments

import ru.geekbrains.popular.libraries.model.data.AppState
import ru.geekbrains.popular.libraries.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.RepositoryLocal
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.isEnglish
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel.Interactor

class ShowDatabaseInteractor(
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
): Interactor<AppState> {

    override suspend fun getData(word: String): AppState {
        return AppState.Success(repositoryLocal.getData(word), isEnglish(word))
    }

    override suspend fun deleteDataByWord(word: String) {
        repositoryLocal.deleteDataByWord(word)
    }

    // В этом классе данный метод не нужен
    override suspend fun saveDataToDB(data: List<DataModel>, isEnglish: Boolean) {
//        TODO("Not yet implemented")
    }
}
