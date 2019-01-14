package com.zhudong.permissiongo.model;


import java.io.Serializable;

/**
 * 弹窗里具体权限明细
 */

public class PermissionItemModel implements Serializable {
    private String title;//标题
    private String desc;//标题下的描述
    private int img;//权限图标

    public PermissionItemModel() {
    }

    public PermissionItemModel(String title) {
        this.title = title;
    }

    public PermissionItemModel(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public PermissionItemModel(String title, String desc, int img) {
        this.title = title;
        this.desc = desc;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
