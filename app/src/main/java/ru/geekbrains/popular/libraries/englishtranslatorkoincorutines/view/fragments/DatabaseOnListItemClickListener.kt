package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.fragments

import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataWord

interface DatabaseOnListItemClickListener {
    fun onItemClick(word: String)
}