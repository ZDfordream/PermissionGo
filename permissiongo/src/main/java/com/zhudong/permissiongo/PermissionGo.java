package com.zhudong.permissiongo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import com.zhudong.permissiongo.callbacks.PermissionCallBack;
import com.zhudong.permissiongo.callbacks.PermissionsCallback;
import com.zhudong.permissiongo.model.PermissionModel;
import com.zhudong.permissiongo.model.PermissionsResult;
import com.zhudong.permissiongo.setting.BaseSetting;
import com.zhudong.permissiongo.setting.Default;
import com.zhudong.permissiongo.setting.OPPO;
import com.zhudong.permissiongo.setting.ViVo;

/**
 * 此类维护权限管理相关的工具方法
 */
public class PermissionGo {
    public static final String MANUFACTURER_HUAWEI = "Huawei";//华为
    public static final String MANUFACTURER_MEIZU = "Meizu";//魅族
    public static final String MANUFACTURER_XIAOMI = "Xiaomi";//小米
    public static final String MANUFACTURER_OPPO = "OPPO";
    public static final String MANUFACTURER_VIVO = "vivo";
    public static final String MANUFACTURER_SAMSUNG = "samsung";//三星

    public static Application application;

    public static void init(Application app){
        application = app;
    }

    /**
     * 常用的权限，如果不够的话，可以添加，也可以使用 {@link PermissionGo#checkPermission(String)} 检测
     */
    @Deprecated
    public enum Permission {
        ACCESS_NETWORK_STATE("android.permission.ACCESS_NETWORK_STATE"),
        ACCESS_WIFI_STATE("android.permission.ACCESS_WIFI_STATE"),
        CHANGE_WIFI_STATE("android.permission.CHANGE_WIFI_STATE"),
        CHANGE_WIFI_MULTICAST_STATE("android.permission.CHANGE_WIFI_MULTICAST_STATE"),
        RECEIVE_SMS("android.permission.RECEIVE_SMS"),
        AUTHENTICATE_ACCOUNTS("android.permission.AUTHENTICATE_ACCOUNTS"),
        GET_ACCOUNTS("android.permission.GET_ACCOUNTS"),
        WRITE_CONTACTS("android.permission.WRITE_CONTACTS"),
        READ_LOGS("android.permission.READ_LOGS"),
        WAKE_LOCK("android.permission.WAKE_LOCK"),
        INTERNET("android.permission.INTERNET"),
        MANAGE_ACCOUNTS("android.permission.MANAGE_ACCOUNTS"),
        READ_PHONE_STATE("android.permission.READ_PHONE_STATE"),
        USE_CREDENTIALS("android.permission.USE_CREDENTIALS"),
        WRITE_EXTERNAL_STORAGE("android.permission.WRITE_EXTERNAL_STORAGE"),
        READ_CONTACTS("android.permission.READ_CONTACTS"),
        SEND_SMS("android.permission.SEND_SMS"),
        WRITE_SMS("android.permission.WRITE_SMS"),
        READ_SMS("android.permission.READ_SMS"),
        SYSTEM_ALERT_WINDOW("android.permission.SYSTEM_ALERT_WINDOW"),
        KILL_BACKGROUND_PROCESSES("android.permission.KILL_BACKGROUND_PROCESSES"),
        BLUETOOTH("android.permission.BLUETOOTH"),
        RESTART_PACKAGES("android.permission.RESTART_PACKAGES"),
        EXPAND_STATUS_BAR("android.permission.EXPAND_STATUS_BAR"),
        MOUNT_UNMOUNT_FILESYSTEMS("android.permission.MOUNT_UNMOUNT_FILESYSTEMS"),
        BROADCAST_STICKY("android.permission.BROADCAST_STICKY"),
        ACCESS_DRM("android.permission.ACCESS_DRM"),
        INSTALL_DRM("android.permission.INSTALL_DRM"),
        CAMERA("android.permission.CAMERA"),
        GET_TASKS("android.permission.GET_TASKS"),
        GET_PACKAGE_SIZE("android.permission.GET_PACKAGE_SIZE"),
        GET_TOP_ACTIVITY_INFO("android.permission.GET_TOP_ACTIVITY_INFO"),
        DOWNLOAD_WITHOUT_NOTIFICATION("android.permission.DOWNLOAD_WITHOUT_NOTIFICATION"),
        WRITE_SETTINGS("android.permission.WRITE_SETTINGS");


        private final String permission;

        Permission(String permission) {
            this.permission = permission;
        }

        public String getPermission() {
            return permission;
        }
    }

    /**
     * 检查权限
     *
     * @return 返回 true 如果已经申请，返回 false 如果未申请（或者被动态关闭）
     */
    @Deprecated
    public static boolean checkPermission(Permission permission) {
        return checkPermission(permission.getPermission());
    }

    /**
     * 检查权限
     *
     * @return 返回 true 如果已经申请，返回 false 如果未申请（或者被动态关闭）
     */
    public static boolean checkPermission(@NonNull String permission) {
        try {
            return PermissionChecker.checkSelfPermission(application, permission)
                    == PackageManager.PERMISSION_GRANTED;
        } catch (RuntimeException t) {
            Log.e("PermissionGo", t.toString());
            return false;
        }
    }

    /**
     * 判断是否所有权限都同意了，都同意返回true 否则返回false
     */
    public static boolean checkPermission(String... permissions) {
        for (String permission : permissions) {
            if (!checkPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查是否都赋予权限
     *
     * @param grantResults grantResults
     * @return 所有都同意返回true 否则返回false
     */
    public static boolean verifyPermissions(int... grantResults) {
        if (grantResults.length == 0) return false;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 申请多个权限
     *
     * @param listener    结果回调
     * @param permissions 权限
     */
    @MainThread
    public static void requestPermissions(Activity context, PermissionsCallback listener, String... permissions) {
        if (context == null) {
            return;
        }
        PermissionsManger.from(context).requestPermissions(listener, permissions);
    }

    /**
     * 申请单个权限
     * @param context
     * @param permission
     * @param permissionCallBack
     */
    public static void requestSinglePermission(Activity context, final String permission,
                                             final PermissionCallBack permissionCallBack) {
        if (PermissionGo.checkPermission(permission)) {
            permissionCallBack.onAccepted(permission);
        } else {
            PermissionGo.requestPermissions(context, new PermissionsCallback() {
                @Override
                public void permissionsResult(PermissionsResult results) {
                    if (results == null || results.getList() == null || results.getList().size() == 0) {
                        permissionCallBack.onRefused(permission);
                    } else {
                        PermissionModel result = results.getList().get(0);
                        if (result.getGranted()) {
                            permissionCallBack.onAccepted(permission);
                        } else if (result.getShouldShowRequest()) {
                            permissionCallBack.onRefused(permission);
                        } else {
                            permissionCallBack.onNeverShowed(permission);
                        }
                    }
                }
            }, permission);
        }
    }

    /**
     * 打开权限设置界面
     * vivo,OPPO单独处理
     *
     * @param context context
     */
    public static void openSetting(Context context) {
        BaseSetting iSetting;

        switch (Build.MANUFACTURER) {
            case MANUFACTURER_VIVO:
                iSetting = new ViVo(context);
                break;
            case MANUFACTURER_OPPO:
                iSetting = new OPPO(context);
                break;
            default:
                iSetting = new Default(context);
                break;
        }
        if (iSetting.getSetting() == null) return;
        context.startActivity(iSetting.getSetting());
    }
}
