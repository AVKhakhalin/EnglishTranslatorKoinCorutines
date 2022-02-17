package ru.geekbrains.popular.libraries.model

class Constants {
    companion object {
        const val ANGLE_TO_ROTATE_BOTTOM_FAB: Float = -360f
        const val SEARCH_FIELD_TEXT_SIZE: Float = 20F
        const val SHARED_PREFERENCES_KEY: String = "Shared Preferences"
        const val SHARED_PREFERENCES_THEME_KEY: String = "Shared Preferences Is Theme Day"
        const val SHARED_PREFERENCES_MAIN_STATE_KEY: String = "Shared Preferences Is Main State"
        const val SHARED_PREFERENCES_DATABASE_SCREEN: String = "Shared Preferences Is Database Show"
        const val BUTTON_LOAD_FROM_DB_INDEX: Int = 0
        const val BUTTON_CHANGE_THEME_INDEX: Int = 1
        const val ENGLISH_SYMBOLS = "[a-zA-Z]"
        const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"
        const val HTTPS_BASE = "https:"
        const val CACHE_FOLDER_NAME = "/Pictograms/"
        const val CACHE_FILE_EXTENSION = ".jpg"
        const val MAIN_ACTIVITY_SCOPE = "MAIN_ACTIVITY_SCOPE"
        const val SHOW_DATABASE_FRAGMENT_SCOPE = "SHOW_DATABASE_FRAGMENT_SCOPE"
        const val NAME_DATABASE = "HistoryDB"

        // Dagger
        const val NAME_REMOTE = "Remote"
        const val NAME_LOCAL = "Local"
    }
}