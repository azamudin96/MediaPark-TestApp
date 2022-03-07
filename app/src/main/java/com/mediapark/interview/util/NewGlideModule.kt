package com.mediapark.interview.util

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class NewGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setLogLevel(Log.ERROR)
    }

    companion object {
        fun diskCacheRequestOption(): RequestOptions {
            return RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        }

        fun noCacheRequestOption(): RequestOptions {
            return RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
        }

        fun noCacheListResizeRequestOption(): RequestOptions {
            return RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .override(Constant.DEFAULT_GLIDE_RESIZE_LIST_VALUE)
        }

        fun noCacheDetailResizeRequestOption(): RequestOptions {
            return RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .override(Constant.DEFAULT_GLIDE_RESIZE_DETAIL_VALUE)
        }

        fun listResizeRequestOption(): RequestOptions {
            return RequestOptions()
                .override(Constant.DEFAULT_GLIDE_RESIZE_LIST_VALUE)
        }
    }
}