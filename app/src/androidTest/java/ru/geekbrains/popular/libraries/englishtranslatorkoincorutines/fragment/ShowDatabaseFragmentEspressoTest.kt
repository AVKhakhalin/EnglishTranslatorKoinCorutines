package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.fragment

import MIN_SDK_VALUE
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import com.google.android.material.button.MaterialButton
import junit.framework.TestCase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import playerURL
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments.ShowDatabaseFragment
import ru.geekbrains.popular.libraries.model.Settings.Settings

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = MIN_SDK_VALUE)
class ShowDatabaseFragmentEspressoTest {
    /** Задание переменных */ //region
    private lateinit var scenario: FragmentScenario<ShowDatabaseFragment>
    //endregion

    @Before
    fun setup() {
        // Запускаем ShowDatabaseFragment в корне Activity
        scenario = launchFragmentInContainer(themeResId = R.style.DayTheme)
    }

    @Test // Проверка корректности создания класса ShowDatabaseFragment
    fun activity_AssertNotNull() {
        scenario.onFragment(Assert::assertNotNull)
    }

    @Test // Проверка наличия элементов фрагмента на экране
    fun showDatabaseFragment_isExistsElements() {
        val scenario =
            launchFragmentInContainer<ShowDatabaseFragment>(themeResId = R.style.DayTheme)
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onFragment {
            val successFrameLayout =
                it.requireActivity().findViewById<FrameLayout>(R.id.success_linear_layout)
            TestCase.assertNotNull(successFrameLayout)
            val fragmentDatabaseRecyclerview =
                it.requireActivity().findViewById<RecyclerView>(R.id.fragment_database_recyclerview)
            TestCase.assertNotNull(fragmentDatabaseRecyclerview)
            val loadingFrameLayout =
                it.requireActivity().findViewById<FrameLayout>(R.id.loading_frame_layout)
            TestCase.assertNotNull(loadingFrameLayout)
            val progressBarHorizontal =
                it.requireActivity().findViewById<ProgressBar>(R.id.progress_bar_horizontal)
            TestCase.assertNotNull(progressBarHorizontal)
            val progressBarRound =
                it.requireActivity().findViewById<ProgressBar>(R.id.progress_bar_round)
            TestCase.assertNotNull(progressBarRound)
            val errorLinearLayout =
                it.requireActivity().findViewById<LinearLayout>(R.id.error_linear_layout)
            TestCase.assertNotNull(errorLinearLayout)
            val errorTextview =
                it.requireActivity().findViewById<TextView>(R.id.error_textview)
            TestCase.assertNotNull(errorTextview)
            val reloadButton =
                it.requireActivity().findViewById<MaterialButton>(R.id.reload_button)
            TestCase.assertNotNull(reloadButton)
        }
    }

    @Test // Проверка корректного отображения элементов фрагмента на экране
    fun showDatabaseFragment_isShowElements() {
        val scenario =
            launchFragmentInContainer<ShowDatabaseFragment>(themeResId = R.style.DayTheme)
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onFragment {
            val successFrameLayout =
                it.requireActivity().findViewById<FrameLayout>(R.id.success_linear_layout)
            TestCase.assertEquals(successFrameLayout.visibility, View.VISIBLE)
            val fragmentDatabaseRecyclerview =
                it.requireActivity().findViewById<RecyclerView>(R.id.fragment_database_recyclerview)
            TestCase.assertEquals(fragmentDatabaseRecyclerview.visibility, View.VISIBLE)
            val loadingFrameLayout =
                it.requireActivity().findViewById<FrameLayout>(R.id.loading_frame_layout)
            TestCase.assertEquals(loadingFrameLayout.visibility, View.GONE)
            val progressBarHorizontal =
                it.requireActivity().findViewById<ProgressBar>(R.id.progress_bar_horizontal)
            TestCase.assertEquals(progressBarHorizontal.visibility, View.GONE)
            val progressBarRound =
                it.requireActivity().findViewById<ProgressBar>(R.id.progress_bar_round)
            TestCase.assertEquals(progressBarRound.visibility, View.VISIBLE)
            val errorLinearLayout =
                it.requireActivity().findViewById<LinearLayout>(R.id.error_linear_layout)
            TestCase.assertEquals(errorLinearLayout.visibility, View.GONE)
            val errorTextview =
                it.requireActivity().findViewById<TextView>(R.id.error_textview)
            TestCase.assertEquals(errorTextview.visibility, View.VISIBLE)
            val reloadButton =
                it.requireActivity().findViewById<MaterialButton>(R.id.reload_button)
            TestCase.assertEquals(reloadButton.visibility, View.VISIBLE)
        }
    }

    @Test // Проверка корректности загрузки настроек приложения
    fun showDatabaseFragment_CorrectSettingsLoad() {
        val scenario =
            launchFragmentInContainer<ShowDatabaseFragment>(themeResId = R.style.DayTheme)
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onFragment {
            val previousRequest: Settings = it.model.loadSettings()
            Assert.assertEquals(previousRequest.requestedWord, "")
            Assert.assertEquals(previousRequest.isDatabaseShow, true)
            Assert.assertEquals(previousRequest.isMain, true)
            Assert.assertEquals(previousRequest.isThemeDay, true)
        }
    }

    @Test // Проверка корректного воспроизведения звука
    fun showDatabaseFragment_PlayerCheck() {
        val scenario =
            launchFragmentInContainer<ShowDatabaseFragment>(themeResId = R.style.DayTheme)
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onFragment {
            it.model.
            playSoundWord(playerURL)
        }
    }

    @After // Необязательное завершение работы сценария после выполнения всех тестов
    fun close() {
        scenario.close()
    }
}