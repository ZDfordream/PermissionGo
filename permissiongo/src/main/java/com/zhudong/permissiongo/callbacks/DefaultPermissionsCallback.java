package com.zhudong.permissiongo.callbacks;

import java.util.List;

/**
 * 提供一个默认实现
 */
public class DefaultPermissionsCallback implements PermissionsCallback{
    @Override
    public void onAcceptAll() {

    }

    @Override
    public void onAcceptPart(List<String> partAcceptedPermissions) {

    }

    @Override
    public void onRefused(List<String> refusedPermissions) {

    }

    @Override
    public void onNeverShowed(List<String> neverShowedPermissions) {

    }
}
