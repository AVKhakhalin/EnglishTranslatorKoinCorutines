package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.databinding.FragmentDatabaseWordsBinding
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments.adapter.DatabaseAdapter
import ru.geekbrains.popular.libraries.model.Constants
import ru.geekbrains.popular.libraries.model.data.AppState
import ru.geekbrains.popular.libraries.model.data.DataModel
import ru.geekbrains.popular.libraries.utils.resources.ResourcesProviderImpl
import ru.geekbrains.popular.libraries.utils.view.viewById

class ShowDatabaseFragment: Fragment(), DatabaseOnListItemClickListener {
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
    // FragmentDatabaseRecyclerview
    private val fragmentDatabaseRecyclerview by viewById<RecyclerView>(
        R.id.fragment_database_recyclerview, getKoin().get())
    // ResourcesProviderImpl
    private val resourcesProviderImpl: ResourcesProviderImpl = getKoin().get()
    // ShowDatabaseFragmentScope
    lateinit var showDatabaseFragmentScope: Scope
    //endregion


    companion object {
        fun newInstance(): ShowDatabaseFragment = ShowDatabaseFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // РАБОЧИЙ АЛГОРИТМ
        showDatabaseFragmentScope = getKoin().getOrCreateScope(
                Constants.SHOW_DATABASE_FRAGMENT_SCOPE,
                named(Constants.SHOW_DATABASE_FRAGMENT_SCOPE))
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

    override fun onDetach() {
        // Удаление скоупа для данного фрагмента
        showDatabaseFragmentScope.close()
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ShowDatabaseViewModel
        initViewModel()
        // Инициализация адаптера
        initViews()
    }

    private fun initViewModel() {
        if (fragmentDatabaseRecyclerview.adapter != null) {
            throw IllegalStateException(
                "${resourcesProviderImpl.getString(R.string.error_textview_stub)}: ${
                    resourcesProviderImpl.getString(R.string.viewmodel_error)}")
        }
        val viewModel: ShowDatabaseViewModel by showDatabaseFragmentScope.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner,
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
        fragmentDatabaseRecyclerview.layoutManager = LinearLayoutManager(context)
        fragmentDatabaseRecyclerview.adapter = databaseAdapter
    }

    // Получение данных из базы данных
    fun setWord(word: String) {
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