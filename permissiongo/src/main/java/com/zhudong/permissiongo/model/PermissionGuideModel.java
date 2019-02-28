package com.zhudong.permissiongo.model;

import java.io.Serializable;
import java.util.List;

public class PermissionGuideModel implements Serializable {
    private String title;//标题
    private String desc;//标题下的描述
    private String bottomDesc;//底部按钮描述
    private List<PermissionItemModel> list;//具体权限描述
    private boolean showClose;//右上角的X

    public PermissionGuideModel() {
    }

    public PermissionGuideModel(String title, String desc, String bottomDesc) {
        this.title = title;
        this.desc = desc;
        this.bottomDesc = bottomDesc;
    }

    public PermissionGuideModel(String title, String desc, String bottomDesc, List<PermissionItemModel> list) {
        this.title = title;
        this.desc = desc;
        this.bottomDesc = bottomDesc;
        this.list = list;
    }

    public PermissionGuideModel(String title, String desc, String bottomDesc, List<PermissionItemModel> list, boolean showClose) {
        this.title = title;
        this.desc = desc;
        this.bottomDesc = bottomDesc;
        this.list = list;
        this.showClose = showClose;
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

    public String getBottomDesc() {
        return bottomDesc;
    }

    public void setBottomDesc(String bottomDesc) {
        this.bottomDesc = bottomDesc;
    }

    public List<PermissionItemModel> getList() {
        return list;
    }

    public void setList(List<PermissionItemModel> list) {
        this.list = list;
    }

    public boolean isShowClose() {
        return showClose;
    }

    public void setShowClose(boolean showClose) {
        this.showClose = showClose;
    }
}
