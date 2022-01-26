package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.rx.SchedulerProvider

abstract class BaseViewModel<T : AppState>(
    protected val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
): ViewModel() {

    //    abstract fun getData(word: String, isOnline: Boolean)
    abstract fun getData(word: String)

    override fun onCleared() {
        compositeDisposable.clear()
    }
}