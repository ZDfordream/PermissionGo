package com.zhudong.permissiongo;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.zhudong.permissiongo.Utils.ToolUtil;
import com.zhudong.permissiongo.callbacks.PermissionsCallback;
import com.zhudong.permissiongo.model.PermissionModel;
import com.zhudong.permissiongo.model.PermissionsResult;

import java.util.ArrayList;
import java.util.List;

public class PermissionRequestFragment extends Fragment {
    private int requestCode = 42;
    private List<String> grantedPermissions = new ArrayList<>();//已经有过的权限
    private PermissionsCallback mListener;

    public void requestPermissions(PermissionsCallback listener, List<String> permissions) {
        if (permissions == null || permissions.size() == 0) {
            return;
        }
        mListener = listener;
        requestPermission(permissions);
    }

    /**
     * 申请权限
     * 申请之前先把已经有的权限去掉不再次申请，否则小米手机上，拒绝的话会崩溃
     *
     * @param permissions permission list
     */
    private void requestPermission(List<String> permissions) {
        List<String> requestPermissions = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++) {
            //对数组进入遍历，判断是否已经有了权限，有的话移除申请权限数组
            boolean result = PermissionGo.checkPermission(permissions.get(i));
            if (result) {
                grantedPermissions.add(permissions.get(i));
            } else {
                requestPermissions.add(permissions.get(i));
            }
        }
        if (requestPermissions.isEmpty()) {
            //申请的权限全都有了，直接走成功回调
            List<PermissionModel> grantedList = new ArrayList<>();
            for (int i = 0; i < permissions.size(); i++) {
                PermissionModel model = new PermissionModel(permissions.get(i), true);
                grantedList.add(model);
            }
            callBack(new PermissionsResult(true, grantedList));
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //fragment里申请权限是在6.0以后才增加的方法
            //6.0之前只要在清单里申请过权限，只要用户安装了就一定有了，除非用户手动关闭
            requestPermissions(requestPermissions.toArray(new String[requestPermissions.size()]), requestCode);
        } else {
            List<PermissionModel> permissionList = new ArrayList<>();
            for (int i = 0; i < grantedPermissions.size(); i++) {
                //已经赋予的权限为true
                permissionList.add(new PermissionModel(grantedPermissions.get(i), true));
            }
            for (int j = 0; j < requestPermissions.size(); j++) {
                //需要申请的权限全都是false
                permissionList.add(new PermissionModel(requestPermissions.get(j), false, false));
            }
            callBack(new PermissionsResult(false, permissionList));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mListener == null) {
            return;
        }
        List<PermissionModel> list = new ArrayList<>();
        //如果有申请之前保存的权限组，放进去
        for (int i = 0; i < grantedPermissions.size(); i++) {
            list.add(new PermissionModel(grantedPermissions.get(i), true));
        }
        boolean grantedAll = true;
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            PermissionModel result = new PermissionModel(perm);
            if ("Xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
                boolean checkPermission = PermissionGo.checkPermission(perm);
                if (checkPermission) {
                    result.setGranted(true);
                } else {
                    grantedAll = false;
                    result.setShouldShowRequest(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), perm));
                }
            } else {
                //grantResults 需要下标来比对
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    result.setGranted(true);
                } else {
                    grantedAll = false;
                    result.setShouldShowRequest(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), perm));
                }
            }
            list.add(result);
        }
        callBack(new PermissionsResult(grantedAll, list));
    }

    private void callBack(PermissionsResult permissionsResult) {
        if (permissionsResult.getGrantedAll()) {
            mListener.onAcceptAll();
            return;
        }

        List<String> partAcceptedPermissions = new ArrayList<>();
        List<String> refusedPermissions = new ArrayList<>();
        List<String> neverShowedPermissions = new ArrayList<>();
        for (int i = 0; i < permissionsResult.getList().size(); i++) {
            PermissionModel permissionModel = permissionsResult.getList().get(i);
            if (permissionModel.getGranted()) {
                //同意的权限
                partAcceptedPermissions.add(permissionModel.getName());
            } else if (!permissionModel.getGranted() && permissionModel.getShouldShowRequest()) {
                //拒绝的权限
                refusedPermissions.add(permissionModel.getName());
            } else if (!permissionModel.getGranted() && !permissionModel.getShouldShowRequest()) {
                //拒绝并且不在提示，表明需要跳转到设置界面
                neverShowedPermissions.add(permissionModel.getName());
            }
        }
        if (ToolUtil.size(partAcceptedPermissions) > 0) {
            mListener.onAcceptPart(partAcceptedPermissions);
        }
        if (ToolUtil.size(refusedPermissions) > 0) {
            mListener.onRefused(refusedPermissions);
        }
        if (ToolUtil.size(neverShowedPermissions) > 0) {
            mListener.onNeverShowed(neverShowedPermissions);
        }
        mListener = null;
    }

}
