package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import org.koin.java.KoinJavaComponent.getKoin
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataWord
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.resources.ResourcesProviderImpl
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.sounds.playSound
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.ThemeColorsImpl
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.imageloader.GlideImageLoaderImpl

class WordViewHolder(
    private val mainAdapterTouch: MainAdapterTouch,
    view: View,
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
    // CurrentDataWord
    private var currentDataWord: DataWord? = null
    //endregion

    @SuppressLint("SetTextI18n")
    override fun bind(dataWord: DataWord, isEnglish: Boolean) {
        if (layoutPosition != RecyclerView.NO_POSITION) {
            // Сохранение dataWord
            currentDataWord = dataWord
            // Установка слова и его перевода
            if (isEnglish) {
                itemView.findViewById<TextView>(R.id.main_header_textview_recycler_item).text =
                    "${dataWord.word} ${dataWord.transcription}"
                itemView.findViewById<TextView>(R.id.main_description_textview_recycler_item).text =
                    dataWord.translation
            } else {
                itemView.findViewById<TextView>(R.id.main_header_textview_recycler_item).text =
                    dataWord.translation
                itemView.findViewById<TextView>(R.id.main_description_textview_recycler_item).text =
                    "${dataWord.word} ${dataWord.transcription}"
            }
            // Установка развёрнутого описания слова
            itemView.findViewById<TextView>(
                R.id.main_translations_textview_recycler_item
            ).text = dataWord.allMeanings
            // Установка пиктограммы слова
            glideImageLoaderImpl.loadInto(dataWord.linkImage,
                itemView.findViewById<ImageView>(R.id.main_word_pictogram))
            // Установка картинки для слова
            glideImageLoaderImpl.loadInto(
                dataWord.linkImage,
                itemView.findViewById<ImageView>(
                    R.id.main_translations_image_recycler_item
                )
            )
            // Установка слушателя при нажатии на контейнер со словом
            itemView.setOnClickListener {
                if (mainAdapterTouch.oldView == null) {
                    // Сохранение текущей View
                    mainAdapterTouch.oldView = itemView
                    mainAdapterTouch.positionOldView = adapterPosition
                    // Отображение дополнительной информации у элемента
                    showAdditionalInfoForElement(dataWord)
                    // Озвучивание слова в элементе
                    onListItemClickListener.playSoundClick(dataWord.linkSound)
                } else {
                    // Скрытие дополнительной информации у элемента
                    hideAdditionalInfoForElement()
                    // Проверка наличия не скрытого элемента c oldView
                    if (mainAdapterTouch.oldView != itemView) {
                        // Отображение дополнительной информации у элемента
                        showAdditionalInfoForElement(dataWord)
                        // Озвучивание слова в элементе
                        onListItemClickListener.playSoundClick(dataWord.linkSound)
                        // Сохранение текущей View
                        mainAdapterTouch.oldView = itemView
                        mainAdapterTouch.positionOldView = adapterPosition
                    } else {
                        // Обнуление данных о предыдущей вью,
                        // потому что пользователь просто закрыл только что открытую вью
                        mainAdapterTouch.oldView = null
                        mainAdapterTouch.positionOldView = null
                    }
                }
            }
            // Установка слушателя при нажатии на кнопку сохранения слова в базе данных
            itemView.findViewById<ImageView>(R.id.main_save_to_db).setOnClickListener {
                saveWordToDatabase(dataWord)
            }
        }
    }

    // Передача события клика на кнопку добавления данного слова в базу данных
    private fun saveWordToDatabase(dataWord: DataWord) {
        onListItemClickListener.onItemClick(dataWord)
    }

    //region МЕТОДЫ ДЛЯ РЕШЕНИЯ ВОПРОСА С ДУБЛИРОВАНИЕМ ОТОБРАЖЕНИЯ КАРТИНКИ В ЭЛЕМЕНТАХ (~ >10)
    override fun clearDuplicateAttach() {
        if (mainAdapterTouch.positionOldView == adapterPosition) {
            showAdditionalInfoForElement(currentDataWord)
        }
    }
    override fun clearDuplicateDetach() {
        if (mainAdapterTouch.positionOldView == adapterPosition) {
            // Скрытие дополнительной информации у элемента
            hideAdditionalInfoForElement()
        }
    }
    //endregion

    //region МЕТОДЫ ItemTouchHelperViewHolder ДЛЯ РАБОТЫ СО СМАХИВАНИЕМ (ВЫДЕЛЕНИЕ И ОЧИСТКА)
    override fun onItemSelected() {
        itemView.setBackgroundColor(themeColorsImpl.getColorPrimaryTypedValue())
    }
    override fun onItemClear() {
        itemView.setBackgroundColor(themeColorsImpl.getColorSurfaceTypedValue())
    }
    //endregion

    // Скрытие дополнительной информации у элемента
    private fun hideAdditionalInfoForElement() {
        // Скрытие развёрнутого описания слова у предыдущей ImageView
        mainAdapterTouch.oldView?.let {
            it.findViewById<TextView>(R.id.main_translations_textview_recycler_item)
                .visibility = View.GONE
        }
        // Восстановление нулевых размеров предыдущей ImageView для её скрытия
        constraintLayout = mainAdapterTouch.oldView?.let {
                it.findViewById<ConstraintLayout>(R.id.main_constraint_layout)
            }
        constraintSet.clone(constraintLayout)
        constraintSet.constrainWidth(R.id.main_translations_image_recycler_item, 0)
        constraintSet.constrainHeight(R.id.main_translations_image_recycler_item, 0)
        constraintSet.applyTo(constraintLayout)
    }

    private fun showAdditionalInfoForElement(dataWord: DataWord?) {
        // Отображение развёрнутого описания слова
        dataWord?.let { dataWord ->
            if ((dataWord.allMeanings != "") && (dataWord.allMeanings != dataWord.translation)) {
                itemView.findViewById<TextView>(
                    R.id.main_translations_textview_recycler_item
                ).visibility = View.VISIBLE
            }
        }
        // Изменение размеров ImageView картинки для её отображения
        constraintLayout = itemView.findViewById<ConstraintLayout>(R.id.main_constraint_layout)
        constraintSet.clone(constraintLayout)
        constraintSet.constrainWidth(R.id.main_translations_image_recycler_item,
            ConstraintSet.MATCH_CONSTRAINT)
        constraintSet.constrainHeight(R.id.main_translations_image_recycler_item,
            ConstraintSet.WRAP_CONTENT)
        constraintSet.applyTo(constraintLayout)
    }
}