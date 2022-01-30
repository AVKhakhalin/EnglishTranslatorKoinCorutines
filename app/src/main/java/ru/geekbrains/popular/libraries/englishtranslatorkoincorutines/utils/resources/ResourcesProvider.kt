package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.resources

import androidx.annotation.StringRes

interface ResourcesProvider {

    fun getString(@StringRes id: Int): String
}