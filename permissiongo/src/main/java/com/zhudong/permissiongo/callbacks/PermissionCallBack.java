package com.zhudong.permissiongo.callbacks;

/**
 * 申请单条回调
 */
public interface PermissionCallBack {

    void onAccepted(String permission);

    void onRefused(String permission);

    void onNeverShowed(String permission);
}
