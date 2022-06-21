package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.imageloader

interface ImageLoader<T> {
    fun loadInto(url: String, container: T)
}