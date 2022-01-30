package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main

import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application.Constants
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.Repository
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.network.NetworkStatus
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel.Interactor
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainInteractor (
    val remoteRepository: Repository<List<DataModel>>,
    val localRepository: Repository<List<DataModel>>,
    val networkStatus: NetworkStatus
): Interactor<AppState> {

    /** Задание переменных */ //region
    // Переменные для распознавания языка вводимого слова для перевода
    private val engPattern: Pattern = Pattern.compile(Constants.ENGLISH_SYMBOLS)
    private var engMatcher: Matcher = engPattern.matcher("")
    //endregion


    override suspend fun getData(word: String): AppState {
        return AppState.Success(
            if (networkStatus.isOnline()) {
                remoteRepository
            } else {
                localRepository
            }.getData(word),
            isEnglish(word)
        )
    }

    // Определение языка (английский - true, русский - false) вводимого слова
    private fun isEnglish(word: String): Boolean {
        engMatcher = engPattern.matcher(word)
        return engMatcher.find()
    }
}