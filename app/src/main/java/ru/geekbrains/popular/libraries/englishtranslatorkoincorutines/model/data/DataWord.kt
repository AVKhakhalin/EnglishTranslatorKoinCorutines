package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data

class DataWord() {
    /** Задание переменных */ //region
    // Слово для перевода
    var word: String = ""
    // Перевод слова
    var translation: String = ""
    // Ссылка на пиктограмму с уменьшенным изображением значения слова
    var linkPictogram: String = ""
    // Ссылка на картинку с изображением значения слова
    var linkImage: String = ""
    // Ссылка на транскрипцию слова
    var linkTranscription: String = ""
    // Ссылка на аудио-файл с произношением слова
    var linkSound: String = ""
    //endregion

    // Второй конструктор класса
    // ! Нужно обязательно оставить скобочки у первого пустого конструктора
    constructor(
        word: String,
        translation: String,
        linkPictogram: String,
        linkImage: String,
        linkTranscription: String,
        linkSound: String
    ): this() {
        this.word = word
        this.translation = translation
        this.linkPictogram = linkPictogram
        this.linkImage = linkImage
        this.linkTranscription = linkTranscription
        this.linkSound = linkSound
    }

    //region Установка правила сравнения двух классов Favorite
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }
        other as DataWord
        if (word != other.word) {
            return false
        }
        if (linkSound != other.linkSound) {
            return false
        }
        if (linkPictogram != other.linkPictogram) {
            return false
        }
        if (linkImage != other.linkImage) {
            return false
        }
        if (translation != other.translation) {
            return false
        }
        if (linkTranscription != other.linkTranscription) {
            return false
        }
        return true
    }
    //endregion
}