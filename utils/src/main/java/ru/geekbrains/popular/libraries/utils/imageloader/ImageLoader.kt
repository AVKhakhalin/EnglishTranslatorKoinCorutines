package ru.geekbrains.popular.libraries.utils.imageloader

interface ImageLoader<T> {
    fun loadInto(url: String, container: T)
}