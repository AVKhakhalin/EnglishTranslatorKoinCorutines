package ru.geekbrains.popular.libraries.model.data

import com.google.gson.annotations.SerializedName

class Meanings(
    @field:SerializedName("translation") val translation: Translation?,
    @field:SerializedName("previewUrl") val previewUrl: String?,
    @field:SerializedName("imageUrl") val imageUrl: String?,
    @field:SerializedName("transcription") val transcription: String?,
    @field:SerializedName("soundUrl") val soundUrl: String?,
)