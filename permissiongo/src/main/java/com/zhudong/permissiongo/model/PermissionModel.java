package com.zhudong.permissiongo.model;

import java.io.Serializable;

/**
 * 当granted和shouldShowRequest 同为false时
 * 表示用户拒绝并不再询问
 */
public class PermissionModel implements Serializable{
    private String name;//权限名字
    private boolean granted;//是否申请成功
    private boolean shouldShowRequest;//是否可以再次弹出申请

    public PermissionModel() {
    }

    public PermissionModel(String name) {
        this.name = name;
    }

    public PermissionModel(String name, boolean granted) {
        this.name = name;
        this.granted = granted;
    }

    public PermissionModel(String name, boolean granted, boolean shouldShowRequest) {
        this.name = name;
        this.granted = granted;
        this.shouldShowRequest = shouldShowRequest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }

    public boolean getShouldShowRequest() {
        return shouldShowRequest;
    }

    public void setShouldShowRequest(boolean shouldShowRequest) {
        this.shouldShowRequest = shouldShowRequest;
    }
}
