package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.parseSearchResults
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel.BaseViewModel

class MainViewModel (
    private val interactor: MainInteractor
): BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word) }
    }

    //Doesn't have to use withContext for Retrofit call if you use .addCallAdapterFactory(CoroutineCallAdapterFactory()). The same goes for Room
    private suspend fun startInteractor(word: String) = withContext(Dispatchers.IO) {
        _mutableLiveData.postValue(parseSearchResults(interactor.getData(word)))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null, true)
        super.onCleared()
    }
}