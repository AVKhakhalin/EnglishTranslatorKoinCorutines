package ru.geekbrains.popular.libraries.utils.resources

import android.content.Context

class ResourcesProviderImpl(
    val context: Context
): ResourcesProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }
}