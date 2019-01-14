package com.zhudong.permissiongo.model;

import java.io.Serializable;
import java.util.List;

public class PermissionsResult implements Serializable {
    private boolean grantedAll;//所有权限有一个拒绝就是false
    private List<PermissionModel> list;

    public PermissionsResult() {
    }

    public PermissionsResult(boolean grantedAll, List<PermissionModel> list) {
        this.grantedAll = grantedAll;
        this.list = list;
    }

    public boolean getGrantedAll() {
        return grantedAll;
    }

    public void setGrantedAll(boolean grantedAll) {
        this.grantedAll = grantedAll;
    }

    public List<PermissionModel> getList() {
        return list;
    }

    public void setList(List<PermissionModel> list) {
        this.list = list;
    }
}
