package ru.geekbrains.popular.libraries.utils.sounds

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

// Озвучивание произношения слова
fun playSound(uriString: String, context: Context) {
    val snd = MediaPlayer()
    with(snd) {
        val fl = Uri.parse(uriString)
        setDataSource(context, fl)
        prepare()
        start()
        setOnCompletionListener { release() }
    }
}