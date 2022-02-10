package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application.Settings

class Settings (
    private val isThemeDay: Boolean,
    private val isMain: Boolean,
    private val isDatabaseShow: Boolean
) {
    fun getIsThemeDay(): Boolean {
        return isThemeDay
    }
    fun getIsMain(): Boolean {
        return isMain
    }
    fun getIsDatabaseShow(): Boolean {
        return isDatabaseShow
    }
}