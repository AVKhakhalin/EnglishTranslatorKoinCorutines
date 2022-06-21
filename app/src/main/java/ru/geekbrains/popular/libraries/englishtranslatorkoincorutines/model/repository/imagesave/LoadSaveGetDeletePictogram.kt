package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.imagesave

interface LoadSaveGetDeletePictogram {
    fun loadAndSaveImage(wordName: String, imageUrl: String)
    fun getImage(wordName: String, imageUrl: String): String?
    fun deleteImage(wordName: String)
}