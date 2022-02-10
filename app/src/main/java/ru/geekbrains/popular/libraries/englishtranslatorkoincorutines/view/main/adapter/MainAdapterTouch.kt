package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataWord

class MainAdapterTouch (
    private val onListItemClickListener: OnListItemClickListener,
    private var dataWord: MutableList<DataWord>,
    // Признак перевода английского слова (true - английское слово; false - русское слово)
    private var isEnglish: Boolean
): RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {
    /** Задание переменных */ //region
    // View, по которой кликнули в предыдущий раз
    var oldView: View? = null
    var positionOldView: Int? = null
    //endregion

    fun setData(dataWord: MutableList<DataWord>, isEnglish: Boolean) {
        this.isEnglish = isEnglish
        this.dataWord = dataWord
        notifyDataSetChanged()
    }

    //region БАЗОВЫЕ МЕТОДЫ ДЛЯ РАБОТЫ АДАПТЕРА
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return WordViewHolder(
            this,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false)
                    as View,
            onListItemClickListener
            )
        }
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        (holder).bind(dataWord[position], isEnglish)
    }
    override fun getItemCount(): Int {
        return dataWord.size
    }
    //endregion

    //region МЕТОДЫ ДЛЯ ОТСЛЕЖИВАНИЯ ПРИКРЕПЛЕНИЯ ЭЛЕМЕНТА К ОКНУ ИЛИ ЕГО ОТКРЕПЛЕНИЯ ОТ ОКНА
    // Этими методами решается проблема отображения одной и той же картики на элементах,
    // првышающих максимальное количество элементов, отображаемых на экране (~ > 10)
    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (oldView != null) {
            holder.clearDuplicateAttach()
        }
    }
    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (oldView != null) {
            holder.clearDuplicateDetach()
        }
    }
    //endregion

    //region БАЗОВЫЕ МЕТОДЫ ДЛЯ РЕАЛИЗАЦИИ СМАХИВАНИЯ (УДАЛЕНИЯ) ЭЛЕМЕНТОВ
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        dataWord.removeAt(fromPosition).apply {
            dataWord.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }
    override fun onItemDismiss(position: Int) {
        dataWord.removeAt(position)
        notifyItemRemoved(position)
    }
    //endregion

    //region МЕТОД И КЛАСС ДЛЯ ДИНАМИЧЕСКОГО ОБНОВЛЕНИЯ СПИСКА
    // НАСТРОЕН, НО НЕ ИСПОЛЬЗУЕТСЯ
    fun submitList(newDataWord: List<DataWord>) {
        val oldDataWord: List<DataWord> = dataWord
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(DiffCallback(oldDataWord, newDataWord))
        dataWord.clear()
        newDataWord.forEach {
            dataWord.add(it)
        }
        diffResult.dispatchUpdatesTo(this)
    }
    class DiffCallback(
        var oldList: List<DataWord>,
        var newList: List<DataWord>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }
        override fun getNewListSize(): Int {
            return newList.size
        }
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
    //endregion


}