package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.adapter

import android.animation.ObjectAnimator
import android.opengl.Visibility
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import org.koin.java.KoinJavaComponent
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataWord
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.ThemeColorsImpl

class WordViewHolder(
    val view: View,
    val onListItemClickListener: OnListItemClickListener
): BaseViewHolder(view), ItemTouchHelperViewHolder {
    /** Задание переменных */ //region
    // ThemeColors
    private var themeColorsImpl: ThemeColorsImpl = KoinJavaComponent.getKoin().get()
    //endregion

    override fun bind(dataWord: DataWord, isEnglish: Boolean) {
        if (layoutPosition != RecyclerView.NO_POSITION) {
            if (isEnglish) {
                itemView.findViewById<TextView>(R.id.main_header_textview_recycler_item).text =
                    dataWord.word
                itemView.findViewById<TextView>(R.id.main_description_textview_recycler_item).text =
                    dataWord.translation
            } else {
                itemView.findViewById<TextView>(R.id.main_header_textview_recycler_item).text =
                    dataWord.translation
                itemView.findViewById<TextView>(R.id.main_description_textview_recycler_item).text =
                    dataWord.word
            }
            itemView.setOnClickListener {
                if (itemView.findViewById<Group>(R.id.main_translations_elements).visibility ==
                    View.VISIBLE) {
                    itemView.findViewById<Group>(R.id.main_translations_elements).visibility =
                        View.GONE
                } else {
                    itemView.findViewById<Group>(R.id.main_translations_elements).visibility =
                        View.VISIBLE
                }
                openInNewWindow(dataWord)
            }
            itemView.findViewById<Group>(R.id.main_translations_elements).visibility = View.GONE
        }
    }

    //region МЕТОДЫ ItemTouchHelperViewHolder ДЛЯ РАБОТЫ СО СМАХИВАНИЕМ (ВЫДЕЛЕНИЕ И ОЧИСТКА)
    override fun onItemSelected() {
        itemView.setBackgroundColor(themeColorsImpl.getColorPrimaryTypedValue())
    }
    override fun onItemClear() {
        itemView.setBackgroundColor(themeColorsImpl.getColorSurfaceTypedValue())
    }
    //endregion

    private fun openInNewWindow(dataWord: DataWord) {
        onListItemClickListener.onItemClick(dataWord)
    }
}