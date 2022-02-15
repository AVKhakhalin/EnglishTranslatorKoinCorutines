package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.getKoin
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.databinding.FragmentDatabaseWordsBinding
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments.adapter.DatabaseAdapter
import ru.geekbrains.popular.libraries.model.data.AppState
import ru.geekbrains.popular.libraries.model.data.DataModel
import ru.geekbrains.popular.libraries.utils.resources.ResourcesProviderImpl

class ShowDatabaseFragment(
    private val word: String
): Fragment(), DatabaseOnListItemClickListener {
    /** Задание переменных */ //region
    // Binding
    private var _binding: FragmentDatabaseWordsBinding? = null
    private val binding: FragmentDatabaseWordsBinding
        get() {
            return _binding!!
        }
    // ShowDatabaseViewModel
    lateinit var model: ShowDatabaseViewModel
    // DatabaseAdapter
    private val databaseAdapter: DatabaseAdapter by lazy {
        DatabaseAdapter(this@ShowDatabaseFragment)
    }
    // ResourcesProviderImpl
    private val resourcesProviderImpl: ResourcesProviderImpl = getKoin().get()
    //endregion


    companion object {
        fun newInstance(word: String): ShowDatabaseFragment = ShowDatabaseFragment(word)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Установка списка заметок к отображению и реагированию на события
        _binding = FragmentDatabaseWordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ShowDatabaseViewModel
        initViewModel()
        // Инициализация адаптера
        initViews()
    }

    private fun initViewModel() {
        if (binding.fragmentDatabaseRecyclerview.adapter != null) {
            throw IllegalStateException(
                "${resourcesProviderImpl.getString(R.string.error_textview_stub)}: ${
                    resourcesProviderImpl.getString(R.string.viewmodel_error)}")
        }
        val viewModel: ShowDatabaseViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@ShowDatabaseFragment,
            Observer<AppState> { renderData(it) })
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        Toast.makeText(requireContext(), "${
                            resourcesProviderImpl.getString(R.string.error_textview_stub)}: ${
                            resourcesProviderImpl.getString(R.string.empty_database_error)}",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = View.VISIBLE
                    binding.progressBarRound.visibility = View.GONE
                    binding.progressBarHorizontal.progress =
                        if (appState.progress == null) 0 else appState.progress!!
                } else {
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.progressBarRound.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                Toast.makeText(requireContext(), "${
                    resourcesProviderImpl.getString(R.string.error_textview_stub)}: ${
                    resourcesProviderImpl.getString(R.string.loading_dates_error)}",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showViewWorking() {
        binding.loadingFrameLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.loadingFrameLayout.visibility = View.VISIBLE
    }

    private fun setDataToAdapter(data: List<DataModel>) {
        databaseAdapter.setData(data)
    }

    private fun initViews() {
        // Настройка списка
        binding.fragmentDatabaseRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.fragmentDatabaseRecyclerview.adapter = databaseAdapter
        // Получение данных из базы данных
        getData(word)
    }

    // Очистка Binding при уничтожении фрагмента
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // Удаление слова из базы данных
    override fun deleteItemClick(word: String) {
        model.deleteDataByWord(word)
    }

    // Озвучивание слова
    override fun playSoundClick(soundUrl: String) {
        model.playSoundWord(soundUrl)
    }

    // Получение данных из базы данных
    fun getData(word: String) {
        model.getData(word)
    }
}