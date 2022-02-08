package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils

import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.AppState
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataWord
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.Meanings
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.room.HistoryEntity

fun parseSearchResults(state: AppState): AppState {
    val newSearchResults = arrayListOf<DataModel>()
    var isEnglish = false
    when (state) {
        is AppState.Success -> {
            val searchResults = state.data
            isEnglish = state.isEnglish
            if (!searchResults.isNullOrEmpty()) {
                for (searchResult in searchResults) {
                    parseResult(searchResult, newSearchResults)
                }
            }
        }
    }

    return AppState.Success(newSearchResults, isEnglish)
}

private fun parseResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings) {
            if (meaning.translation != null && !meaning.translation.translation.isNullOrBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.previewUrl, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataModel(dataModel.text, newMeanings))
        }
    }
}

fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
        }
    }
    return meaningsSeparatedByComma
}

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<DataModel> {
    val searchResult = ArrayList<DataModel>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(DataModel(entity.word, null))
        }
    }
    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                HistoryEntity("${searchResult[0].text}",
                    "${searchResult[0].meanings!![0].translation}",
                    "${searchResult[0].meanings!![0].previewUrl}",
                    "${searchResult[0].meanings!![0].imageUrl}"
                )
            }
        }
        else -> null
    }
}

fun convertDataModelToDataWord(dataModel: List<DataModel>?): MutableList<DataWord> {
    var dataWord: MutableList<DataWord> = mutableListOf()
    dataModel?.let {
        it.forEach { it ->
            var allMeanings: String = ""
            it.meanings?.let { meaningsList ->
                meaningsList.forEachIndexed { index, meanings ->
                    if (index > 0) {
                        allMeanings = if (index < meaningsList.count() - 1)
                            "$allMeanings${meanings.translation?.translation}, " else
                            "$allMeanings${meanings.translation?.translation}"
                    }
                }
            }
            dataWord.add(
                DataWord(
                "${it.text}",
                "${it.meanings?.get(0)?.translation?.translation}",
                "https:${it.meanings?.get(0)?.previewUrl}",
                "https:${it.meanings?.get(0)?.imageUrl}",
                "",
                "",
                allMeanings)
            )
        }
    }
    return dataWord
}