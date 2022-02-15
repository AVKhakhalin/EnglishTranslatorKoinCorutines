package ru.geekbrains.popular.libraries.utils.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoaderImpl: ImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
//            .asBitmap()
//            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .load(url)
            .into(container)
    }
}