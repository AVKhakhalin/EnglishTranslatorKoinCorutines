package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.popular.libraries.model.data.DataWord

abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(data: DataWord, isEnglish: Boolean)

    abstract fun clearDuplicateAttach()
    abstract fun clearDuplicateDetach()
}