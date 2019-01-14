package com.zhudong.permissiongo;

import android.app.Activity;
import android.util.Log;

import com.zhudong.permissiongo.callbacks.PermissionsCallback;

import java.util.Arrays;

public class PermissionsManger {
    private String TAG = "Permissions";
    private String FRAG_TAG = "__permissions_";
    private PermissionRequestFragment mFragment;

    public static PermissionsManger from(Activity activity) {
        return new PermissionsManger(activity);
    }

    private PermissionsManger(Activity activity){
        if (activity == null || activity.isFinishing()) {
            return;
        }
        mFragment = (PermissionRequestFragment) activity.getFragmentManager().findFragmentByTag(FRAG_TAG);
        if (mFragment == null) {
            mFragment = new PermissionRequestFragment();
            activity.getFragmentManager()
                    .beginTransaction()
                    .add(mFragment, FRAG_TAG)
                    .commitAllowingStateLoss();
            activity.getFragmentManager().executePendingTransactions();
        }
    }

    public void requestPermissions(PermissionsCallback listener, String... permissions) {
        if (mFragment == null) {
            Log.w(TAG, "Please check you activity state");
            return;
        }
        if (permissions == null || permissions.length == 0) {
            return;
        }
        mFragment.requestPermissions(listener, Arrays.asList(permissions));
    }


}
