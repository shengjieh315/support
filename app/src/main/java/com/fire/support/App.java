package com.fire.support;

import android.app.Application;
import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.fire.support.utils.Utils;
import com.socks.library.KLog;

import java.io.File;

public class App extends Application {

    //    全局application
    private static App app;

    /**
     * 全局唯一的Application
     *
     * @return Application
     */
    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        initAppFresco();
    }

    public void initAppFresco() {

//        MemoryTrimmableRegistry memoryTrimmableRegistry = NoOpMemoryTrimmableRegistry.getInstance();
//        memoryTrimmableRegistry.registerMemoryTrimmable(new MemoryTrimmable() {
//            @Override
//            public void trim(MemoryTrimType trimType) {
//                final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();
//
//                KLog.e("suggestedTrimRatio" + suggestedTrimRatio);
//                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
//                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
//                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio
//                ) {
//
//                    KLog.e("clearMemoryCaches" + suggestedTrimRatio);
//                    // 清除内存缓存
//                    Fresco.getImagePipeline().clearMemoryCaches();
//                }
//            }
//        });
//
//        File fromCacheFile = Utils.getCacheDir(getApplicationContext());
//
//        DiskCacheConfig smallDiskCacheConfig = DiskCacheConfig
//                .newBuilder(getApplicationContext())
//                .setBaseDirectoryPath(fromCacheFile)
//                .setBaseDirectoryName("image_small")
//                .setMaxCacheSizeOnVeryLowDiskSpace(50 * ByteConstants.MB)
//                .setMaxCacheSizeOnLowDiskSpace(100 * ByteConstants.MB)
//                .setMaxCacheSize(200 * ByteConstants.MB)
//                .build();
//
//
//        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(
//                getApplicationContext(), ua, CanOkHttp.getInstance()
//                        .setConnectTimeout(10)
//                        .setReadTimeout(10)
//                        .setWriteTimeout(10)
//                        .setRetryOnConnectionFailure(true)
//                        .setMaxRetry(0)
//                        .setDns(OkHttpDns.getInstance(getApplicationContext()))
//                        .getHttpClient())
//                .setDownsampleEnabled(true)
//                .setBitmapsConfig(Bitmap.Config.RGB_565)
//                .setMemoryTrimmableRegistry(memoryTrimmableRegistry) // 报内存警告时的监听
//                .setResizeAndRotateEnabledForNetwork(true)
//                .setSmallImageDiskCacheConfig(smallDiskCacheConfig)
//                .setCacheKeyFactory(MyCacheKeyFactory.getInstance())
//                .build();

        Fresco.initialize(this);

    }

}
