package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import org.koin.java.KoinJavaComponent.getKoin
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataWord
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.ThemeColorsImpl
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.imageloader.GlideImageLoaderImpl

class WordViewHolder(
    private val mainAdapterTouch: MainAdapterTouch,
    private val view: View,
    private val onListItemClickListener: OnListItemClickListener
): BaseViewHolder(view), ItemTouchHelperViewHolder {
    /** Задание переменных */ //region
    // ThemeColors
    private var themeColorsImpl: ThemeColorsImpl = getKoin().get()
    // GlideImageLoaderImpl
    private val glideImageLoaderImpl: GlideImageLoaderImpl = getKoin().get()
    // ConstraintLayout
    private var constraintLayout: ConstraintLayout? = null
    private val constraintSet: ConstraintSet = ConstraintSet()
    //endregion

    override fun bind(dataWord: DataWord, isEnglish: Boolean) {
        if (layoutPosition != RecyclerView.NO_POSITION) {
            // Установка слова и его перевода
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
            // Установка пиктограммы слова
            glideImageLoaderImpl.loadInto(dataWord.linkImage,
                itemView.findViewById<ImageView>(R.id.main_word_pictogram))
            // Установка слушателя при нажатии на контейнер со словом
            itemView.setOnClickListener {
                if (mainAdapterTouch.oldView == null) {
                    // Сохранение текущей View
                    mainAdapterTouch.oldView = itemView
                    // Отображение развёрнутого описания слова
                    if (dataWord.allMeanings != "") {
                        itemView.findViewById<TextView>(
                            R.id.main_translations_textview_recycler_item
                        ).visibility = View.VISIBLE
                        itemView.findViewById<TextView>(
                            R.id.main_translations_textview_recycler_item
                        ).setText(dataWord.allMeanings)
                    }
                    // Установка картинки для слова
                    glideImageLoaderImpl.loadInto(dataWord.linkImage,
                        itemView.findViewById<ImageView>(
                            R.id.main_translations_image_recycler_item))
                    // Изменение размеров ImageView картинки для её отображения
                    constraintLayout =
                        itemView.findViewById<ConstraintLayout>(R.id.main_constraint_layout)
                    constraintSet.clone(constraintLayout)
                    constraintSet.constrainWidth(R.id.main_translations_image_recycler_item,
                        ConstraintSet.MATCH_CONSTRAINT)
                    constraintSet.constrainHeight(R.id.main_translations_image_recycler_item,
                        ConstraintSet.WRAP_CONTENT)
                    constraintSet.applyTo(constraintLayout)
                    Toast.makeText(view.context, "$adapterPosition", Toast.LENGTH_SHORT).show()
                } else {
                    // Скрытие развёрнутого описания слова у предыдущей ImageView
                    mainAdapterTouch.oldView?.let {
                        it.findViewById<TextView>(R.id.main_translations_textview_recycler_item)
                            .visibility = View.GONE
                    }
                    // Восстановление нулевых размеров предыдущей ImageView для её скрытия
                    constraintLayout =
                        mainAdapterTouch.oldView?.let {
                            it.findViewById<ConstraintLayout>(R.id.main_constraint_layout)
                        }
                    constraintSet.clone(constraintLayout)
                    constraintSet.constrainWidth(R.id.main_translations_image_recycler_item,
                        0)
                    constraintSet.constrainHeight(R.id.main_translations_image_recycler_item,
                        0)
                    constraintSet.applyTo(constraintLayout)

                    if (mainAdapterTouch.oldView != itemView) {
                        // Отображение развёрнутого описания слова
                        if (dataWord.allMeanings != "") {
                            itemView.findViewById<TextView>(
                                R.id.main_translations_textview_recycler_item
                            ).visibility = View.VISIBLE
                            itemView.findViewById<TextView>(
                                R.id.main_translations_textview_recycler_item
                            ).setText(dataWord.allMeanings)
                        }
                        // Установка картинки для слова
                        glideImageLoaderImpl.loadInto(
                            dataWord.linkImage,
                            itemView.findViewById<ImageView>(
                                R.id.main_translations_image_recycler_item
                            )
                        )
                        // Изменение размеров ImageView картинки для её отображения
                        constraintLayout =
                            itemView.findViewById<ConstraintLayout>(R.id.main_constraint_layout)
                        val constraintSet = ConstraintSet()
                        constraintSet.clone(constraintLayout)
                        constraintSet.constrainWidth(R.id.main_translations_image_recycler_item,
                            ConstraintSet.MATCH_CONSTRAINT
                        )
                        constraintSet.constrainHeight(R.id.main_translations_image_recycler_item,
                            ConstraintSet.WRAP_CONTENT
                        )
                        constraintSet.applyTo(constraintLayout)
                        // Сохранение текущей View
                        mainAdapterTouch.oldView = itemView
                    } else {
                        // Обнуление данных о предыдущей вью,
                        // потому что пользователь просто закрыл только что открытую вью
                        mainAdapterTouch.oldView = null
                    }
                    Toast.makeText(view.context, "Else: $adapterPosition",
                        Toast.LENGTH_SHORT).show()
                }
//                openInNewWindow(dataWord)
            }
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