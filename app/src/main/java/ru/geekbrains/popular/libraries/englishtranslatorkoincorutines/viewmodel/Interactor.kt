package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel

import io.reactivex.Observable

interface Interactor<T> {

    //    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
    fun getData(word: String): Observable<T>
}