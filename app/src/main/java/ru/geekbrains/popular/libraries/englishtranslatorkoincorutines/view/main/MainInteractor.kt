package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main

import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.Repository
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.RepositoryLocal
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.isEnglish
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.network.NetworkStatus
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel.Interactor

class MainInteractor (
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>,
    private val networkStatus: NetworkStatus
): Interactor<AppState> {

    override suspend fun getData(word: String): AppState {
        val appState: AppState
        if (networkStatus.isOnline()) {
            appState = AppState.Success(remoteRepository.getData(word), isEnglish(word))
            localRepository.saveToDB(appState)
        } else {
            appState = AppState.Success(localRepository.getData(word), isEnglish(word))
        }
        return appState
    }

    override suspend fun deleteDataByWord(word: String) {
        // TODO("Not yet implemented")
    }

    override suspend fun saveDataToDB(data: List<DataModel>, isEnglish: Boolean) {
        localRepository.saveToDB(AppState.Success(data, isEnglish))
    }
}