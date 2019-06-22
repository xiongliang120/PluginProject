package com.xiongliang.plugin1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class ResourceUtil {
    public String getStringForResId(Context context){
        return context.getResources().getString(R.string.plugin_name1);
    }

    public Drawable getImageDrawable(Context context){
        return context.getResources().getDrawable(R.drawable.plugin1);
    }

    public View getLayout(Context context){
        return LayoutInflater.from(context).inflate(R.layout.activity_plugin,null);
    }
}
