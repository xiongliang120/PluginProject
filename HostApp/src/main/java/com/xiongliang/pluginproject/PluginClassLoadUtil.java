package com.xiongliang.pluginproject;

import android.content.Context;
import android.util.Log;

import com.xiongliang.pluginlibrary.IBean;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginClassLoadUtil {
    private DexClassLoader dexClassLoader;
    /**
     * 加载插件apk中dex 文件
     */
    public void loadPlugin(Context mContext){
        File extractFile = mContext.getFileStreamPath("plugin1-debug.apk");
        String dexPath = extractFile.getPath();
        File fileRelease =  mContext.getDir("dex",0);
        dexClassLoader = new DexClassLoader(dexPath,fileRelease.getAbsolutePath(),null,mContext.getClassLoader());
    }

    /**
     * 加载插件中任何类
     */
    public void loadClass(String className){
        try{
            //加载指定类
            Class beanClass = dexClassLoader.loadClass(className);
            Object beanObject = beanClass.newInstance();

            IBean bean = (IBean) beanObject;
            bean.setName("熊亮");
            Log.i("msg","打印名字="+bean.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
