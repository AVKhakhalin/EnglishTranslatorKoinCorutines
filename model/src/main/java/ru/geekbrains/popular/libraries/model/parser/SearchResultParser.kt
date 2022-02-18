package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils

import ru.geekbrains.popular.libraries.model.Constants
import ru.geekbrains.popular.libraries.model.data.*
import ru.geekbrains.popular.libraries.model.room.HistoryEntity
import java.util.regex.Matcher
import java.util.regex.Pattern

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
        for (meaning in dataModel.meanings!!) {
            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                newMeanings.add(
                    Meanings(
                    meaning.translation,
                    meaning.previewUrl,
                    meaning.imageUrl,
                    meaning.transcription,
                    meaning.soundUrl
                ))
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

fun mapHistoryEntityToSearchResult(
    word: String,
    list: List<HistoryEntity>,
    error_empty: String,
    begin_info: String,
    end_info: String
): List<DataModel> {
    val searchResult = ArrayList<DataModel>()
    if (!list.isNullOrEmpty()) {
        if (word == "") {
            searchResult.add(DataModel(error_empty, null))
        } else {
            for (entity in list) {
                if (word == "*") {
                    searchResult.add(
                        DataModel(
                        entity.word,
                        listOf<Meanings>(Meanings(
                            Translation(
                            "${entity.allMeanings}"),
                            "${entity.previewUrl}",
                            "${entity.imageUrl}",
                            "${entity.transcription}",
                            "${entity.soundUrl}"))
                    )
                    )
                } else if (entity.word.indexOf(word, 0) > -1) {
                    searchResult.add(
                        DataModel(
                        entity.word,
                        listOf<Meanings>(Meanings(
                        Translation(
                        "${entity.allMeanings}"),
                        "${entity.previewUrl}",
                        "${entity.imageUrl}",
                        "${entity.transcription}",
                        "${entity.soundUrl}"
                        ))
                    )
                    )
                }
            }
            if (searchResult.size == 0)
                searchResult.add(
                    DataModel(
                "$begin_info \"$word\".$end_info", null)
                )
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
            } else if (searchResult[0].meanings != null) {
                var allMeanings: String = ""
                searchResult[0].meanings?.let { meaningsList ->
                    meaningsList.forEachIndexed { index, meanings ->
                        // Отбрасываем первое значение, чтобы оно не дублировалось с meanings
                        allMeanings = "$allMeanings${meanings.translation?.translation}" +
                                if (index < meaningsList.count() - 1) ", " else ""
                    }
                    HistoryEntity("${searchResult[0].text}",
                        "${meaningsList[0].translation?.translation}",
                        convertToURI("${meaningsList[0].previewUrl}"),
                        convertToURI("${meaningsList[0].imageUrl}"),
                        allMeanings,
                        convertToTranscription("${meaningsList[0].transcription}"),
                        convertToURI("${meaningsList[0].soundUrl}")
                    )
                }
            } else {
                null
            }
        }
        else -> null
    }
}

fun convertToURI(stringData: String): String {
    return if (stringData.indexOf(Constants.HTTPS_BASE) == -1)
               "${Constants.HTTPS_BASE}$stringData"
           else
               stringData
}

fun convertToTranscription(stringData: String): String {
    return if (stringData.isNotEmpty()) {
                if (stringData[0].compareTo('[') == 0) stringData
                else "[$stringData]"
           }
           else ""
}

fun convertDataModelToDataWord(dataModel: List<DataModel>?): MutableList<DataWord> {
    var dataWord: MutableList<DataWord> = mutableListOf()
    dataModel?.let {
        it.forEach { it ->
            var allMeanings: String = ""
            it.meanings?.let { meaningsList ->
                meaningsList.forEachIndexed { index, meanings ->
                    allMeanings = "$allMeanings${meanings.translation?.translation}" +
                            if (index < meaningsList.count() - 1) ", " else ""
                }
            }
            dataWord.add(
                DataWord(
                "${it.text}",
                "${it.meanings?.get(0)?.translation?.translation}",
                "${Constants.HTTPS_BASE}${it.meanings?.get(0)?.previewUrl}",
                "${Constants.HTTPS_BASE}${it.meanings?.get(0)?.imageUrl}",
                convertToTranscription("${it.meanings?.get(0)?.transcription}"),
                "${Constants.HTTPS_BASE}${it.meanings?.get(0)?.soundUrl}",
                allMeanings)
            )
        }
    }
    return dataWord
}


fun convertDataWordToDataModel(dataWord: DataWord): MutableList<DataModel> {
    var dataModel: MutableList<DataModel> = mutableListOf()
    var meanings: MutableList<Meanings> = mutableListOf(
        Meanings(
            Translation(dataWord.translation),
            dataWord.linkPictogram,
            dataWord.linkImage,
            dataWord.transcription,
            dataWord.linkSound,
            )
    )
    val allMeanings: List<String> = dataWord.allMeanings.split(", ")

    allMeanings.forEachIndexed { index, meaning ->
        if ((meaning.indexOf(dataWord.translation) == -1) &&
            (meaning.length != dataWord.translation.length)) {
            meanings.add(
                Meanings(
                    Translation(allMeanings[index]),
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
    }
    dataModel.add(DataModel(dataWord.word, meanings))
    return dataModel
}

// Определение языка (английский - true, русский - false) вводимого слова
fun isEnglish(word: String): Boolean {
    /** Задание переменных */ //region
    // Переменные для распознавания языка вводимого слова для перевода
    val engPattern: Pattern = Pattern.compile(Constants.ENGLISH_SYMBOLS)
    var engMatcher: Matcher = engPattern.matcher("")
    //endregion

    engMatcher = engPattern.matcher(word)
    return engMatcher.find()
}