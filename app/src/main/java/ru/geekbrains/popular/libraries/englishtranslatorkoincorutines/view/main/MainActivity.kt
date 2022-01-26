package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main

import android.animation.ObjectAnimator
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application.Constants
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application.TranslatorApp
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.databinding.ActivityMainBinding
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.base.BaseActivity
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.adapter.MainAdapter
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.ThemeColorsImpl
import javax.inject.Inject


class MainActivity: BaseActivity<AppState, MainInteractor>() {
    /** Задание переменных */ //region
    // Binding
    private lateinit var binding: ActivityMainBinding
    // MainAdapter
    private var adapter: MainAdapter? = null
    // Bottom navigation menu (признако основного состояния Main State - когда можно вводить слова)
    private var isMain: Boolean = true
    // Установка темы приложения
    private var isThemeDay: Boolean = true
    // Событие: клик по элементу списка с найденными словами
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object: MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }
    // ViewModel
    override lateinit var model: MainViewModel
    // ThemeColors
    @Inject
    lateinit var themeColorsImpl: ThemeColorsImpl
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Запуск Dagger
        TranslatorApp.instance.component.inject(this)
        // Считывание системных настроек, применение темы к приложению
        readSettingsAndSetupApplication(savedInstanceState)
        // Установка binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Начальная установка ViewModel
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        // Подписка на ViewModel
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })

        // Начальная установка доступности поискового поля
        switchBottomAppBar()
        // Установка события нажатия на нижниюю FAB для открытия и закрытия поискового элемента
        binding.bottomNavigationMenu.bottomAppBarFab.setOnClickListener {
            switchBottomAppBar()
        }
        // Получение текущих цветов поля
        themeColorsImpl.initiateColors(theme)
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                val dataModel = appState.data
                val isEnglish: Boolean = appState.isEnglish
                if (dataModel == null || dataModel.isEmpty()) {
                    Toast.makeText(
                        this, getString(R.string.empty_server_response_on_success),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (adapter == null) {
                        binding.mainActivityRecyclerview.layoutManager =
                            LinearLayoutManager(applicationContext)
                        binding.mainActivityRecyclerview.adapter =
                            MainAdapter(onListItemClickListener, dataModel, isEnglish)
                    } else {
                        adapter?.let {
                            it.setData(dataModel, isEnglish)
                        }
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = VISIBLE
                    binding.progressBarRound.visibility = GONE
                    binding.progressBarHorizontal.progress = appState.progress
                } else {
                    binding.progressBarHorizontal.visibility = GONE
                    binding.progressBarRound.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                Toast.makeText(this, appState.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showViewWorking() {
        binding.loadingFrameLayout.visibility = GONE
    }

    private fun showViewLoading() {
        binding.loadingFrameLayout.visibility = VISIBLE
    }

    // Переключение режима нижней навигационной кнопки BottomAppBar
    // с центрального на крайнее правое положение и обратно
    private fun switchBottomAppBar() {
        if (isMain) {
            // Анимация вращения картинки на нижней кнопке FAB
            ObjectAnimator.ofFloat(
                binding.bottomNavigationMenu.bottomAppBarFab,
                "rotation", 0f, -Constants.ANGLE_TO_ROTATE_BOTTOM_FAB
            ).start()
            // Изменение нижней кнопки FAB
            isMain = false
            binding.bottomNavigationMenu.bottomAppBar.navigationIcon = null
            binding.bottomNavigationMenu.bottomAppBar.fabAlignmentMode =
                BottomAppBar.FAB_ALIGNMENT_MODE_END
            binding.bottomNavigationMenu.bottomAppBarFab.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_back_fab)
            )
            binding.bottomNavigationMenu.bottomAppBar.replaceMenu(
                R.menu.bottom_menu_bottom_bar_other_screen
            )

            //region НАСТРОЙКИ ПОИСКОВОГО ПОЛЯ
            // Установка поискового поля
            val searchViewActionView: android.view.View = binding.bottomNavigationMenu.bottomAppBar
                .menu.findItem(R.id.action_bottom_bar_search_request_form).actionView
            val searchView: SearchView = searchViewActionView as SearchView
            // Установка ранее заданного слова
            // TODO
            // Событие установки поискового запроса
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    model.getData(query)
                    return false
                }

                // Отслеживание появления каждого символа
                override fun onQueryTextChange(newText: String): Boolean {
                    // Отображение текущего поискового запроса
                    // TODO
                    return false
                }
            })

            // Событие на закрытие поискового окна (обнуление фильтра)
            searchView.setOnCloseListener {
                // TODO
//                Toast.makeText(this@MainActivity, "Close", Toast.LENGTH_SHORT).show()
                true
            }
            // Получение поискового поля для ввода и редактирования текста поискового
            val searchedEditText =
                searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            // Установка цветов фона и текста для поискового поля
            searchedEditText.setBackgroundResource(R.drawable.search_view_shape)
            searchedEditText.setTextColor(themeColorsImpl.getColorTypedValue())
            searchedEditText.setHintTextColor(themeColorsImpl.getColorTypedValue())
            // Установка размера поискового текста
            searchedEditText.textSize = Constants.SEARCH_FIELD_TEXT_SIZE
            // Открытие поля для ввода текста
            searchView.isIconified = false
            // Анимированное появление поля для ввода текста
            ObjectAnimator.ofFloat(searchView, "alpha", 0F, 1F)
                .setDuration(1500).start()
            //endregion
        } else {
            // Закрытие поля для ввода текста
//            val searchViewActionView: android.view.View = binding.bottomNavigationMenu.bottomAppBar
//                .menu.findItem(R.id.action_bottom_bar_search_request_form).actionView
//            val searchView: SearchView = searchViewActionView as SearchView
//            searchView.onActionViewCollapsed()
//            // Скрытие поля для ввода текста
//            searchView.visibility = android.view.View.INVISIBLE

            // Анимация вращения картинки на нижней кнопке FAB
            ObjectAnimator.ofFloat(
                binding.bottomNavigationMenu.bottomAppBarFab,
                "rotation", 0f, Constants.ANGLE_TO_ROTATE_BOTTOM_FAB
            ).start()
            // Изменение нижней кнопки FAB
            isMain = true
            binding.bottomNavigationMenu.bottomAppBar.navigationIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_hamburger_menu_bottom_bar)
            binding.bottomNavigationMenu.bottomAppBar.fabAlignmentMode =
                BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            binding.bottomNavigationMenu.bottomAppBarFab.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_plus_fab)
            )
            // Появление меню с настройками приложения
            binding.bottomNavigationMenu.bottomAppBar
                .replaceMenu(R.menu.bottom_menu_navigation)
            // Установка события смены кнопки
            binding.bottomNavigationMenu.bottomAppBar.menu
                .getItem(Constants.BUTTON_CHANGE_THEME_INDEX).setOnMenuItemClickListener {
                    isMain = false
                    changeTheme()
                    true
                }
            // Анимированное появление кнопки меню со сменой темы
            // TODO
