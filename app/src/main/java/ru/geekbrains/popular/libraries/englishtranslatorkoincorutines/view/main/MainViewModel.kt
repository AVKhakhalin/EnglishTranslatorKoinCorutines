package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.getKoin
import ru.geekbrains.popular.libraries.core.viewmodel.BaseViewModel
import ru.geekbrains.popular.libraries.model.Settings.Settings
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.convertDataWordToDataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.isEnglish
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.parseSearchResults
import ru.geekbrains.popular.libraries.model.data.AppState
import ru.geekbrains.popular.libraries.model.data.DataWord
import ru.geekbrains.popular.libraries.utils.resources.ResourcesProviderImpl
import ru.geekbrains.popular.libraries.utils.sounds.playSound

class MainViewModel (
    private val interactor: MainInteractor,
    private val settings: Settings
): BaseViewModel<AppState>() {
    /** Задание переменных */ //region
    // Информация с переводом слова
    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData
    // ResourcesProviderImpl
    private val resourcesProviderImpl: ResourcesProviderImpl = getKoin().get()
    // MainActivity для получения разрешений
    @SuppressLint("StaticFieldLeak")
    private lateinit var mainActivity: MainActivity
    //endregion

    fun subscribe(mainActivity: MainActivity): LiveData<AppState> {
        this.mainActivity = mainActivity
        return liveDataForViewToObserve
    }

    override fun getData() {
        // Выполнение поиска запрошенного слова
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(settings.requestedWord) }
    }

    //Doesn't have to use withContext for Retrofit call if you use
    // .addCallAdapterFactory(CoroutineCallAdapterFactory()). The same goes for Room
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
        this.settings.isThemeDay = settings.isThemeDay
        this.settings.isMain = settings.isMain
        this.settings.isDatabaseShow = settings.isDatabaseShow
        this.settings.requestedWord = settings.requestedWord
    }
    fun loadSettings(): Settings {
        return settings
    }
    fun setRequestedWord(requestedWord: String) {
        this.settings.requestedWord = requestedWord
    }
    fun getRequestedWord(): String = this.settings.requestedWord
    //endregion

    fun saveData(data: DataWord) {
        viewModelCoroutineScope.launch {
            interactor.saveDataToDB(convertDataWordToDataModel(data), isEnglish(data.word))
        }
    }

    fun playSoundWord(soundUrl: String) {
        playSound(soundUrl, resourcesProviderImpl.context)
    }

    /** Получение разрешений на запись и считывание информации с телефона */
    fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (resourcesProviderImpl.context.checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
//                Toast.makeText(resourcesProviderImpl.context,
//                    resourcesProviderImpl.getString(
//                        R.string.get_permission_write_read_text), Toast.LENGTH_LONG).show()
                true
            } else {
//                Toast.makeText(resourcesProviderImpl.context,
//                    resourcesProviderImpl.getString(
//                        R.string.not_get_permission_write_read_text), Toast.LENGTH_LONG).show()
                    ActivityCompat.requestPermissions(mainActivity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1
                    )
                false
            }
        } else {
//            Toast.makeText(resourcesProviderImpl.context,
//                resourcesProviderImpl.getString(
//                    R.string.get_permission_write_read_text), Toast.LENGTH_LONG).show()
            true
        }
    }
}