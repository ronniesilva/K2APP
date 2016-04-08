package com.app.k2app.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.app.k2app.R;
import com.app.k2app.config.Config;
import com.app.k2app.config.Params;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public class MyApplication extends Application {

    private static MyApplication sInstance;
    public ImageLoader mImageLoader;

    // Value for Activity FILTERS
    public static SharedPreferences sharedPrefs;
    public static SharedPreferences.Editor shEditor;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        //Shared Preferences
        initSharedPreferences();

        //Init Universal Image Loader
        initUIL();

    }

    public static synchronized MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }


    public static String getWsUrlApp(){
        String WsUrlApp = Config.K2WEBSERVER + Params.WsBaseDir;
        return WsUrlApp;
    }

    public static String getWsUrlPhotoPerfil(){
        String WsUrlImages = Config.K2WEBSERVER + Params.WsDirPhotoPerfil;
        return WsUrlImages;
    }


    public static String getWsUrlPostImages(){
        String WsUrlImages = Config.K2WEBSERVER + Params.WsDirPostImage;
        return WsUrlImages;
    }

    // Init Shared Preferences
    public static void initSharedPreferences(){
        sharedPrefs = MyApplication.getAppContext().getSharedPreferences(Config.SHARED_PREFS_NAME, MODE_PRIVATE);
        shEditor = sharedPrefs.edit();
    }

    //Lib Universal Image Loader
    public void initUIL(){

        //UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .displayer(new FadeInBitmapDisplayer(300))
                .showImageForEmptyUri(R.mipmap.ic_action_k2pio) // error404
                .showImageOnFail(R.mipmap.ic_action_k2pio)      // fail
                .showImageOnLoading(R.drawable.loading_blue)    // loading
                .build();

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(MyApplication.getAppContext())
                .defaultDisplayImageOptions(mDisplayImageOptions)
                .memoryCacheSize(50 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                //.tasksProcessingOrder(QueueProcessingType.FIFO)
                .threadPriority(5)
                .threadPoolSize(5)
                .writeDebugLogs()
                .build();

            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(conf);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
