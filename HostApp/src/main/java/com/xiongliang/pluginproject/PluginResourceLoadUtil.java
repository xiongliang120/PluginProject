package com.xiongliang.pluginproject;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;

import dalvik.system.DexClassLoader;

/**
 * 加载插件资源
 */
public class PluginResourceLoadUtil {
    private Context mContext;
    private DexClassLoader dexClassLoader;
    AssetManager assetManager;
    Resources mResource;
//    /**
//     * 加载插件apk中dex 文件
//     */
//    public void loadPlugin(Context context){
//        mContext = context;
//        File extractFile = mContext.getFileStreamPath("plugin1-debug.apk");
//        String dexPath = extractFile.getPath();
//        File fileRelease =  mContext.getDir("dex",0);
//        dexClassLoader = new DexClassLoader(dexPath,fileRelease.getAbsolutePath(),null,mContext.getClassLoader());
//    }


    /**
     * 加载资源文件
     */
    public void loadResources(Context mContext,String apkPath){
        try{
            File extractFile = mContext.getFileStreamPath(apkPath);
            String path= extractFile.getPath();
            assetManager = AssetManager.class.newInstance();
            ReflectUtil.invokeInstanceMethod(assetManager,"addAssetPath",new Class[]{String.class},new String[]{path});
            mResource = new Resources(assetManager,mContext.getResources().getDisplayMetrics(),mContext.getResources().getConfiguration());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public String getString(int resId){
        if(mResource != null){
            return mResource.getString(resId);
        }
        return "";
    }



}
