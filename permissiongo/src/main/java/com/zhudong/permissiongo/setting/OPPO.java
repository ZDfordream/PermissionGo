package com.zhudong.permissiongo.setting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class OPPO implements BaseSetting {

    private final Context context;

    public OPPO(Context context){
        this.context = context;
    }

    @Override
    public Intent getSetting() {
        if(Build.VERSION.SDK_INT<23){
            //低版本手机打开应用管理，所有权限只能看不能操作，兼容处理一下,低版本跳到权限管家
            return context.getPackageManager().getLaunchIntentForPackage("com.oppo.secure");
        }
        Intent intent =new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }
}
