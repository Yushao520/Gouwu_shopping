package com.bewai.Gouwu_shopping;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by &那么& on 2017/10/25.
 */

public class MyApp extends Application{
    @Override
        public void onCreate() {
            super.onCreate();

            String path = Environment.getExternalStorageDirectory()+"/1507BAccess";

            File cacheDir = new File(path);

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                    .threadPriority(100)
                    .threadPoolSize(3)
                    .memoryCacheExtraOptions(200,200)
                    .memoryCacheSize(2 * 1024 * 1024)
                    .diskCache(new UnlimitedDiskCache(cacheDir))
                    .diskCacheSize(50 * 1024 * 1024)
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .build();

            ImageLoader.getInstance().init(config);


        }


        /**
         * 配置图片是否缓存
         * @return
         */
        public static DisplayImageOptions getDisplayImageOptions(){


            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();

            return options;


        }

}
