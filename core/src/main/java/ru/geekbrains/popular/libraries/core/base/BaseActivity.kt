package ru.geekbrains.popular.libraries.core.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import ru.geekbrains.popular.libraries.core.viewmodel.BaseViewModel
import ru.geekbrains.popular.libraries.core.viewmodel.Interactor
import ru.geekbrains.popular.libraries.model.data.AppState

//abstract class BaseActivity<T: AppState, I: Interactor<T>>: AppCompatActivity() {
abstract class BaseActivity<T: AppState, I: Interactor<T>>: FragmentActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(dataModel: T)
}