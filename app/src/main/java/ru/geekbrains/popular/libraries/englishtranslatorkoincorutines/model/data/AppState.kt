package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data

sealed class AppState {

    data class Success(val data: List<DataModel>?, val isEnglish: Boolean) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}