package com.zhudong.permissiongo.Utils;

import android.app.Activity;
import android.content.Context;

import com.zhudong.permissiongo.PermissionGo;

import java.util.Collection;

public class ToolUtil {

    public static boolean isActivityContext(Context context) {
        return context instanceof Activity;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = PermissionGo.application.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = PermissionGo.application.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static <T> boolean isEmpty(Collection<T> list) {
        return list == null || list.isEmpty();
    }

    public static int size(Collection collection) {
        return collection != null ? collection.size() : 0;
    }
}
