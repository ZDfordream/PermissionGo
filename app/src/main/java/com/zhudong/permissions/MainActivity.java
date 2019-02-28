package com.zhudong.permissions;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zhudong.permissiongo.PermissionGo;
import com.zhudong.permissiongo.Utils.ToolUtil;
import com.zhudong.permissiongo.callbacks.PermissionsCallback;
import com.zhudong.permissiongo.callbacks.PermissionsDialogCallback;
import com.zhudong.permissiongo.dialog.PermissionGuideDialog;
import com.zhudong.permissiongo.model.PermissionGuideModel;
import com.zhudong.permissiongo.model.PermissionItemModel;
import com.zhudong.permissiongo.model.PermissionModel;
import com.zhudong.permissiongo.model.PermissionsResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionGo.init(getApplication());
        findViewById(R.id.request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionCheck();
            }
        });
    }

    public void permissionCheck() {
        PermissionGo.requestPermissions(MainActivity.this, new PermissionsCallback() {
            @Override
            public void onAcceptAll() {
                Log.e(TAG, "onAcceptAll");
            }

            @Override
            public void onAcceptPart(List<String> partAcceptedPermissions) {
                Log.e(TAG, "onAcceptPart");
            }

            @Override
            public void onRefused(List<String> refusedPermissions) {
                Log.e(TAG, "onRefused");
            }

            @Override
            public void onNeverShowed(List<String> neverShowedPermissions) {
                List<PermissionItemModel> list = new ArrayList<>();
                list.add(new PermissionItemModel("读写存储卡权限", "使用您的存储卡来缓存视频数据", 0));
                list.add(new PermissionItemModel("定位信息权限", "使用您的位置信息为您提供精准服务", 0));
                PermissionGuideModel model = new PermissionGuideModel("温馨提示", "播放视频需要开启以下权限", "去开启权限",
                        list,
                        true);
                PermissionGuideDialog
                        .showDialog(getSupportFragmentManager(), model, new PermissionsDialogCallback() {
                            @Override
                            public void onDismiss(boolean isClickOk) {
                                //isClickOk为true时表示用户点击的是Button，为false表示用户点击的关闭按钮或者物理返回键
                                if (isClickOk) {
                                    PermissionGo.openSetting(MainActivity.this);
                                }
                            }
                        });
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION);
    }
}
