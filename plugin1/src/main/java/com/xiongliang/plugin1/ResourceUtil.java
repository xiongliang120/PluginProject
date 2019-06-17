package com.xiongliang.plugin1;

import android.content.Context;
import android.util.Log;

public class ResourceUtil {
    public String getStringForResId(Context context){
        Log.i("msg","插件中获取到的string="+context.getResources().getString(R.string.plugin_name));
        return context.getResources().getString(R.string.plugin_name);
    }
}
