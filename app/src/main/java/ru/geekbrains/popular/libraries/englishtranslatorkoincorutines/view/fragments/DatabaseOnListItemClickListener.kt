package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments

interface DatabaseOnListItemClickListener {
    fun deleteItemClick(word: String)
    fun playSoundClick(soundUrl: String)
}