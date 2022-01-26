package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main

import io.reactivex.Observable
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application.Constants
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.Repository
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.network.NetworkStatus
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel.Interactor
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(Constants.NAME_REMOTE) val remoteRepository: Repository<List<DataModel>>,
    @Named(Constants.NAME_LOCAL) val localRepository: Repository<List<DataModel>>,
    val networkStatus: NetworkStatus
): Interactor<AppState> {

    /** Задание переменных */ //region
    // Переменные для распознавания языка вводимого слова для перевода
    private val engPattern: Pattern = Pattern.compile(Constants.ENGLISH_SYMBOLS)
    private var engMatcher: Matcher = engPattern.matcher("")
    private var isEnglishText: Boolean = true
    //endregion

    //    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
    override fun getData(word: String): Observable<AppState> {
        // Определение языка (английский - true, русский - false) вводимого слова
        engMatcher = engPattern.matcher(word)
        isEnglishText = engMatcher.find()
        // Отображение полученного поискового запроса
        return if (networkStatus.isOnline()) {
            remoteRepository.getData(word).map { AppState.Success(it, isEnglishText) }
        } else {
            localRepository.getData(word).map { AppState.Success(it, isEnglishText) }
        }
    }
}