//            ObjectAnimator.ofFloat(binding.bottomNavigationMenu.bottomAppBar.menu
//                .findItem(R.id.action_change_theme).actionView, "alpha", 0F, 1F)
//                .setDuration(1500).start()
        }
    }

    // Считывание системных настроек, применение темы к приложению
    private fun readSettingsAndSetupApplication(savedInstanceState: Bundle?) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE)
        isMain = sharedPreferences.getBoolean(
            Constants.SHARED_PREFERENCES_MAIN_STATE_KEY, true
        )
        if (savedInstanceState != null) {
            isThemeDay = sharedPreferences.getBoolean(
                Constants.SHARED_PREFERENCES_THEME_KEY, true
            )
            if (isThemeDay) {
                setTheme(R.style.DayTheme)
            } else {
                setTheme(R.style.NightTheme)
            }
        } else {
            // Применение тёмной темы при первом запуске приложения
            // на мобильных устройствах с версией Android 10+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                isThemeDay = false
                setTheme(R.style.NightTheme)
            }
        }
    }

    // Установка темы приложения
    private fun changeTheme() {
        isThemeDay = !isThemeDay
        saveApplicationSettings()
        recreate()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        saveApplicationSettings()
    }

    override fun onPause() {
        super.onPause()
        saveApplicationSettings()
    }

    // Сохранение настроек приложения
    private fun saveApplicationSettings() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, AppCompatActivity.MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putBoolean(Constants.SHARED_PREFERENCES_THEME_KEY, isThemeDay)
        sharedPreferencesEditor.putBoolean(Constants.SHARED_PREFERENCES_MAIN_STATE_KEY, isMain)
        sharedPreferencesEditor.apply()
    }
}