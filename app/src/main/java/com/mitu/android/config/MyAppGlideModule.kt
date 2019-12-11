package com.mitu.android.config

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskCache
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule
import com.mitu.android.constant.Constants
import java.io.File

/**
 * Created by HuangQiang on 18/6/5.
 * glide必须的
 */
@GlideModule
class MyAppGlideModule : AppGlideModule() {


    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //设置缓存目录
        val imagePath = File(Constants.SDCARD_IMG_ROOT)

        val cache = DiskLruCacheWrapper.get(imagePath, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE.toLong())// 250 MB

        builder.setDiskCache { cache }
        //设置memory和Bitmap池的大小
        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize

        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()

        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize.toLong()))

    }
}