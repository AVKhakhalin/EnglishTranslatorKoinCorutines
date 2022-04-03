package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.activity

import DELAY_TIME
import MIN_SDK_VALUE
import SEARCHED_SCROLL_WORD
import SEARCHED_WORD
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import delayTime
import junit.framework.Assert.assertNotNull
import junit.framework.TestCase
import loadList
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.MainActivity
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.adapter.WordViewHolder
import tapOnItemWithId

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = MIN_SDK_VALUE)
class MainActivityRecyclerViewEspressoTest {
    /** Задание переменных */ //region
    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context
    //endregion

    @Before // Инициализация сценария для активити
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test // Проверка на существование активити
    fun mainActivity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test // Проверка прохождения активити через метод onResume()
    fun mainActivity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test // Проверка скрола до позиции
    fun mainActivity_PerformScrollTo() {
        loadList(scenario)
        Espresso.onView(ViewMatchers.isRoot()).perform(delayTime(DELAY_TIME))
        onView(withId(R.id.main_activity_recyclerview)).perform(
            RecyclerViewActions.scrollTo<WordViewHolder>(
                hasDescendant(withText(SEARCHED_SCROLL_WORD))
            )
        )
    }

    @Test // Проверка клика по первой позиции в списке
    fun mainActivity_PerformClickAtPosition() {
        loadList(scenario)
        Espresso.onView(ViewMatchers.isRoot()).perform(delayTime(DELAY_TIME))
        onView(withId(R.id.main_activity_recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<WordViewHolder>(0, click())
        )
    }

    @Test // Проверка клика по выбранной позиции
    fun mainActivity_PerformClickOnItem() {
        loadList(scenario)
        Espresso.onView(ViewMatchers.isRoot()).perform(delayTime(DELAY_TIME))
        onView(withId(R.id.main_activity_recyclerview)).perform(
            RecyclerViewActions.scrollTo<WordViewHolder>(
                hasDescendant(withText(SEARCHED_SCROLL_WORD))
            )
        )
        onView(withId(R.id.main_activity_recyclerview)).perform(
            RecyclerViewActions.actionOnItem<WordViewHolder>(
                hasDescendant(withText(SEARCHED_SCROLL_WORD)), click()
            )
        )
    }

    @Test // Проверка клика по кнопке с мышкой для сохранения позиции в базе данных
    fun mainActivity_PerformClickOnMouse() {
        loadList(scenario)
        onView(withId(R.id.main_activity_recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<WordViewHolder>(
                1,
                tapOnItemWithId(R.id.main_save_to_db)
            )
        )
    }

    @After // Обязательное завершение работы сценария для активити после выполнения всех тестов
    fun close() {
        scenario.close()
    }
}