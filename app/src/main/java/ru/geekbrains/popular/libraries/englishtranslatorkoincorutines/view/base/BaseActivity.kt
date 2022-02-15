package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.base

import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel.BaseViewModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.viewmodel.Interactor
import ru.geekbrains.popular.libraries.model.data.AppState

abstract class BaseActivity<T: AppState, I: Interactor<T>>: AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(dataModel: T)
}