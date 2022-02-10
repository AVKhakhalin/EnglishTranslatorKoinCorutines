package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application.Settings.Settings
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataWord
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.convertDataWordToDataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.isEnglish
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.parseSearchResults
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel.BaseViewModel

class MainViewModel (
    private val interactor: MainInteractor
): BaseViewModel<AppState>() {
    /** Задание переменных */ //region
    // Информация с переводом слова
    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData
    // Сохранение состояния приложения
    private val liveDataSaveSettings: MutableLiveData<Settings> = MutableLiveData()
    //endregion

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

    /** Сохранение и восстановление текущих настроек приложения */ //region
    fun saveSettings(settings: Settings) {
        liveDataSaveSettings.value = settings
    }
    fun loadSettings(): Settings? {
        return liveDataSaveSettings.value
    }
    //endregion

    fun saveData(data: DataWord) {
        viewModelCoroutineScope.launch {
            interactor.saveDataToDB(convertDataWordToDataModel(data), isEnglish(data.word))
        }
    }
}