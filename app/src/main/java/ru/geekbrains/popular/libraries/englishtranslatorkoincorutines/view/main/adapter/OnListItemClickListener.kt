package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.main.adapter

import ru.geekbrains.popular.libraries.model.data.DataWord

interface OnListItemClickListener {
    fun onItemClick(data: DataWord)
    fun playSoundClick(soundUrl: String)
}