package com.zhudong.permissiongo.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

public class ViVo implements BaseSetting {

    private final Context context;

    public ViVo(Context context){
        this.context = context;
    }

    @Override
    public Intent getSetting() {
        if(Build.VERSION.SDK_INT<23){
            return context.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
        }
        Intent intent =new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_SETTINGS);
        return intent;
    }
}
