package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.koin.java.KoinJavaComponent.getKoin
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.resources.ResourcesProviderImpl
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments.DatabaseOnListItemClickListener
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments.ShowDatabaseViewModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.adapter.OnListItemClickListener

class DatabaseAdapter(
    private val databaseOnListItemClickListener: DatabaseOnListItemClickListener
): RecyclerView.Adapter<DatabaseAdapter.RecyclerItemViewHolder>() {
    /** Задание переменных */ //region
    // DataModel
    private var dataModel: MutableList<DataModel> = mutableListOf()
    // ResourcesProviderImpl
    private val resourcesProviderImpl: ResourcesProviderImpl = getKoin().get()
    //endregion


    fun setData(dataModel: List<DataModel>) {
        dataModel.forEach {
            this.dataModel.add(it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.database_word_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(dataModel[position])
    }

    override fun getItemCount(): Int {
        return dataModel.size
    }

    inner class RecyclerItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.database_header_textview_recycler_item).text =
                    data.text
                data.meanings?.let { meanings ->
                    itemView.findViewById<TextView>(
                        R.id.database_description_textview_recycler_item).text =
                        meanings[0].translation?.translation
//                    itemView.findViewById<TextView>(
//                        R.id.database_translations_textview_recycler_item).text =
//                        meanings[0].translation?.translation
                    itemView.findViewById<ImageView>(R.id.database_main_delete_from_db)
                        .setOnClickListener {
                        Toast.makeText(itemView.context, "${
                            resourcesProviderImpl.getString(R.string.delete_word_info_begin)} \"${
                                data.text}\" ${
                            resourcesProviderImpl.getString(R.string.delete_word_info_end)}",
                            Toast.LENGTH_SHORT).show()
                        // Удаление выбранного слова из базы данных
                        databaseOnListItemClickListener.onItemClick("${data.text}")
                        // Удаление выбранного слова из адаптера
                        dataModel.remove(data)
                        // Скрытие удалённого элемента в списке
                        notifyItemRemoved(adapterPosition)
                    }
                }
            }
        }
    }
}