package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = arrayOf(Index(value = arrayOf("word"), unique = true)))
class HistoryEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "word")
    var word: String,
    @field:ColumnInfo(name = "description")
    var description: String?,
    @field:ColumnInfo(name = "previewUrl")
    var previewUrl: String?,
    @field:ColumnInfo(name = "imageUrl")
    var imageUrl: String?,
)