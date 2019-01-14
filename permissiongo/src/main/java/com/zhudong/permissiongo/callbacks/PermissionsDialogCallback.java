package com.zhudong.permissiongo.callbacks;


public interface PermissionsDialogCallback {

    /**
     * @param isClickOk true 点击的是确定按钮，false用户选择的关闭弹窗(关闭按钮或物理返回键)
     */
    void onDismiss(boolean isClickOk);
}
