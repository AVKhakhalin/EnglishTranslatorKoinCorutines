package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.view.utils.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class GlideImageLoaderImpl: ImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
//            .asBitmap()
//            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .load(url)
            .into(container)
    }
}