package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils

import android.content.res.Resources
import android.util.TypedValue
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.R
import javax.inject.Inject

class ThemeColorsImpl @Inject constructor(): ThemeColors {
    //region ЗАДАНИЕ ПЕРЕМЕННЫХ
    private val colorSecondaryTypedValue: TypedValue = TypedValue()
    private val colorTypedValue: TypedValue = TypedValue()
    private val colorSecondaryVariantTypedValue: TypedValue = TypedValue()
    private val colorPrimaryVariantTypedValue: TypedValue = TypedValue()
    private val colorPrimaryTypedValue: TypedValue = TypedValue()
    //endregion

    override fun initiateColors(resourcesTheme: Resources.Theme) {
        resourcesTheme.resolveAttribute(
            R.attr.colorSecondary, colorSecondaryTypedValue, true
        )
        resourcesTheme.resolveAttribute(R.attr.color, colorTypedValue, true)
        resourcesTheme.resolveAttribute(
            R.attr.colorSecondaryVariant, colorSecondaryVariantTypedValue, true
        )
        resourcesTheme.resolveAttribute(
            R.attr.colorPrimaryVariant, colorPrimaryVariantTypedValue, true
        )
        resourcesTheme.resolveAttribute(
            R.attr.colorPrimary, colorPrimaryTypedValue, true
        )
    }

    //region МЕТОДЫ ПОЛУЧЕНИЯ ЦВЕТОВ ИЗ АТТРИБУТОВ ТЕМЫ
    fun getColorSecondaryTypedValue(): Int {
        return colorSecondaryTypedValue.data
    }

    fun getColorTypedValue(): Int {
        return colorTypedValue.data
    }

    fun getSecondaryVariantTypedValue(): Int {
        return colorSecondaryVariantTypedValue.data
    }

    fun getColorPrimaryVariantTypedValue(): Int {
        return colorPrimaryVariantTypedValue.data
    }

    fun getColorPrimaryTypedValue(): Int {
        return colorPrimaryTypedValue.data
    }
    //endregion
}