package com.zhudong.permissiongo.callbacks;


import java.util.List;

public interface PermissionsCallback {

    void onAcceptAll();

    void onAcceptPart(List<String> partAcceptedPermissions);

    void onRefused(List<String> refusedPermissions);

    void onNeverShowed(List<String> neverShowedPermissions);
}
