package com.example.study.model.Cache;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class GlideCache extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
//缓存大小
        int GlideCacheMaxSize = 1024 * 1024 * 500;
        builder.setDiskCache(
                new DiskLruCacheFactory(getStorageDirectory() + "/GlideDisk", GlideCacheMaxSize)
        );
    }

    //外部路径
    private String sdRootPath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.example.study/cache/GlidePicture";
    private String appRootPath = Environment.getDataDirectory().getPath() + "/Android/data/com.example.study/cache/GlidePicture";

    private String getStorageDirectory() {
        //判断有没有SD卡 有的话就用SD卡路径 没有的话就用系统根路径
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? sdRootPath : appRootPath;

    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }


}


