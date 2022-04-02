import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

/** Задание тестовых констант */ //region
internal const val MIN_SDK_VALUE = 18
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