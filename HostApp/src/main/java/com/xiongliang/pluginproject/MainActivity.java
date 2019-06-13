package com.xiongliang.pluginproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xiongliang.pluginlibrary.IBean;

import java.io.File;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    private DexClassLoader dexClassLoader;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
//        checkPermission();
        //将Assets中插件apk 拷贝
        FileUtils.extractAssets(this,"plugin-debug.apk");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadPlugin();
        loadClass();
    }

    /**
     * 加载插件
     */
    public void loadPlugin(){
        File extractFile = this.getFileStreamPath("plugin1-debug.apk");
        String dexPath = extractFile.getPath();
        File fileRelease =  getDir("dex",0);
        dexClassLoader = new DexClassLoader(dexPath,fileRelease.getAbsolutePath(),null,getClassLoader());


    }

    /**
     * 加载插件中任何类
     */
    public void loadClass(){
        try{
            //加载指定类
            Class beanClass = dexClassLoader.loadClass("com.xiongliang.plugin1.Bean");
            Object beanObject = beanClass.newInstance();

            IBean bean = (IBean) beanObject;
            bean.setName("熊亮");
            Log.i("msg","打印名字="+bean.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //读写权限
    private void checkPermission() {
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
