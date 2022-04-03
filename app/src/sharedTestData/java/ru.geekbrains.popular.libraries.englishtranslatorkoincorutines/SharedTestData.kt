import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.MainActivity
import ru.geekbrains.popular.libraries.model.Settings.Settings

/** Задание тестовых констант */ //region
internal const val MIN_SDK_VALUE = 18
internal const val DELAY_TIME: Long = 5000
internal const val SEARCHED_WORD: String = "fox"
internal const val SEARCHED_SCROLL_WORD: String = "брусника"
internal const val playerURL: String = "https://d2fmfepycn0xw0.cloudfront.net/" +
        "?gender=male&accent=british&text=ka%CA%8A&transcription=1"
//endregion

// Функция для реализации ожидания
internal fun delayTime(waitTime: Long): ViewAction? {
    return object: ViewAction {
        override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
        override fun getDescription(): String = "Ожидание в течение ${waitTime / 1000 } секунд"
        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(waitTime)
        }
    }
}

// Функция для отправки запроса на поиск слова в MainActivity
internal fun loadList(scenario: ActivityScenario<MainActivity>) {
    // Проверка стартовых настроек приложения и их установка для тестирования списка в активити
    scenario.onActivity {
        // Получение настроек приложения
        val previousRequest: Settings = it.model.loadSettings()
        // Проверка какое окно сейчас отображается с данными из базы данных (true) или
        // с данными из сети (false)
        if (previousRequest.isDatabaseShow) {
            it.onBackPressed()
        }
        // Проверка вида меню приложения: true - отображается поисковое поле,
        // false - поисковое поле не отображается
        if (!previousRequest.isMain) {
            Espresso.onView(ViewMatchers.withId(R.id.bottom_app_bar_fab))
                .perform(ViewActions.click())
        }
    }
    // Установка и отправка запроса в сеть
    Espresso.onView(ViewMatchers.withId(R.id.search_src_text)).perform(ViewActions.click())
    Espresso.onView(ViewMatchers.withId(R.id.search_src_text))
        .perform(ViewActions.replaceText(SEARCHED_WORD), ViewActions.closeSoftKeyboard())
    Espresso.onView(ViewMatchers.withId(R.id.search_src_text))
        .perform(ViewActions.pressImeActionButton())
}

// Функция для клика на картинку мышки для сохранения выбранного элемента в базу данных
internal fun tapOnItemWithId(id: Int) = object: ViewAction {
    override fun getConstraints(): Matcher<View>? {
        return null
    }
    override fun getDescription(): String {
        return "Нажимаем на view с указанным id"
    }
    override fun perform(uiController: UiController, view: View) {
        val v = view.findViewById(id) as View
        v.performClick()
    }
}
