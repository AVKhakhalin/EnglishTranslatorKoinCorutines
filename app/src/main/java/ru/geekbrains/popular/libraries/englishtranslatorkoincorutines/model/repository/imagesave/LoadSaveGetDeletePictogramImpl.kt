package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.repository.imagesave

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.application.Constants
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.network.NetworkStatus
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.utils.resources.ResourcesProviderImpl
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class LoadSaveGetDeletePictogramImpl: LoadSaveGetDeletePictogram {
    /** Задание переменных */ //region
    // ResourcesProviderImpl
    private val resourcesProviderImpl: ResourcesProviderImpl = getKoin().get()
    // NetworkStatus
    private val networkStatus: NetworkStatus = getKoin().get()
    // File
    private var file: File = File("")
    //endregion

    override fun loadAndSaveImage(wordName: String, imageUrl: String) {
        if (networkStatus.isOnline()) {
            file = File(
                "${
                    resourcesProviderImpl.context.getExternalFilesDir(
                        Environment.DIRECTORY_PICTURES)}${Constants.CACHE_FOLDER_NAME}$wordName"
            )

            /** Сохранение картинки в локальную папку с данным приложением */
            /** Создание директории, если она ещё не создана */
            if (!file.exists()) {
                file.mkdirs()
            }
            file = File(file, "$wordName${Constants.CACHE_FILE_EXTENSION}")
            /** Создание файла */
            file.createNewFile()

            CoroutineScope(Dispatchers.IO).launch {
                saveImage(
                    Glide.with(resourcesProviderImpl.context)
                        .asBitmap()
                        .load(imageUrl)
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                        .error(android.R.drawable.stat_notify_error)
                        .submit()
                        .get(), file
                )
            }
        }
    }

    /** Сохранение картинки */
    private fun saveImage(image: Bitmap, outPutFile: File) {
        try {
            val fOut: OutputStream = FileOutputStream(outPutFile)
            image.compress(Bitmap.CompressFormat.JPEG, 40, fOut)
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /** Получение ссылки на картинку в папке приложения */
    override fun getImage(wordName: String, imageUrl: String): String? {
        file = File(
            "${
                resourcesProviderImpl.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            }${Constants.CACHE_FOLDER_NAME}$wordName"
        )
        file = File(file, "$wordName${Constants.CACHE_FILE_EXTENSION}")
        return if ((file.exists()) && (file.length() > 0))
            file.toString()
        else {
            loadAndSaveImage(wordName, imageUrl)
            file = File(
                "${
                    resourcesProviderImpl.context.getExternalFilesDir(
                        Environment.DIRECTORY_PICTURES)}${Constants.CACHE_FOLDER_NAME}$wordName"
            )
            file = File(file, "$wordName${Constants.CACHE_FILE_EXTENSION}")
            if ((file.exists()) && (file.length() > 0))
                file.toString()
            else
                null
        }
    }

    // Удаление картинки из кеша
    override fun deleteImage(wordName: String) {
        file = File(
            "${
                resourcesProviderImpl.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            }${Constants.CACHE_FOLDER_NAME}$wordName"
        )
        File(file, "$wordName${Constants.CACHE_FILE_EXTENSION}").delete()
        file.delete()
    }
}