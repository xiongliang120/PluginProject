package com.xiongliang.pluginproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xiongliang.pluginlibrary.IBean;

import java.io.File;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    private Button loadClassButton;
    private Button loadResourceButton;


   PluginClassLoadUtil pluginClassLoadUtil;
   PluginResourceLoadUtil pluginResourceLoadUtil;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        //将Assets中插件apk 拷贝
        FileUtils.extractAssets(this,"plugin1-debug.apk");
        pluginClassLoadUtil = new PluginClassLoadUtil();
        pluginResourceLoadUtil = new PluginResourceLoadUtil();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadClassButton = findViewById(R.id.plugin_class);
        loadResourceButton = findViewById(R.id.plugin_resource);

        loadClassButton.setOnClickListener(clickListener);
        loadResourceButton.setOnClickListener(clickListener);



    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.plugin_class:
                    testLoadClass();
                    break;
                case R.id.plugin_resource:
                    loadResource();
                    break;
            }
        }
    };


    public void testLoadClass(){
        pluginClassLoadUtil.loadPlugin(this);
        pluginClassLoadUtil.loadClass("com.xiongliang.plugin1.Bean");
    }

    public void loadResource(){
        pluginResourceLoadUtil.loadResources(this,"plugin1-debug.apk");
        String pluginName = pluginResourceLoadUtil.getString(R.string.app_name);
        Log.i("msg","打印插件名字="+pluginName);
        pluginClassLoadUtil.loadPlugin(this);
        pluginClassLoadUtil.loadClass("com.xiongliang.plugin1.ResourceUtil",this);

    }



}
