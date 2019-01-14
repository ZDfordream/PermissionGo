package com.zhudong.permissiongo.setting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.zhudong.permissiongo.Utils.ToolUtil;

public class Default implements BaseSetting{

    private final Context context;

    public Default(Context context){
        this.context = context;
    }

    @Override
    public Intent getSetting() {
        Intent intent = new Intent();
        if (!ToolUtil.isActivityContext(context)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }
}
