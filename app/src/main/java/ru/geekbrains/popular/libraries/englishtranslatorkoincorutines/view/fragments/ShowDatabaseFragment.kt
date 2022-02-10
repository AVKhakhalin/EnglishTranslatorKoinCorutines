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
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.databinding.FragmentDatabaseWordsBinding
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataWord
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments.adapter.DatabaseAdapter

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
            throw IllegalStateException("The ViewModel should be initialised first")
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
                        Toast.makeText(requireContext(), "В базе данных нет данных",
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
                    binding.progressBarHorizontal.progress = appState.progress
                } else {
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.progressBarRound.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                Toast.makeText(requireContext(), "В процессе загрузки данных возникла ошибка",
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

    // Удаление элемента из базы данных
    override fun onItemClick(word: String) {
        model.deleteDataByWord(word)
    }

    fun getData(word: String) {
        // Получение данных из базы данных
        model.getData(word)
    }
}