package com.xiongliang.pluginproject;

import android.content.Context;
import android.util.Log;

import com.xiongliang.pluginlibrary.IBean;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

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


    /**
     *  合并插件dex 到主dex
     */
    public void combinePluginDex(ClassLoader classLoader, File apkFile, File optDexFile){
        try{
            //获取BaseDexClassLoader的变量pathList
            Object  pathListObject = ReflectUtil.getFieldObject(DexClassLoader.class.getSuperclass(),classLoader,"pathList");

            //获取DexPathList的 Elements[] dexElements
            Object[]  dexElements = (Object[]) ReflectUtil.getFieldObject(pathListObject,"dexElements");

            //创建一个数组，用来替换原始的数组
            Class<?> elementClass = dexElements.getClass().getComponentType();  //获取Element类型
            Object[] newElements = (Object[]) Array.newInstance(elementClass,dexElements.length+1);

            //构造插件Element(File file,boolean isDirectory, File zip, DexFile dexFile)
            Class[] paramClass = {File.class, boolean.class, File.class, DexFile.class};
            Object[] paramValue = {apkFile,false, apkFile,
                    DexFile.loadDex(apkFile.getCanonicalPath(),optDexFile.getAbsolutePath(),0)};
            Object elementObject = ReflectUtil.createObject(elementClass,paramClass,paramValue);

            Object[] toAddElementArray = new Object[]{elementObject};
            //将原始的Element 复制进去
            System.arraycopy(dexElements,0,newElements,0,dexElements.length);
            //将插件Element 复制进去
            System.arraycopy(toAddElementArray,0,newElements,dexElements.length,toAddElementArray.length);
            //替换
            ReflectUtil.setFileObject(pathListObject,"dexElements",newElements);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
