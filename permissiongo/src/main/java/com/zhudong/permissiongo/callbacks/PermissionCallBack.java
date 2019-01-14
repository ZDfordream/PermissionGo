package com.zhudong.permissiongo.callbacks;

/**
 * 申请单条回调
 */
public interface PermissionCallBack {
    //已经接受该权限
    void onAccepted(String permission);

    void onRefused(String permission);

    void onNeverShowed(String permission);
}
