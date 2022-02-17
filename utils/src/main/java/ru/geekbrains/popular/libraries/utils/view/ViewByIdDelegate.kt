package ru.geekbrains.popular.libraries.utils.view

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import ru.geekbrains.popular.libraries.utils.R
import ru.geekbrains.popular.libraries.utils.resources.ResourcesProviderImpl
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class ViewByIdDelegate<out T: View>(
    private val rootGetter: () -> View?,
    private val viewId: Int,
    private val resourcesProviderImpl: ResourcesProviderImpl
) {
    /** Исходные данные */ //region
    // Ссылка на root
    private var rootRef: WeakReference<View>? = null
    // Ссылка на View
    private var viewRef: T? = null

    //endregion

    // Метод вызывается при каждом обращении к переменной
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        var view = viewRef
        val cachedRoot = rootRef?.get()
        // Получаем root
        val currentRoot = rootGetter()

        if (currentRoot != cachedRoot || view == null) {
            if (currentRoot == null) {
                if (view != null) {
                    // Failsafe, возвращать хотя бы последнюю View
                    return view
                }
                throw IllegalStateException(
                    "${resourcesProviderImpl.getString(R.string.error_textview_stub)}: ${
                        resourcesProviderImpl.getString(R.string.view_not_exist_error)}")
            }
            // Создаём View
            view = currentRoot.findViewById(viewId)
            // Сохраняем ссылку на View, чтобы не создавать её каждый раз
            // заново
            viewRef = view
            // Сохраняем ссылку на root, чтобы не искать его каждый раз заново
            rootRef = WeakReference(currentRoot)
        }

        checkNotNull(view) { "${resourcesProviderImpl.getString(R.string.error_textview_stub)}: ${
            resourcesProviderImpl.getString(R.string.view_not_found_error)} \"$viewId\"" }
        // Возвращаем View в момент обращения к ней
        return view
    }
}

fun <T: View> Activity.viewById(
    @IdRes viewId: Int,
    resourcesProviderImpl: ResourcesProviderImpl
): ViewByIdDelegate<T> {
    // Возвращаем корневую View для Activity
    return ViewByIdDelegate(
        {window.decorView.findViewById(android.R.id.content)},
        viewId,
        resourcesProviderImpl
    )
}

fun <T: View> Fragment.viewById(
    @IdRes viewId: Int,
    resourcesProviderImpl: ResourcesProviderImpl
): ViewByIdDelegate<T> {
    // Возвращаем корневую View для Fragment
    return ViewByIdDelegate(
        { view },
        viewId,
        resourcesProviderImpl
    )
}