package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application

class Constants {
    companion object {
        const val ANGLE_TO_ROTATE_BOTTOM_FAB: Float = -360f
        const val SEARCH_FIELD_TEXT_SIZE: Float = 20F
        const val SHARED_PREFERENCES_KEY: String = "Shared Preferences"
        const val SHARED_PREFERENCES_THEME_KEY: String = "Shared Preferences Is Theme Day"
        const val SHARED_PREFERENCES_MAIN_STATE_KEY: String = "Shared Preferences Is Main State"
        const val BUTTON_CHANGE_THEME_INDEX: Int = 0
        const val ENGLISH_SYMBOLS = "[a-zA-Z]"
        const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"

        // Dagger
        const val NAME_REMOTE = "Remote"
        const val NAME_LOCAL = "Local"
    }
}