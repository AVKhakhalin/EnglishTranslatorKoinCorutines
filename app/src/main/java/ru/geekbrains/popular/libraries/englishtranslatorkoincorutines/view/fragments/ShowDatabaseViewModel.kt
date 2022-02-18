package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments

import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import ru.geekbrains.popular.libraries.core.viewmodel.BaseViewModel
import ru.geekbrains.popular.libraries.model.data.AppState
import ru.geekbrains.popular.libraries.utils.resources.ResourcesProviderImpl
import ru.geekbrains.popular.libraries.utils.sounds.playSound

class ShowDatabaseViewModel (
    private val interactor: ShowDatabaseInteractor
): BaseViewModel<AppState>() {
    /** Задание переменных */ //region
    private val resourcesProviderImpl: ResourcesProviderImpl = getKoin().get()
    //endregion

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, false) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        _mutableLiveData.postValue(interactor.getData(word))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null, true) //Set View to original state in onStop
        super.onCleared()
    }

    fun deleteDataByWord(word: String) {
        viewModelCoroutineScope.launch {
            interactor.deleteDataByWord(word)
        }
    }

    fun playSoundWord(soundUrl: String) {
        playSound(soundUrl, resourcesProviderImpl.context)
    }
}