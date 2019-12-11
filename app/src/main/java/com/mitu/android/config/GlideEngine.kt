package com.daydaycook.mitao.config

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.zhihu.matisse.engine.ImageEngine

/**
 * Describe : implementation using Glide.
 * Created by Leo on 2018/9/7 on 10:55.
 */
class GlideEngine : ImageEngine {
    override fun supportAnimatedGif(): Boolean {
        return true
    }


    override fun loadThumbnail(
        context: Context,
        resize: Int,
        placeholder: Drawable,
        imageView: ImageView,
        uri: Uri
    ) {
        try {
            Glide.with(context)
                .asBitmap()  // some .jpeg files are actually gif
                .load(uri)
                .apply(
                    RequestOptions().placeholder(placeholder)
                        .override(resize, resize)
                        .centerCrop()
                )
                .into(imageView)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

    override fun loadGifThumbnail(
        context: Context,
        resize: Int,
        placeholder: Drawable,
        imageView: ImageView,
        uri: Uri
    ) {
        try {

            Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(
                    RequestOptions().placeholder(placeholder)
                        .override(resize, resize)
                        .centerCrop()
                )
                .into(imageView)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

    override fun loadImage(
        context: Context,
        resizeX: Int,
        resizeY: Int,
        imageView: ImageView,
        uri: Uri
    ) {
        try {

            Glide.with(context)
                .load(uri)
                .apply(
                    RequestOptions().priority(Priority.HIGH)
                        .fitCenter()
                )
                .into(imageView)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

    override fun loadGifImage(
        context: Context,
        resizeX: Int,
        resizeY: Int,
        imageView: ImageView,
        uri: Uri
    ) {
        try {

            Glide.with(context)
                .asGif()
                .load(uri)
                .apply(
                    RequestOptions().priority(Priority.HIGH)
                        .override(resizeX, resizeY)
                )
                .into(imageView)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }
}