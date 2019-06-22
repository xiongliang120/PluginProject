package com.xiongliang.pluginproject;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 加载插件资源
 */
public class PluginResourceLoadUtil {
    private Context mContext;
    private DexClassLoader dexClassLoader;
    AssetManager assetManager;
    Resources mResource;
    Resources.Theme mTheme;
    /**
     * 加载插件apk中dex 文件
     */
    public void loadPlugin(Context context){
        mContext = context;
        File extractFile = mContext.getFileStreamPath("plugin1-debug.apk");
        String dexPath = extractFile.getPath();
        File fileRelease =  mContext.getDir("dex",0);
        dexClassLoader = new DexClassLoader(dexPath,fileRelease.getAbsolutePath(),null,mContext.getClassLoader());
    }


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
            mTheme = mResource.newTheme();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /** 加载插件中类
     *
     * @param className
     * @param paramValue
     */
    public Object loadClass(String className,String methodName,Context paramValue){
        try{
            //加载指定类
            Class beanClass = dexClassLoader.loadClass(className);
            Object beanObject = beanClass.newInstance();
            Method method = beanClass.getDeclaredMethod(methodName,Context.class);
            return  method.invoke(beanObject,paramValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public Resources getmResource(){
        return mResource;
    }

    public AssetManager getAssetManager(){
        return assetManager;
    }

    public Resources.Theme getmTheme(){
        return mTheme;
    }


    public String getString(int resId){
        if(mResource != null){
            return mResource.getString(resId);
        }
        return "";
    }



}
