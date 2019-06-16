package com.xiongliang.plugin1.base;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("msg","执行插件的Application的onCreate方法");
    }
}
